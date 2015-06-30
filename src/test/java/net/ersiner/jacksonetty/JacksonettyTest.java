package net.ersiner.jacksonetty;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import org.junit.Assert;
import org.junit.Test;

public class JacksonettyTest {

    @Test
    public void testJacksonetty() {

        ObjectMapper objectMapper = new ObjectMapper();
        BasicJacksonCodec<Operation> jacksonCodec = new BasicJacksonCodec<>(Operation.class, objectMapper);
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new LineBasedFrameDecoder(1024),
                new NewlineAppender(),
                jacksonCodec);

        // {"opr":"+","opd1":1.5,"opd2":2.5}
        embeddedChannel.writeOutbound(new Operation('+', 1.5f, 2.5f));

        // {"opr":"*","opd1":5.0,"opd2":7.0}
        embeddedChannel.writeInbound(
                Unpooled.copiedBuffer("{\"opr\":\"*\",\"opd1\":5.0,\"opd2\":7.0}\r\n", CharsetUtil.UTF_8));

        embeddedChannel.flush();
        embeddedChannel.finish();

        ByteBuf outbound = (ByteBuf) embeddedChannel.readOutbound();
        Assert.assertEquals("{\"opr\":\"+\",\"opd1\":1.5,\"opd2\":2.5}\r\n", outbound.toString(CharsetUtil.UTF_8));
        Operation inbound = (Operation) embeddedChannel.readInbound();
        Assert.assertEquals(new Operation('*', 5, 7), inbound);
    }
}
