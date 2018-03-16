package mecho;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by yuan on 2018/3/16.
 */
//netty4 使用ByteBuf 作为信息的容器 在channel之间传递  netty3 是ChannelBuffer
    //SimpleChannelInboundHandler 是最常见的Handler  由于netty是单线程运行 所以 必须保证每个方法不要阻塞io
    //否则可以在注册handler的时候指定一个EventExecutorGroup  这个ExecutorGroup将保存在这个pipline的context里
    //ExecutorGroup 其实就是juc的Executor的子类 在DefaultChannelContext
public class ServerHandler extends SimpleChannelInboundHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("Channel Active!!");

        super.channelActive(ctx);
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

        ByteBuf buffer=(ByteBuf) o;

        byte[] data=new byte[buffer.readableBytes()];

        buffer.readBytes(data);

        System.out.println(new String(data));

        channelHandlerContext.write(o);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println(cause.toString());

        super.exceptionCaught(ctx, cause);
    }
}
