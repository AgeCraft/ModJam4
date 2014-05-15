package org.agecraft.modjam4;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.util.EnumHelper;

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
	public static Item helmetCopper;
	public static Item chestplateCopper;
	public static Item leggingsCopper;
	public static Item bootsCopper;

	public static ToolMaterial toolMaterialCopper;
	public static ArmorMaterial armorMaterialCopper;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//init creative tab
		creativeTab = new MJCreativeTab("ModJam4");
		
		//register tool material
		toolMaterialCopper = EnumHelper.addToolMaterial("COPPER", 1, 181, 4.0F, 1.0F, 7);
		
		//register armor material
		armorMaterialCopper = EnumHelper.addArmorMaterial("COPPER", 8, new int[]{2, 4, 3, 1}, 7);
		
		//init blocks
		ore = new BlockOre().setBlockName("MJ_ore");
		block = new BlockBlock().setBlockName("MJ_block");
		
		//register blocks
		GameRegistry.registerBlock(ore, ItemBlockMetadata.class, "MJ_ore");
		GameRegistry.registerBlock(block, ItemBlockMetadata.class, "MJ_block");
		
		//init items
		ingot = new ItemIngot().setUnlocalizedName("MJ_ingot");
		helmetCopper = new ItemArmor(armorMaterialCopper, 5, 0).setUnlocalizedName("MJ_helmetCopper").setTextureName("modjam4:helmetCopper");
		chestplateCopper = new ItemArmor(armorMaterialCopper, 5, 1).setUnlocalizedName("MJ_chestplateCopper").setTextureName("modjam4:chestplateCopper");
		leggingsCopper = new ItemArmor(armorMaterialCopper, 5, 2).setUnlocalizedName("MJ_leggingsCopper").setTextureName("modjam4:leggingsCopper");
		bootsCopper = new ItemArmor(armorMaterialCopper, 5, 3).setUnlocalizedName("MJ_bootsCopper").setTextureName("modjam4:bootsCopper");
		
		//register items
		GameRegistry.registerItem(ingot, "MJ_ingot");
		GameRegistry.registerItem(helmetCopper, "MJ_helmetCopper");
		GameRegistry.registerItem(chestplateCopper, "MJ_chetsplateCopper");
		GameRegistry.registerItem(leggingsCopper, "MJ_leggingsCopper");
		GameRegistry.registerItem(bootsCopper, "MJ_bootsCopper");
		
		//set crafting materials
		toolMaterialCopper.customCraftingMaterial = ingot;
		armorMaterialCopper.customCraftingMaterial = ingot;
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenderingInformation();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
