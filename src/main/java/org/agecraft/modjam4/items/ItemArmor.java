package org.agecraft.modjam4.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import org.agecraft.modjam4.ModJam4;

public class ItemArmor extends net.minecraft.item.ItemArmor {

	public ItemArmor(ArmorMaterial material, int renderIndex, int armorIndex) {
		super(material, renderIndex, armorIndex);
		setCreativeTab(ModJam4.creativeTab);
	}

	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == ModJam4.ingot && stack2.getItemDamage() == 0 ? true : super.getIsRepairable(stack1, stack2);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return String.format("modjam4:textures/armor/copper_layer_%d.png", (slot == 2 ? 2 : 1));
	}
}
