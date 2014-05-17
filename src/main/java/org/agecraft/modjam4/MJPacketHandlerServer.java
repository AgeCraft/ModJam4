package org.agecraft.modjam4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MJPacketHandlerServer extends SimpleChannelInboundHandler<MJMessage> {
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MJMessage msg) throws Exception {
		msg.handle();
	}
}
