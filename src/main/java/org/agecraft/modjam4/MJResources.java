package org.agecraft.modjam4;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MJResources {

	public static final String[] metals = new String[]{"copper", "nickel", "zinc"};

	public static IIcon[] ores = new IIcon[metals.length];
	public static IIcon[] blocks = new IIcon[metals.length];
	public static IIcon[] ingots = new IIcon[metals.length];

	public static void registerBlockIcons(IIconRegister iconRegister) {
		for(int i = 0; i < metals.length; i++) {
			ores[i] = iconRegister.registerIcon("modjam4:ore" + firstUpperCase(metals[i]));
			blocks[i] = iconRegister.registerIcon("modjam4:block" + firstUpperCase(metals[i]));
		}
	}
	
	public static void registerItemIcons(IIconRegister iconRegister) {
		for(int i = 0; i < metals.length; i++) {
			ingots[i] = iconRegister.registerIcon("modjam4:ingot" + firstUpperCase(metals[i]));
		}
	}

	public static String firstUpperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
	}
}
