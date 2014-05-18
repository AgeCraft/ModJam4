package org.agecraft.modjam4.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import org.agecraft.modjam4.tileentities.TileEntitySteamEngine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerSteamEngine extends ContainerBasic {

	public TileEntitySteamEngine tile;
	public int lastBurnTime;
	public int lastCurrentItemBurnTime;
	
	public ContainerSteamEngine(InventoryPlayer inventory, TileEntitySteamEngine tile) {
		this.tile = tile;
		addSlotToContainer(new Slot(tile, 0, 80, 40));
		addInventoryPlayer(inventory, 8, 84);
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, tile.burnTime);
		crafting.sendProgressBarUpdate(this, 1, tile.currentItemBurnTime);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for(int i = 0; i < crafters.size(); i++) {
			ICrafting crafting = (ICrafting) crafters.get(i);
			if(lastBurnTime != tile.burnTime) {
				crafting.sendProgressBarUpdate(this, 0, tile.burnTime);
			}
			if(lastCurrentItemBurnTime != tile.currentItemBurnTime) {
				crafting.sendProgressBarUpdate(this, 1, tile.currentItemBurnTime);
			}
		}
		lastBurnTime = tile.burnTime;
		lastCurrentItemBurnTime = tile.currentItemBurnTime;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		if(id == 0) {
			tile.burnTime = value;
		} else if(id == 1) {
			tile.currentItemBurnTime = value;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotID);
		if(slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();
			if(slotID == 0) {
				if(!mergeItemStack(slotStack, 1, 37, true)) {
					return null;
				}
				slot.onSlotChange(slotStack, stack);
			} else {
				if(TileEntityFurnace.isItemFuel(slotStack)) {
					if(!mergeItemStack(slotStack, 0, 1, false)) {
						return null;
					}
				} else if(slotID >= 1 && slotID < 28) {
					if(!mergeItemStack(slotStack, 28, 37, false)) {
						return null;
					}
				} else if(slotID >= 28 && slotID < 37 && !mergeItemStack(slotStack, 1, 28, false)) {
					return null;
				}
			}
			if(slotStack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if(slotStack.stackSize == stack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, slotStack);
		}
		return stack;
	}
}
