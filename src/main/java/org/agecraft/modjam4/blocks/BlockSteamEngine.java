package org.agecraft.modjam4.blocks;

import org.agecraft.modjam4.ModJam4;

import net.minecraft.block.material.Material;

public class BlockSteamEngine extends BlockElectrical {

	public BlockSteamEngine() {
		super(Material.iron);
		setCreativeTab(ModJam4.creativeTab);
	}
}
