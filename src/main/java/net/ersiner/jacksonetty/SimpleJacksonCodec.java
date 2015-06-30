package net.ersiner.jacksonetty;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public class SimpleJacksonCodec<T> extends ByteToMessageCodec<T> {
    private final ObjectMapper objectMapper;
    private final Class<T> clazz;

    public SimpleJacksonCodec(ObjectMapper objectMapper, Class<T> clazz) {
        super(clazz);
        this.objectMapper = objectMapper;
        this.clazz = clazz;
    }

    public SimpleJacksonCodec(ObjectMapper objectMapper, Class<T> clazz, boolean preferDirect) {
        super(clazz, preferDirect);
        this.objectMapper = objectMapper;
        this.clazz = clazz;
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
