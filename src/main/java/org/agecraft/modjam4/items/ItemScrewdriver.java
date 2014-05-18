package org.agecraft.modjam4.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.agecraft.modjam4.MJResources;
import org.agecraft.modjam4.ModJam4;
import org.agecraft.modjam4.blocks.BlockBattery;
import org.agecraft.modjam4.blocks.BlockElectrical;
import org.agecraft.modjam4.tileentities.TileEntityBattery;
import org.agecraft.modjam4.tileentities.TileEntityElectrical;

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
				if(block instanceof BlockBattery) {
					player.addChatComponentMessage(new ChatComponentText("Energy Stored: (" + ((TileEntityBattery) world.getTileEntity(x, y, z)).energyStored + " / " + ((TileEntityBattery) world.getTileEntity(x, y, z)).getMaxEnergy() + ")"));
				} else if(block instanceof BlockElectrical) {
					player.addChatComponentMessage(new ChatComponentText("Energy: (" + ((TileEntityElectrical) world.getTileEntity(x, y, z)).energyTotal + " / " + ((TileEntityElectrical) world.getTileEntity(x, y, z)).getMaxEnergy() + ")"));
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
