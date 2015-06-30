package net.ersiner.jacksonetty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class NewLineAppender extends ChannelOutboundHandlerAdapter {

    private static byte[] newLineBytes = new byte[] { '\r', '\n' };

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf out = (ByteBuf) msg;
            out.writeBytes(newLineBytes);
        }
        ctx.write(msg, promise);
    }
}
