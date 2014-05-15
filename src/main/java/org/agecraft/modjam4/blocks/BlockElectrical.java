package org.agecraft.modjam4.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.agecraft.modjam4.tileentities.TileEntityElectrical;

public class BlockElectrical extends BlockExtendedContainer {

	public BlockElectrical(Material material) {
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityElectrical();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityElectrical.class;
	}
}
