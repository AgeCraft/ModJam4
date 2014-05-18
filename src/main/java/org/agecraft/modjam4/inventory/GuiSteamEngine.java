package org.agecraft.modjam4.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.agecraft.modjam4.MJResources;
import org.agecraft.modjam4.tileentities.TileEntitySteamEngine;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSteamEngine extends GuiContainer {

	private TileEntitySteamEngine tile;

	public GuiSteamEngine(InventoryPlayer inventory, TileEntitySteamEngine tile) {
		super(new ContainerSteamEngine(inventory, tile));
		this.tile = tile;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialRenderTime, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(MJResources.guiSteamEngine);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if(tile.isBurning()) {
			int scale = tile.getBurnTimeRemainingScaled(12);
			drawTexturedModalRect(guiLeft + 80, guiTop + 23 + 12 - scale, 176, 12 - scale, 14, scale + 2);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		String inventoryName = tile.hasCustomInventoryName() ? tile.getInventoryName() : StatCollector.translateToLocal(tile.getInventoryName());
		fontRendererObj.drawString(inventoryName, xSize / 2 - fontRendererObj.getStringWidth(inventoryName) / 2, 6, 0x404040);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 0x404040);
	}
}
