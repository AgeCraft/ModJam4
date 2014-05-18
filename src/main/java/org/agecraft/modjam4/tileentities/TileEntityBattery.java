package org.agecraft.modjam4.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBattery extends TileEntityElectrical {

	public double energyStored;
	
	@Override
	public void addEnergy(ForgeDirection direction, double energy) {
		energyStored += energy;
	}
	
	@Override
	public double getMaxEnergy(ForgeDirection direction) {
		return getMaxEnergy() - energyStored;
	}
	
	@Override
	public double getMaxEnergy() {
		return 1000.0D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		energyStored = nbt.getDouble("EnergyStored");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble("EnergyStored", energyStored);
	}
}
