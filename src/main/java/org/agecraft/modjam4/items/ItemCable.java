package org.agecraft.modjam4.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import org.agecraft.modjam4.MJResources;
import org.agecraft.modjam4.blocks.BlockCable;
import org.agecraft.modjam4.util.MJUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCable extends ItemBlockName {

	public ItemCable(Block block) {
		super(block);
		setMaxDamage(0);
        setHasSubtypes(true);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return ((BlockCable) field_150939_a).getLocalizedName(stack);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ((BlockCable) field_150939_a).getUnlocalizedName(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		if((stack.getItemDamage() & 1) == 1) {
			list.add(StatCollector.translateToLocal("color." + MJUtil.getColorName((stack.getItemDamage() & 30) / 2)));
		}
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getSpriteNumber() {
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		if((meta & 1) == 0) {
			return MJResources.itemCableCopperUninsulated;
		} else {
			return MJResources.itemCablesCopper[(meta & 30) / 2];
		}
	}
}
