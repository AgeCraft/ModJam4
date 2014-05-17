package org.agecraft.modjam4.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import org.agecraft.modjam4.ModJam4;

public class BlockCable extends BlockElectrical {

	public BlockCable() {
		super(Material.cloth);
		setHardness(0.8F);
		setStepSound(Block.soundTypeCloth);
		setCreativeTab(ModJam4.creativeTab);
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.MJ_cable.name";
	}
}
