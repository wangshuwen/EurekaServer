package com.cst.xinhe.voice.monitor.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ClassName VoiceDataEncoder
 * @Description
 * @Auther lifeng
 * @DATE 2018/11/20 17:08
 * @Vserion v0.0.1
 */
//TODO 声音解码器
public class VoiceDataEncoder extends MessageToByteEncoder<byte[]> {

    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {
        out.writeBytes(msg);
    }
}
