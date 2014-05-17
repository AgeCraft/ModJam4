package org.agecraft.modjam4;

import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MJEventHandler {

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		if(!event.world.isRemote) {
			MJSaveHandler saveHandler = new MJSaveHandler(event.world.getSaveHandler(), event.world);
			saveHandler.load();
		}
	}
	
	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event) {
		if(!event.world.isRemote) {
			MJSaveHandler saveHandler = new MJSaveHandler(event.world.getSaveHandler(), event.world);
			saveHandler.save();
		}
	}
}
