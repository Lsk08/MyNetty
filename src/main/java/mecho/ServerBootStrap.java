package mecho;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by yuan on 2018/3/16.
 */
//EventLoopGroup 相当于一个线程池  其中装满了EventLoop也就相当于线程
    //ServerBootStrap用来bind本地端口  并且需要2个线程池  一个负责绑定端口 并且监听客户端的请求  另一个负责处理这个请求
    //BootStrap 用connect来连接远程主机  并且只需要1个线程池
public class ServerBootStrap {

    public static void main(String[] args) {

        //Serverbootstrap
        ServerBootstrap bootstrap=new ServerBootstrap();
        //boss worker
        EventLoopGroup boss=new NioEventLoopGroup();
        EventLoopGroup worker=new NioEventLoopGroup();
        //设置线程组
        bootstrap.group(boss,worker);
        //设置channelFactory的类型  netty根据这个类型获得工厂实例
        //NioServerSocketChannel
        bootstrap.channel(NioServerSocketChannel.class);
        //添加handler SocketChannel
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new ServerHandler());
            }
        });

        ChannelFuture future= null;
        try {
            future = bootstrap.bind(10101).sync();
            System.out.println("start!!");

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }



    }
}
