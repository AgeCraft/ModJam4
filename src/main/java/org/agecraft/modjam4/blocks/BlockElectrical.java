package org.agecraft.modjam4.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.agecraft.modjam4.network.ElectricalNetwork;
import org.agecraft.modjam4.tileentities.TileEntityElectrical;

public class BlockElectrical extends BlockExtendedContainer {

	public BlockElectrical(Material material) {
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityElectrical();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityElectrical.class;
	}
	
	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int metadata) {
		updateConnections(world, x, y, z);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		updateConnections(world, x, y, z);
	}
	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int oldMetadata) {
		TileEntityElectrical tile = (TileEntityElectrical) getTileEntity(world, x, y, z);
		if(tile != null && tile.getNetwork() != null) {
			tile.getNetwork().removeNode(tile.getPosition());
			if(tile.getNetwork().size() > 0) {
				tile.updateNetwork();
			}
			if(tile.getNetwork() != null && tile.getNetwork().size() <= 0) {
				tile.setNetwork(null);
			}
		}
	}
	
	public void updateConnections(World world, int x, int y, int z) {
		if(!world.isRemote) {
			TileEntityElectrical tile = (TileEntityElectrical) getTileEntity(world, x, y, z);
			tile.createNetwork();
			for(int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
				ForgeDirection direction = ForgeDirection.VALID_DIRECTIONS[i];
				int otherX = x + direction.offsetX;
				int otherY = y + direction.offsetY;
				int otherZ = z + direction.offsetZ;
				if(canConnect(world, x, y, z, otherX, otherY, otherZ) && (world.getBlock(otherX, otherY, otherZ) instanceof BlockElectrical && ((BlockElectrical) world.getBlock(otherX, otherY, otherZ)).canConnect(world, otherX, otherY, otherZ, x, y, z))) {
					TileEntityElectrical tileOther = (TileEntityElectrical) getTileEntity(world, otherX, otherY, otherZ);
					if(tileOther != null && tileOther.getNetwork() != null && !tile.getNetwork().hasEdge(tile.getPosition(), tileOther.getPosition())) {
						if(tile.getNetwork().size() > tileOther.getNetwork().size()) {
							tile.mergeNetworks(tileOther);
							tile.getNetwork().addEdge(tile.getPosition(), tileOther.getPosition());
						} else {
							tileOther.mergeNetworks(tile);
							tileOther.getNetwork().addEdge(tile.getPosition(), tileOther.getPosition());
						}
					}
					tile.isConnected[direction.ordinal()] = true;
					tileOther.isConnected[ForgeDirection.OPPOSITES[direction.ordinal()]] = true;
				} else {
					tile.isConnected[direction.ordinal()] = false;
				}
			}
		}
	}
	
	public boolean canConnect(World world, int x, int y, int z, int otherX, int otherY, int otherZ) {
		return world.getBlock(otherX, otherY, otherZ) instanceof BlockElectrical;
	}
	
	public ElectricalNetwork getNetwork(World world, int x, int y, int z) {
		return ((TileEntityElectrical) getTileEntity(world, x, y, z)).getNetwork();
	}
}
