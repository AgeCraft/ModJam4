package org.agecraft.modjam4;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import org.agecraft.modjam4.blocks.BlockCable;
import org.agecraft.modjam4.tileentities.TileEntityCable;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MJBlockRenderingHandler implements ISimpleBlockRenderingHandler {

	@Override
	public int getRenderId() {
		return MJConfig.cableRenderID;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		if(modelID == MJConfig.cableRenderID) {
			return renderBlockCable(blockAccess, x, y, z, (BlockCable) block, modelID, renderer);
		}
		return false;
	}

	private boolean renderBlockCable(IBlockAccess blockAccess, int x, int y, int z, BlockCable block, int modelID, RenderBlocks renderer) {
		TileEntityCable tile = (TileEntityCable) blockAccess.getTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityCable();
		}
		int size = tile.isInsulated ? 2 : 1;
		double min = pixels(8 - size);
		double max = pixels(8 + size);
		renderer.setRenderBounds(min, min, min, max, max, max);
		renderer.renderStandardBlock(block, x, y, z);
		if(tile.isConnected[ForgeDirection.NORTH.ordinal()]) {
			renderer.setRenderBounds(min, min, 0.0D, max, max, min);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if(tile.isConnected[ForgeDirection.SOUTH.ordinal()]) {
			renderer.setRenderBounds(min, min, max, max, max, 1.0D);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if(tile.isConnected[ForgeDirection.EAST.ordinal()]) {
			renderer.setRenderBounds(max, min, min, 1.0D, max, max);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if(tile.isConnected[ForgeDirection.WEST.ordinal()]) {
			renderer.setRenderBounds(0.0D, min, min, min, max, max);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if(tile.isConnected[ForgeDirection.UP.ordinal()]) {
			renderer.setRenderBounds(min, max, min, max, 1.0D, max);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if(tile.isConnected[ForgeDirection.DOWN.ordinal()]) {
			renderer.setRenderBounds(min, 0.0D, min, max, min, max);
			renderer.renderStandardBlock(block, x, y, z);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {

	}

	public double pixels(int pixels) {
		return ((double) pixels) * 0.0625D;
	}
}
