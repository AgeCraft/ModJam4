package org.agecraft.modjam4.items;

import net.minecraft.item.ItemStack;

import org.agecraft.modjam4.ModJam4;

public class ItemPickaxe extends net.minecraft.item.ItemPickaxe {

	public ItemPickaxe(ToolMaterial toolMaterial) {
		super(toolMaterial);
		setCreativeTab(ModJam4.creativeTab);
		setHarvestLevel("pickaxe", 1);
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == ModJam4.ingot && stack2.getItemDamage() == 0 ? true : super.getIsRepairable(stack1, stack2);
	}
}
