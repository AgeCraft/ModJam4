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
		renderer.setRenderBounds(pixels(7), pixels(7), pixels(7), pixels(9), pixels(9), pixels(9));
		renderer.renderStandardBlock(block, x, y, z);
		if(tile.isConnected[ForgeDirection.NORTH.ordinal()]) {
			renderer.setRenderBounds(pixels(7), pixels(7), pixels(0), pixels(9), pixels(9), pixels(7));
			renderer.renderStandardBlock(block, x, y, z);
		}
		if(tile.isConnected[ForgeDirection.SOUTH.ordinal()]) {
			renderer.setRenderBounds(pixels(7), pixels(7), pixels(9), pixels(9), pixels(9), pixels(16));
			renderer.renderStandardBlock(block, x, y, z);
		}
		if(tile.isConnected[ForgeDirection.EAST.ordinal()]) {
			renderer.setRenderBounds(pixels(9), pixels(7), pixels(7), pixels(16), pixels(9), pixels(9));
			renderer.renderStandardBlock(block, x, y, z);
		}
		if(tile.isConnected[ForgeDirection.WEST.ordinal()]) {
			renderer.setRenderBounds(pixels(0), pixels(7), pixels(7), pixels(7), pixels(9), pixels(9));
			renderer.renderStandardBlock(block, x, y, z);
		}
		if(tile.isConnected[ForgeDirection.UP.ordinal()]) {
			renderer.setRenderBounds(pixels(7), pixels(9), pixels(7), pixels(9), pixels(16), pixels(9));
			renderer.renderStandardBlock(block, x, y, z);
		}
		if(tile.isConnected[ForgeDirection.DOWN.ordinal()]) {
			renderer.setRenderBounds(pixels(7), pixels(0), pixels(7), pixels(9), pixels(7), pixels(9));
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
