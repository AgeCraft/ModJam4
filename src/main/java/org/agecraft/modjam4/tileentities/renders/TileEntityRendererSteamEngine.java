package org.agecraft.modjam4.tileentities.renders;

import org.agecraft.modjam4.MJResources;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRendererSteamEngine extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//bindTexture(textureSteamEninge);
		MJResources.modelSteamEngine.renderAll();
		GL11.glPopMatrix();
	}
}
