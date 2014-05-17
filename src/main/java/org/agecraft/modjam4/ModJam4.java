package org.agecraft.modjam4;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

import org.agecraft.modjam4.blocks.BlockBlock;
import org.agecraft.modjam4.blocks.BlockCable;
import org.agecraft.modjam4.blocks.BlockOre;
import org.agecraft.modjam4.items.ItemArmor;
import org.agecraft.modjam4.items.ItemAxe;
import org.agecraft.modjam4.items.ItemBlockMetadata;
import org.agecraft.modjam4.items.ItemBlockName;
import org.agecraft.modjam4.items.ItemHoe;
import org.agecraft.modjam4.items.ItemIngot;
import org.agecraft.modjam4.items.ItemPickaxe;
import org.agecraft.modjam4.items.ItemRod;
import org.agecraft.modjam4.items.ItemScrewdriver;
import org.agecraft.modjam4.items.ItemShovel;
import org.agecraft.modjam4.items.ItemSword;
import org.agecraft.modjam4.tileentities.TileEntityElectrical;
import org.agecraft.modjam4.tileentities.TileEntityExtended;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	public static Logger log = LogManager.getLogger(MJReference.MOD_ID);

	public static MJCreativeTab creativeTab;

	public static Block ore;
	public static Block block;
	public static Block cable;
	
	public static Item ingot;
	public static Item rod;
	public static Item swordCopper;
	public static Item pickaxeCopper;
	public static Item axeCopper;
	public static Item shovelCopper;
	public static Item hoeCopper;
	public static Item helmetCopper;
	public static Item chestplateCopper;
	public static Item leggingsCopper;
	public static Item bootsCopper;
	public static Item screwdriver;
	public static Item battery;

	public static ToolMaterial toolMaterialCopper;
	public static ArmorMaterial armorMaterialCopper;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// init creative tab
		creativeTab = new MJCreativeTab("ModJam4");

		// register tool material
		toolMaterialCopper = EnumHelper.addToolMaterial("COPPER", 1, 181, 4.0F, 1.0F, 7);

		// register armor material
		armorMaterialCopper = EnumHelper.addArmorMaterial("COPPER", 8, new int[]{2, 4, 3, 1}, 7);

		// init blocks
		ore = new BlockOre().setBlockName("MJ_ore");
		block = new BlockBlock().setBlockName("MJ_block");
		cable = new BlockCable().setBlockName("MJ_cable");

		// register blocks
		GameRegistry.registerBlock(ore, ItemBlockMetadata.class, "MJ_ore");
		GameRegistry.registerBlock(block, ItemBlockMetadata.class, "MJ_block");
		GameRegistry.registerBlock(cable, ItemBlockName.class, "MJ_cable");

		// init items
		ingot = new ItemIngot().setUnlocalizedName("MJ_ingot");
		rod = new ItemRod().setUnlocalizedName("MJ_rod");
		swordCopper = new ItemSword(toolMaterialCopper).setUnlocalizedName("MJ_swordCopper").setTextureName("modjam4:swordCopper");
		pickaxeCopper = new ItemPickaxe(toolMaterialCopper).setUnlocalizedName("MJ_pickaxeCopper").setTextureName("modjam4:pickaxeCopper");
		axeCopper = new ItemAxe(toolMaterialCopper).setUnlocalizedName("MJ_axeCopper").setTextureName("modjam4:axeCopper");
		shovelCopper = new ItemShovel(toolMaterialCopper).setUnlocalizedName("MJ_shovelCopper").setTextureName("modjam4:shovelCopper");
		hoeCopper = new ItemHoe(toolMaterialCopper).setUnlocalizedName("MJ_hoeCopper").setTextureName("modjam4:hoeCopper");
		helmetCopper = new ItemArmor(armorMaterialCopper, 0, 0).setUnlocalizedName("MJ_helmetCopper").setTextureName("modjam4:helmetCopper");
		chestplateCopper = new ItemArmor(armorMaterialCopper, 0, 1).setUnlocalizedName("MJ_chestplateCopper").setTextureName("modjam4:chestplateCopper");
		leggingsCopper = new ItemArmor(armorMaterialCopper, 0, 2).setUnlocalizedName("MJ_leggingsCopper").setTextureName("modjam4:leggingsCopper");
		bootsCopper = new ItemArmor(armorMaterialCopper, 0, 3).setUnlocalizedName("MJ_bootsCopper").setTextureName("modjam4:bootsCopper");
		screwdriver = new ItemScrewdriver().setUnlocalizedName("MJ_screwdriver");

		// register items
		GameRegistry.registerItem(ingot, "MJ_ingot");
		GameRegistry.registerItem(rod, "MJ_rod");
		GameRegistry.registerItem(swordCopper, "MJ_swordCopper");
		GameRegistry.registerItem(pickaxeCopper, "MJ_pickaxeCopper");
		GameRegistry.registerItem(axeCopper, "MJ_axeCopper");
		GameRegistry.registerItem(shovelCopper, "MJ_shovelCopper");
		GameRegistry.registerItem(hoeCopper, "MJ_hoeCopper");
		GameRegistry.registerItem(helmetCopper, "MJ_helmetCopper");
		GameRegistry.registerItem(chestplateCopper, "MJ_chetsplateCopper");
		GameRegistry.registerItem(leggingsCopper, "MJ_leggingsCopper");
		GameRegistry.registerItem(bootsCopper, "MJ_bootsCopper");
		GameRegistry.registerItem(screwdriver, "MJ_screwdriver");
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityExtended.class, "MJ_TileExteneded");
		GameRegistry.registerTileEntity(TileEntityElectrical.class, "MJ_TileElectrical");

		// set crafting materials
		toolMaterialCopper.customCraftingMaterial = ingot;
		armorMaterialCopper.customCraftingMaterial = ingot;
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// register recipes
		for(int i = 0; i < MJResources.metals.length; i++) {
			FurnaceRecipes.smelting().func_151394_a(new ItemStack(ore, 1, i), new ItemStack(ingot, 1, i), 0.7F);
			CraftingManager.getInstance().addRecipe(new ItemStack(block, 1, i), "XXX", "XXX", "XXX", 'X', new ItemStack(ingot, 1, i));
		}
		CraftingManager.getInstance().addRecipe(new ItemStack(rod, 4, 0), "X", "X", 'X', new ItemStack(ingot, 1, 0));
		CraftingManager.getInstance().addRecipe(new ItemStack(rod, 4, 1), "X", "X", 'X', new ItemStack(Items.iron_ingot));

		CraftingManager.getInstance().addRecipe(new ItemStack(screwdriver), " X", "IY", 'X', new ItemStack(rod, 1, 1), 'I', new ItemStack(Items.stick), 'Y', new ItemStack(Items.dye, 1, 11));
		CraftingManager.getInstance().addRecipe(new ItemStack(screwdriver), "YX", "I ", 'X', new ItemStack(rod, 1, 1), 'I', new ItemStack(Items.stick), 'Y', new ItemStack(Items.dye, 1, 11));

		String[][] recipePatterns = new String[][]{{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}, {"X", "X", "#"}, {"XXX", " # ", " # "}, {"XX", "X#", " #"}, {"X", "#", "#"}, {"XX", " #", " #"}};
		Item[] recipeOutputs = new Item[]{helmetCopper, chestplateCopper, leggingsCopper, bootsCopper, swordCopper, pickaxeCopper, axeCopper, shovelCopper, hoeCopper};
		for(int i = 0; i < recipePatterns.length; ++i) {
			CraftingManager.getInstance().addRecipe(new ItemStack(recipeOutputs[i]), recipePatterns[i], 'X', new ItemStack(ingot, 1, 0), '#', new ItemStack(Items.stick));
		}
		
		//register event handler
		MinecraftForge.EVENT_BUS.register(new MJEventHandler());

		// register rendering information
		proxy.registerRenderingInformation();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}
