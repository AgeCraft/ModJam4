package org.agecraft.modjam4;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import org.agecraft.modjam4.blocks.BlockBlock;
import org.agecraft.modjam4.blocks.BlockOre;
import org.agecraft.modjam4.items.ItemBlockMetadata;
import org.agecraft.modjam4.items.ItemIngot;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = MJReference.MOD_ID, name = MJReference.NAME, version = MJReference.VERSION, acceptedMinecraftVersions = MJReference.MC_VERSION, dependencies = MJReference.DEPENDENCIES)
public class ModJam4 {

	@Instance(MJReference.MOD_ID)
	public static ModJam4 instance;
	
	@SidedProxy(clientSide = MJReference.CLIENT_PROXY_CLASS, serverSide = MJReference.SERVER_PROXY_CLASS)
	public static MJCommonProxy proxy;
	
	public static MJCreativeTab creativeTab;
	
	public static Block ore;
	public static Block block;
	
	public static Item ingot;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//init creative tab
		creativeTab = new MJCreativeTab("ModJam4");
		
		//init blocks
		ore = new BlockOre().setBlockName("MJ_ore");
		block = new BlockBlock().setBlockName("MJ_block");
		
		//register blocks
		GameRegistry.registerBlock(ore, ItemBlockMetadata.class, "MJ_ore");
		GameRegistry.registerBlock(block, ItemBlockMetadata.class, "MJ_block");
		
		//init items
		ingot = new ItemIngot().setUnlocalizedName("MJ_ingot");
		
		//register items
		GameRegistry.registerItem(ingot, "MJ_ingot");
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenderingInformation();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
