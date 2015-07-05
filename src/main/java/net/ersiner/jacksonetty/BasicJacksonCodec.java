package net.ersiner.jacksonetty;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public class BasicJacksonCodec<T> extends ByteToMessageCodec<T> {

    private final Class<T> clazz;
    private final ObjectMapper objectMapper;

    public BasicJacksonCodec(Class<T> clazz, ObjectMapper objectMapper) {
        super(clazz);
        this.clazz = clazz;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, T msg, ByteBuf out) throws Exception {
        ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(out);
        objectMapper.writeValue(byteBufOutputStream, msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBufInputStream byteBufInputStream = new ByteBufInputStream(in);
        out.add(objectMapper.readValue(byteBufInputStream, clazz));
    }
}
