package org.agecraft.modjam4.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import org.agecraft.modjam4.MJResources;
import org.agecraft.modjam4.ModJam4;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRod extends Item {
	
	public ItemRod() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(ModJam4.creativeTab);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return StatCollector.translateToLocalFormatted(getUnlocalizedName(), StatCollector.translateToLocal("metals." + MJResources.rodTypes[stack.getItemDamage()]));
	}
	
	@Override
	public String getUnlocalizedName() {
		return "item.MJ_rod.name";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return MJResources.rods[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MJResources.rodTypes.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
