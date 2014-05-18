package org.agecraft.modjam4.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
		int size = tile.isInsulated ? 2 : 1;
		float minX = pixels(8 - size);
		float minY = pixels(8 - size);
		float minZ = pixels(8 - size);
		float maxX = pixels(8 + size);
		float maxY = pixels(8 + size);
		float maxZ = pixels(8 + size);
		if(tile.isConnected[ForgeDirection.NORTH.ordinal()]) {
			minZ = 0.0F;
		}
		if(tile.isConnected[ForgeDirection.SOUTH.ordinal()]) {
			maxZ = 1.0F;
		}
		if(tile.isConnected[ForgeDirection.EAST.ordinal()]) {
			maxX = 1.0F;
		}
		if(tile.isConnected[ForgeDirection.WEST.ordinal()]) {
			minX = 0.0F;
		}
		if(tile.isConnected[ForgeDirection.UP.ordinal()]) {
			maxY = 1.0F;
		}
		if(tile.isConnected[ForgeDirection.DOWN.ordinal()]) {
			minY = 0.0F;
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
		return 0;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntityCable tile = (TileEntityCable) getTileEntity(world, x, y, z);
		tile.isInsulated = (stack.getItemDamage() & 1) == 1;
		tile.color = (stack.getItemDamage() & 30) / 2;
	}
	
	@Override
	public boolean canConnect(World world, int x, int y, int z, int otherX, int otherY, int otherZ) {
		Block block = world.getBlock(otherX, otherY, otherZ);
		if(block instanceof BlockElectrical) {
			if(block instanceof BlockCable) {
				TileEntityCable tile = (TileEntityCable) getTileEntity(world, x, y, z);
				TileEntityCable tileOther = (TileEntityCable) getTileEntity(world, otherX, otherY, otherZ);
				if(tile.isInsulated) {
					return !tileOther.isInsulated || tile.color == tileOther.color;
				} else {
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
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
