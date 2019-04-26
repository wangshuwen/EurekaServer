package com.cst.xinhe.station.monitor.server.handle;

import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.station.monitor.server.channel.ChannelMap;
import com.cst.xinhe.station.monitor.server.client.KafkaClient;
import com.cst.xinhe.station.monitor.server.request.SingletonStationClient;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;


/**
 * @description:  StationServerHandler
 * @author: lifeng
 * @date: 2019-04-26
 */
@Sharable
@Component
public class StationServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 日志
     */
    private Logger log = LoggerFactory.getLogger(getClass());

    public StationServerHandler() {
    }

    @Autowired
    private KafkaClient kafkaClient;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("服务端收到消息:"+msg);
        ResponseData resp = new ResponseData();
        if(msg instanceof RequestData){
            RequestData reqMsg = (RequestData)msg;
            //如果请求信息的帧头为ox6688
            if(reqMsg.getType()== ConstantValue.MSG_HEADER_FREAME_HEAD){
                int cmd = reqMsg.getCmd();
                byte[] body = reqMsg.getBody();
                int ndName = reqMsg.getNdName();

                switch (cmd) {
                    case ConstantValue.MSG_HEADER_COMMAND_ID_REQUEST:
                        log.info("采集数据上报");
                        switch (ndName) {
                            case ConstantValue.MSG_BODY_NODE_NAME_SELFCHECK_RESULT:
//                                upLoadService.sendSelfCheckResult(reqMsg);
                                kafkaClient.sendData("",reqMsg);
                                reqMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                                reqMsg.setResult(ConstantValue.MSG_BODY_RESULT_ERROR);
                                resp.setCustomMsg(reqMsg);
                                SingletonStationClient.getSingletonStationClient().sendCmd(resp);
                                log.info("自检结果");
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_SOFTWARE_VERSION:
//                                upLoadService.sendSoftWareVersion(reqMsg);
                                kafkaClient.sendData("",reqMsg);
                                log.info("软件版本号");
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_HANDWARE_VERSION:
                                kafkaClient.sendData("",reqMsg);
//                                upLoadService.sendHandWareVersion(reqMsg);
                                log.info("硬件版本号");
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_UPDATE_IP:
                                log.info("更新基站的IP");
//                                upLoadService.sendStationUpLoadIp(reqMsg);
                                kafkaClient.sendData("",reqMsg);
                                reqMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                                reqMsg.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
                                resp.setCustomMsg(reqMsg);
                                SingletonStationClient.getSingletonStationClient().sendCmd(resp);
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_MAC_STATION:
                                log.info("基站WiFi探针搜索到基站");
//                                upLoadService.sendUpLoadMacStation(reqMsg);
                                kafkaClient.sendData("",reqMsg);
                                reqMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                                reqMsg.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
                                resp.setCustomMsg(reqMsg);
                                SingletonStationClient.getSingletonStationClient().sendCmd(resp);
                                break;
                            default:
                                log.error("未知命令包");
                                reqMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                                reqMsg.setResult(ConstantValue.MSG_BODY_RESULT_ERROR);
                                resp.setCustomMsg(reqMsg);
                                SingletonStationClient.getSingletonStationClient().sendCmd(resp);
                                break;
                        }
                        break;
                    case ConstantValue.MSG_HEADER_COMMAND_ID_HEARTBEAT:
                        log.info("心跳数据");
                        reqMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                        reqMsg.setLength(34);
                        reqMsg.setResult((byte) 0x55);
                        reqMsg.setNodeCount((byte) 0x00);
                        resp.setCustomMsg(reqMsg);
                        SingletonStationClient.getSingletonStationClient().sendCmd(resp);
                        break;
                    default:
                        log.error("未知命令");
                        break;
                }
            }
        }
        ReferenceCountUtil.release(msg);
    }

    /**
     * @param [ctx]
     * @return void
     * @description 建立连接 执行的方法
     * @date 17:35 2018/11/21
     * @auther lifeng
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inSocket.getAddress().getHostAddress();
        int port = inSocket.getPort();
        StringBuffer sb = new StringBuffer(clientIP);
        sb.append(":");
        sb.append(port);
        String str = sb.toString();
        log.info("基站[" + str + "] 连接成功");
        ChannelMap.addChannel(str, ctx.channel());
        log.info("基站[" + str + "] 加入session");
        log.info("当前连接基站数量" + ChannelMap.getChannelNum());
    }

    /**
     * @param [ctx]
     * @return void
     * @description 断开连接执行的方法
     * @date 17:35 2018/11/21
     * @auther lifeng
     **/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inSocket.getAddress().getHostAddress();
        int port = inSocket.getPort();
        StringBuffer sb = new StringBuffer(clientIP);
        sb.append(":");
        sb.append(port);
        String str = sb.toString();
        log.info("基站[" + str + "] 已断开连接");
        //TODO 基站的掉线处理      去掉注释
        //ProcessOfflineStationCount.getSingletonProcessOffline().process(clientIP, port);
        ChannelMap.removeChannelByName(str);
        log.info("基站[" + str + "] 被移出session");

//        删除该基站的更新IP
//        String[] split = clientIP.split("\\.");
//        String terminalIp = split[2] + "." + split[3];
//        TerminalUpdateIp terminalUpdateIp = terminalUpdateIpMapper.findTerminalIdByIpAndPort(clientIP,port);
//        if(terminalUpdateIp!=null)
//        terminalUpdateIpMapper.deleteByPrimaryKey(terminalUpdateIp.getId());
    }

    /**
     * @param [ctx]
     * @return void
     * @description 数据读取完成执行方法
     * @date 17:35 2018/11/21
     * @auther lifeng
     **/
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inSocket.getAddress().getHostAddress();
        int port = inSocket.getPort();
        StringBuffer sb = new StringBuffer(clientIP);
        sb.append(":");
        sb.append(port);
        String str = sb.toString();
        log.info("读取基站[" + str + "] 数据完成");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("移除客户端");
    }

    /**
     * @param [ctx, evt]
     * @return void
     * @description 心跳处理
     * @date 17:36 2018/11/21
     * @auther lifeng
     **/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inSocket.getAddress().getHostAddress();
        int port = inSocket.getPort();
        StringBuffer sb = new StringBuffer(clientIP);
        sb.append(":");
        sb.append(port);
        String str = sb.toString();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                //TODO 读超时
                ctx.channel().close();
                ChannelMap.removeChannelByName(str);
            }
            if (event.state() == IdleState.WRITER_IDLE) {
                //TODO 写超时
                ctx.channel().close();
                ChannelMap.removeChannelByName(str);
            }
            if (event.state() == IdleState.ALL_IDLE) {
                //清除超时会话
                ChannelFuture writeAndFlush = ctx.writeAndFlush("client will be remove");
                writeAndFlush.addListener((ChannelFutureListener) future -> {
                    //TODO 通知web端显示并存储数据库
                    //TODO 基站掉线
                    ctx.channel().close();
                    ChannelMap.removeChannelByName(str);
                });
            }
        } else {
            super.userEventTriggered(ctx, evt);
            ChannelMap.removeChannelByName(str);
        }
    }

    /**
     * @param [ctx, cause]
     * @return void
     * @description 异常处理方法
     * @date 17:37 2018/11/21
     * @auther lifeng
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inSocket.getAddress().getHostAddress();
        int port = inSocket.getPort();
        StringBuffer sb = new StringBuffer(clientIP);
        sb.append(":");
        sb.append(port);
        String str = sb.toString();
        log.error("基站[" + str + "] 出现异常" + cause.getLocalizedMessage());
        ChannelMap.removeChannelByName(str);
        cause.printStackTrace();
    }
}
