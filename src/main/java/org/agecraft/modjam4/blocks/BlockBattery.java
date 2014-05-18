package org.agecraft.modjam4.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.agecraft.modjam4.ModJam4;
import org.agecraft.modjam4.tileentities.TileEntityBattery;

public class BlockBattery extends BlockElectrical {

	public BlockBattery() {
		super(Material.iron);
		setCreativeTab(ModJam4.creativeTab);
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.MJ_battery.name";
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityBattery();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityBattery.class;
	}
}
