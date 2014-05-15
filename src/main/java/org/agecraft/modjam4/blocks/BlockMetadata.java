package org.agecraft.modjam4.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public abstract class BlockMetadata extends Block {

	public BlockMetadata(Material material) {
		super(material);
	}

	public String getLocalizedName(ItemStack stack) {
		return StatCollector.translateToLocal(getUnlocalizedName(stack));
	}

	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
}
