package org.agecraft.modjam4.items;

import org.agecraft.modjam4.ModJam4;

public class ItemArmor extends net.minecraft.item.ItemArmor {

	public ItemArmor(ArmorMaterial material, int renderIndex, int armorIndex) {
		super(material, renderIndex, armorIndex);
		setCreativeTab(ModJam4.creativeTab);
	}
}
