package org.agecraft.modjam4;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class MJMessageTile extends MJMessage {

	public int x;
	public int y;
	public int z;
	
	public MJMessageTile() {
	}
	
	public MJMessageTile(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void encodeTo(ByteBuf target) {
		target.writeInt(x);
		target.writeShort(y);
		target.writeInt(z);
	}

	@Override
	public void decodeFrom(ByteBuf source) {
		x = source.readInt();
		y = source.readShort();
		z = source.readInt();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public abstract void handle();
}
