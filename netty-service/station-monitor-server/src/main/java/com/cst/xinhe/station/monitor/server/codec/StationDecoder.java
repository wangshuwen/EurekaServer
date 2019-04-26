package com.cst.xinhe.station.monitor.server.codec;


import com.cst.xinhe.common.netty.data.request.RequestData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangshuwen
 * @Description: 基站解码
 * @Date 2018/11/23/11:03
 */
public class StationDecoder extends LengthFieldBasedFrameDecoder{

    Logger logger = LoggerFactory.getLogger(StationDecoder.class);

    //头部信息的大小  2+4+4+2+2+2+2+2+6=26
    private static final int HEAD_SIZE=26;

    //类型  系统编号 0xAB 表示A系统，0xBC 表示B系统
    private int type;


    private int terminalId;

    private int stationId;

    private int terminalIp1;
    private int terminalIp2;

    private int stationIp1;
    private int stationIp2;

    private int length;  //主题信息的长度

    private int cmd;
    private int sequenceId;

    private Date time;

    private byte rt1;
    private byte rt2;
    private byte rt3;
    private byte rt4;
    private byte rt5;
    private byte rt6;

    private int stationPort;

    private int terminalPort;
    private byte result;

    private byte nodeCount;

    private int ndName;

    //主题信息

    private Integer crc;

    /**
     *
     * @param maxFrameLength       解码时，处理每个帧数据的最大长度   544
     * @param lengthFieldOffset     该帧数据中，存放该帧数据的长度的数据的起始位置 14
     * @param lengthFieldLength      记录该帧数据长度的字段本身的长度    4
     * @param lengthAdjustment      修改帧数据长度字段中定义的值，可以为负数
     * @param initialBytesToStrip   解析的时候需要跳过的字节数
     * @param failFast             为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常
     */
    public StationDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if(in==null||in.readableBytes()<HEAD_SIZE){
            return null;
        }
        //标记当前readIndex的位置
        in.markReaderIndex();


        //注意在读的过程中，readIndex的指针也在移动  -16
        type = (((in.readByte() & 0xff) << 8) + (in.readByte() & 0xff));

        logger.info("数据帧头: " + type);
        terminalId = ((in.readByte() & 0xff) << 24) + ((in.readByte() & 0xff) << 16) + ((in.readByte() & 0xff) << 8) + (in.readByte() & 0xff);
        stationId = ((in.readByte() & 0xff) << 24) + ((in.readByte() & 0xff) << 16) + ((in.readByte() & 0xff) << 8) + (in.readByte() & 0xff);

        logger.info("终端ID : " + terminalId);
        logger.info("基站ID : " + stationId);

        terminalIp1 = in.readByte() & 0xff;
        terminalIp2 = in.readByte() & 0xff;

        stationIp1 = in.readByte() & 0xff;
        stationIp2 = in.readByte() & 0xff;
        logger.info("终端IP : " + terminalIp1 + "." + terminalIp2);
        logger.info("基站IP : " + stationIp1 + "." + stationIp2);

        terminalPort = ((in.readByte() & 0xff) << 8) + (in.readByte() & 0xff);
        stationPort = ((in.readByte() & 0xff) << 8) + (in.readByte() & 0xff);

        logger.info("终端PORT : " + terminalPort);
        logger.info("基站PORT : " + stationPort);

        length = (((in.readByte() & 0xff) << 8) + (in.readByte() & 0xff));
        logger.info("数据帧总长度 : " + length);


        cmd = ((in.readByte() & 0xff) << 8) + (in.readByte() & 0xff);
        logger.info("控制类型 : " + (cmd & 0xff));
        sequenceId = ((in.readByte() & 0xff) << 8) + (in.readByte() & 0xff);
        logger.info("上传序列号 : " + sequenceId);
        rt1 = in.readByte();

        rt2 = in.readByte();
        rt3 = in.readByte();
        rt4 = in.readByte();
        rt5 = in.readByte();
        rt6 = in.readByte();

        StringBuilder rt = new StringBuilder("20");
        rt.append((int) rt1).append("-").append((int) rt2)
                .append("-").append((int) rt3).append(" ").append((int) rt4).append(":").append((int) rt5).append(":").append((int) rt6);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        time = simpleDateFormat.parse(rt.toString());
        logger.info("上传时间 : " + time);


        result = in.readByte();
        nodeCount = in.readByte();
        ndName = (((in.readByte() & 0xff) << 8) + (in.readByte() & 0xff));
        int c = in.readableBytes();
        logger.info("剩余可读取长度：" + c);
        int len = length - 34;
        if (c < (len)) {
            //重置当前的readerindex为beginindex
//            in.readerIndex(beginIndex);
            in.resetReaderIndex();
//            throw new Exception("body应该又的长度为："+length+",实际获得长度为：" + c + ",须等待下一包");
            return null;
        }
        ByteBuf buf = in.readBytes(len);
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
//        System.out.println("==============");
//        for (int i = 0; i < req.length; i++) {
//            System.out.printf("0x%02x ", req[i]);
//        }
//        System.out.println("\n==============");
        byte[] body = new byte[req.length];

        System.arraycopy(req, 0, body, 0, req.length);
        buf.release();
//        crc = (((in.readByte() & 0xff) << 8) + (in.readByte() & 0xff));
        RequestData customMsg = new RequestData(type, terminalId, stationId, terminalIp1, terminalIp2, stationIp1, stationIp2, terminalPort, stationPort, length, cmd, sequenceId, time, result, nodeCount, ndName, body);

        return customMsg;

    }
}
