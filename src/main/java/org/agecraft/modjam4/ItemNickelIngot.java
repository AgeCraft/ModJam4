package org.agecraft.modjam4;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemNickelIngot extends Item {
    public ItemNickelIngot() {
        setMaxStackSize(64);
        setCreativeTab(CreativeTabs.tabMaterials);
        setUnlocalizedName("TopazItem");
    }
}
