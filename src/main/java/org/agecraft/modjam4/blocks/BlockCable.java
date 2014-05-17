package org.agecraft.modjam4.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.agecraft.modjam4.MJConfig;
import org.agecraft.modjam4.MJResources;
import org.agecraft.modjam4.ModJam4;
import org.agecraft.modjam4.tileentities.TileEntityCable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCable extends BlockElectrical {

	public BlockCable() {
		super(Material.cloth);
		setHardness(0.8F);
		setStepSound(Block.soundTypeCloth);
		setCreativeTab(ModJam4.creativeTab);
	}
	
	public String getLocalizedName(ItemStack stack) {
		return ((stack.getItemDamage() & 1) == 0 ? StatCollector.translateToLocal("cable.uninsulated") + " " : "") + StatCollector.translateToLocalFormatted(getUnlocalizedName(), StatCollector.translateToLocal("metals.copper"));
	}

	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.MJ_cable.name";
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityCable();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityCable.class;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return MJConfig.cableRenderID;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		TileEntityCable tile = (TileEntityCable) getTileEntity(blockAccess, x, y, z);
		float minX = pixels(7);
		float minY = pixels(7);
		float minZ = pixels(7);
		float maxX = pixels(9);
		float maxY = pixels(9);
		float maxZ = pixels(9);
		if(tile.isConnected[ForgeDirection.NORTH.ordinal()]) {
			minZ = pixels(0);
		}
		if(tile.isConnected[ForgeDirection.SOUTH.ordinal()]) {
			maxZ = pixels(16);
		}
		if(tile.isConnected[ForgeDirection.EAST.ordinal()]) {
			maxX = pixels(16);
		}
		if(tile.isConnected[ForgeDirection.WEST.ordinal()]) {
			minZ = pixels(0);
		}
		if(tile.isConnected[ForgeDirection.UP.ordinal()]) {
			maxY = pixels(16);
		}
		if(tile.isConnected[ForgeDirection.DOWN.ordinal()]) {
			minY = pixels(0);
		}
		setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	public float pixels(int pixels) {
		return ((float) pixels) * 0.0625F;
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
		setBlockBoundsBasedOnState(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		TileEntityCable tile = (TileEntityCable) getTileEntity(world, x, y, z);
		tile.isInsulated = (meta & 1) == 1;
		tile.color = (meta & 30) / 2;
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if((meta & 1) == 0) {
			return MJResources.cableCopperUninsulated;
		} else {
			return MJResources.cablesCopper[(meta & 30) / 2];
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntityCable tile = (TileEntityCable) getTileEntity(blockAccess, x, y, z);
		if(!tile.isInsulated) {
			return MJResources.cableCopperUninsulated;
		} else {
			return MJResources.cablesCopper[tile.color];
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		list.add(new ItemStack(item, 1, 0));
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(item, 1, 1 + i * 2));
		}
	}
}
