package org.agecraft.modjam4.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

public class BlockOre extends BlockMetadata {

	public BlockOre() {
		super(Material.rock);
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(Block.soundTypePiston);
		setCreativeTab(ModJam4.creativeTab);
		
		setHarvestLevel("pickaxe", 1, 0);
		setHarvestLevel("pickaxe", 2, 1);
		setHarvestLevel("pickaxe", 2, 2);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return StatCollector.translateToLocalFormatted(getUnlocalizedName(), StatCollector.translateToLocal("metals." + MJResources.metals[stack.getItemDamage()]));
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.MJ_ore.name";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return MJResources.ores[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		MJResources.registerBlockIcons(iconRegister);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MJResources.metals.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
