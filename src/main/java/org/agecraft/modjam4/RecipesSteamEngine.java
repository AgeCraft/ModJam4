package org.agecraft.modjam4;

import java.util.HashMap;

import org.agecraft.modjam4.util.MJUtil;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipesSteamEngine {

	public static HashMap<ItemStack, Integer> burnTime = new HashMap<ItemStack, Integer>();
	public static HashMap<ItemStack, Integer> burnEnergy = new HashMap<ItemStack, Integer>();
	
	public static int getBurnTime(ItemStack stack) {
		for(ItemStack s : burnTime.keySet()) {
			if(MJUtil.areItemStacksEqualNoSize(stack, s)) {
				return burnTime.get(s);
			}
		}
		return 0;
	}
	
	public static int getBurnEnergy(ItemStack stack) {
		for(ItemStack s : burnEnergy.keySet()) {
			if(MJUtil.areItemStacksEqualNoSize(stack, s)) {
				return burnEnergy.get(s);
			}
		}
		return 0;
	}
	
	public static void addRecipe(ItemStack stack, int time, int energy) {
		burnTime.put(stack, time);
		burnEnergy.put(stack, energy);
	}
	
	public static void addRecipes() {
		addRecipe(new ItemStack(Items.coal), 1, 1);
	}
}
