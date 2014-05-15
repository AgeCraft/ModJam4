package org.agecraft.modjam4.tileentities;

import net.minecraft.tileentity.TileEntity;

public class TileEntityExtended extends TileEntity {

	public boolean hasDroppped = false;
	
	@Override
	public boolean canUpdate() {
		return false;
	}
}
