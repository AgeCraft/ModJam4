package org.agecraft.modjam4;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.agecraft.modjam4.inventory.ContainerSteamEngine;
import org.agecraft.modjam4.tileentities.TileEntitySteamEngine;

import cpw.mods.fml.common.network.IGuiHandler;

public class MJCommonProxy implements IGuiHandler {

	public void registerRenderingInformation() {
		
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == 0) {
			return new ContainerSteamEngine(player.inventory, (TileEntitySteamEngine) world.getTileEntity(x, y, z));
		}
		return null;
	}
}
