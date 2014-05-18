package org.agecraft.modjam4;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.agecraft.modjam4.inventory.GuiSteamEngine;
import org.agecraft.modjam4.tileentities.TileEntitySteamEngine;

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
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == 0) {
			return new GuiSteamEngine(player.inventory, (TileEntitySteamEngine) world.getTileEntity(x, y, z));
		}
		return null;
	}
}
