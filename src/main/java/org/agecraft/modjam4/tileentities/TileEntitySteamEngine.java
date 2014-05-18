package org.agecraft.modjam4.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class TileEntitySteamEngine extends TileEntityElectrical implements IInventory {

	public ItemStack input;

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public ItemStack getStackInSlot(int slotID) {
		return input;
	}

	@Override
	public ItemStack decrStackSize(int slotID, int amount) {
		if(input != null) {
			ItemStack stack;
			if(input.stackSize <= amount) {
				stack = input;
				input = null;
				return stack;
			} else {
				stack = input.splitStack(amount);
				if(input.stackSize == 0) {
					input = null;
				}
				return stack;
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotID) {
		return input;
	}

	@Override
	public void setInventorySlotContents(int slotID, ItemStack stack) {
		input = stack;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack stack) {
		return true;
	}

	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("machine.steamEngine");
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}
}
