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

public class ItemIngot extends Item {
		
	public ItemIngot() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(ModJam4.creativeTab);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return StatCollector.translateToLocalFormatted(getUnlocalizedName(), StatCollector.translateToLocal("metals." + MJResources.metals[stack.getItemDamage()]));
	}
	
	@Override
	public String getUnlocalizedName() {
		return "metals.ingot";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return MJResources.ingots[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		MJResources.registerItemIcons(iconRegister);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabg, List list) {
		for(int i = 0; i < MJResources.metals.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
