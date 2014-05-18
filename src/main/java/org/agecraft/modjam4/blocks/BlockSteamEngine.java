package org.agecraft.modjam4.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.agecraft.modjam4.ModJam4;
import org.agecraft.modjam4.tileentities.TileEntitySteamEngine;

public class BlockSteamEngine extends BlockElectrical {

	public BlockSteamEngine() {
		super(Material.iron);
		setCreativeTab(ModJam4.creativeTab);
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.MJ_steamEngine.name";
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntitySteamEngine();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySteamEngine.class;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			player.openGui(ModJam4.instance, 0, world, x, y, z);
		}
		return true;
	}
}
