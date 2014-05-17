package org.agecraft.modjam4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MJPacketHandlerClient extends SimpleChannelInboundHandler<MJMessage> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MJMessage msg) throws Exception {
		msg.handle();
	}
}
