package org.agecraft.modjam4.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.agecraft.modjam4.MJResources;
import org.agecraft.modjam4.ModJam4;
import org.agecraft.modjam4.blocks.BlockCable;
import org.agecraft.modjam4.blocks.BlockElectrical;
import org.agecraft.modjam4.tileentities.TileEntityCable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemScrewdriver extends Item {

	public ItemScrewdriver() {
		setFull3D();
		setMaxStackSize(1);
		setMaxDamage(69);
		setCreativeTab(ModJam4.creativeTab);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return StatCollector.translateToLocal(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "item.MJ_screwdriver.name";
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			Block block = world.getBlock(x, y, z);
			if(block != null) {
				if(block instanceof BlockCable) {
					boolean[] isConnected = ((TileEntityCable) world.getTileEntity(x, y, z)).isConnected;
					for(int i = 0; i < isConnected.length; i++) {
						player.addChatComponentMessage(new ChatComponentText(ForgeDirection.values()[i] + ": " + (isConnected[i] ? "true" : "false")));
					}
				} else if(block instanceof BlockElectrical) {
					player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.UNDERLINE + "Electrical Network @ " + x + ", " + y + ", " + z + EnumChatFormatting.RESET));
					player.addChatComponentMessage(new ChatComponentText(""));
					player.addChatComponentMessage(new ChatComponentText(((BlockElectrical) block).getNetwork(world, x, y, z).toString()));
					player.addChatComponentMessage(new ChatComponentText(""));
				}
			}
		}
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return MJResources.screwdriver;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
	}
}
