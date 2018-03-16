package mecho;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Scanner;

/**
 * Created by yuan on 2018/3/16.
 */
//todo nettyInAction
public class ClientBootStrap {

    public static void main(String[] args) {
        //Client没有Server
        Bootstrap bootstrap=new Bootstrap();

        EventLoopGroup group=new NioEventLoopGroup();

        bootstrap.group(group);

        //client没有Server
        bootstrap.channel(NioSocketChannel.class);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) throws Exception {
               // socketChannel.pipeline().addLast(new ClientHandler());
            }
        });

        try {
            ChannelFuture future=bootstrap.connect("127.0.0.1",10101).sync();

            //获取buffer的方法  1 channel 或者 ctx  2 ByteBufAllocator  3 unpooled
            ByteBufAllocator alloc = future.channel().alloc();
            //ByteBufAllocator allcator = ByteBufAllocator.DEFAULT;

        //客户端写
            //ChannelFuture cf= future.channel().writeAndFlush("aa");
//            cf.addListener(new ChannelFutureListener() {
//                @Override
//                public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                      if(channelFuture.isSuccess()){
// }
//                }else{
//                  }
//            });
//  Channel的所有操作都保证线程安全  可以多线程操作


            Scanner sc=new Scanner(System.in);
            while(true){

                ByteBuf buffer=alloc.buffer();
                System.out.println("请输入");
                String s=sc.nextLine();
                buffer.writeBytes(s.getBytes());
                future.channel().writeAndFlush(buffer);//一定要flush 不然无法接受到
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
