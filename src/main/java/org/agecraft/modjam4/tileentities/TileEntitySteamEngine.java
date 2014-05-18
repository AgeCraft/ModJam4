package org.agecraft.modjam4.tileentities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;

public class TileEntitySteamEngine extends TileEntityElectrical implements ISidedInventory {

	public static final int[] accessibleSlots = new int[]{0};

	public ItemStack input;
	public int burnTime;
	public int currentItemBurnTime;

	@Override
	public boolean canUpdate() {
		return true;
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			if(burnTime > 0) {
				burnTime--;
			} else {
				if(input != null && input.stackSize > 0) {
					currentItemBurnTime = TileEntityFurnace.getItemBurnTime(input);
					burnTime += currentItemBurnTime;
					input.stackSize--;
					if(input.stackSize == 0) {
						input = input.getItem().getContainerItem(input);
					}
				}
			}
		}
	}

	public boolean isBurning() {
		return burnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int scale) {
		if(currentItemBurnTime == 0) {
			currentItemBurnTime = 200;
		}
		return burnTime * scale / currentItemBurnTime;
	}

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
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false : player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack stack) {
		return TileEntityFurnace.isItemFuel(stack);
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

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return accessibleSlots;
	}

	@Override
	public boolean canExtractItem(int slotID, ItemStack stack, int side) {
		return true;
	}

	@Override
	public boolean canInsertItem(int slotID, ItemStack stack, int side) {
		return isItemValidForSlot(slotID, stack);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		input = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Input"));
		burnTime = nbt.getInteger("BurnTime");
		currentItemBurnTime = nbt.getInteger("CurrentBurnTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagCompound tag = new NBTTagCompound();
		input.writeToNBT(tag);
		nbt.setTag("Input", tag);
		nbt.setInteger("BurnTime", burnTime);
		nbt.setInteger("CurrentBurnTime", currentItemBurnTime);
	}
}
