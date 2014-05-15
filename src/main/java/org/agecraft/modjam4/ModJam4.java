package org.agecraft.modjam4;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MJReference.MOD_ID, name = MJReference.NAME, version = MJReference.VERSION, acceptedMinecraftVersions = MJReference.MC_VERSION, dependencies = MJReference.DEPENDENCIES)
public class ModJam4 {

	@Instance(MJReference.MOD_ID)
	public static ModJam4 instance;
	
	@SidedProxy(clientSide = MJReference.CLIENT_PROXY_CLASS, serverSide = MJReference.SERVER_PROXY_CLASS)
	public static MJCommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenderingInformation();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
