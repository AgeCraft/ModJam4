package org.agecraft.modjam4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;

public class MJCodec extends FMLIndexedMessageToMessageCodec<MJMessage> {

	public MJCodec() {
		addDiscriminator(0, MJMessage.class);
	}
	
	public MJCodec(Class<? extends MJMessage>... messages) {
		for(int i = 0; i < messages.length; i++) {
			addDiscriminator(i, messages[i]);
		}		
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, MJMessage msg, ByteBuf target) throws Exception {
		msg.encodeTo(target);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, MJMessage msg) {
		msg.decodeFrom(source);
	}
}
