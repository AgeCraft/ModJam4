package org.agecraft.modjam4;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MJClientProxy extends MJCommonProxy {

	@Override
	public void registerRenderingInformation() {
		// init packet handler
		ModJam4.packetHandler.setClientHandler(new MJPacketHandlerClient());
		
		//get block render ids
		MJConfig.cableRenderID = RenderingRegistry.getNextAvailableRenderId();
		
		//register block rendering handler
		MJBlockRenderingHandler blockRenderingHandler = new MJBlockRenderingHandler();
		RenderingRegistry.registerBlockHandler(MJConfig.cableRenderID, blockRenderingHandler);
	}
}
