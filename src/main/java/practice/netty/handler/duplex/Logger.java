package practice.netty.handler.duplex;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Logger extends ChannelDuplexHandler {
    private final String host;
    private final boolean endOfPipeline;

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        log.info(host + " : write " + msg.toString());
        ctx.write(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info(host + " : active");
        if (!endOfPipeline) {
            ctx.fireChannelActive();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info(host + " : inactive");
        if (!endOfPipeline) {
            ctx.fireChannelInactive();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info(host + " : read " + msg.toString());
        if (!endOfPipeline) {
            ctx.fireChannelRead(msg);
        }
    }
}
