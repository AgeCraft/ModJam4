package org.agecraft.modjam4.tileentities;

import io.netty.buffer.ByteBuf;
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
			tile.isInsulated = isInsulated;
			tile.color = color;
		}
	}
	
	public boolean isInsulated = false;
	public int color = -1;
	
	@Override
	public Packet getDescriptionPacket() {
		return ModJam4.packetHandler.getPacketToClient(new MessageTileCable(xCoord, yCoord, zCoord, isConnected, isInsulated, color));
	}
}
