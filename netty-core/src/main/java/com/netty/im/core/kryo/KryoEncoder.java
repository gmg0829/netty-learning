package com.netty.im.core.kryo;

import com.netty.im.core.message.Message;
import com.netty.im.core.util.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by yr on 2018/8/18.
 */
public class KryoEncoder  extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {
       KryoSerializer.serialize(message,out);
        ctx.flush();
    }

}
