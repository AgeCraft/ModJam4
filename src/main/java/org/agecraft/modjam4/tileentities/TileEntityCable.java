package org.agecraft.modjam4.tileentities;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

import org.agecraft.modjam4.ModJam4;
import org.agecraft.modjam4.util.MJUtilClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityCable extends TileEntityElectrical {

	public static class MessageTileCable extends MessageTileElectrical {
		
		public boolean isInsulated;
		public int color;
		
		public MessageTileCable() {
			super();
		}
		
		public MessageTileCable(int x, int y, int z, boolean[] isConnected, boolean isInsulated, int color) {
			super(x, y, z, isConnected);
			this.isInsulated = isInsulated;
			this.color = color;
		}
		
		@Override
		public void encodeTo(ByteBuf target) {
			super.encodeTo(target);
			target.writeBoolean(isInsulated);
			target.writeByte(color);
		}
		
		@Override
		public void decodeFrom(ByteBuf source) {
			super.decodeFrom(source);
			isInsulated = source.readBoolean();
			color = source.readByte();
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void handle() {
			World world = MJUtilClient.getWorld();
			TileEntityCable tile = (TileEntityCable) world.getTileEntity(x, y, z);
			tile.isConnected = isConnected;
			tile.isInsulated = isInsulated;
			tile.color = color;
			world.markBlockForUpdate(x, y, z);
		}
	}
	
	public boolean isInsulated;
	public int color;
	
	@Override
	public Packet getDescriptionPacket() {
		return ModJam4.packetHandler.getPacketToClient(new MessageTileCable(xCoord, yCoord, zCoord, isConnected, isInsulated, color));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		isInsulated = nbt.getBoolean("IsInsulated");
		color = nbt.getInteger("Color");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("IsInsulated", isInsulated);
		nbt.setInteger("Color", color);
	}
}
