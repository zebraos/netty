package com.netty.client;

import com.netty.common.FileUploadFile;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/*
 * @author mal
 * @date 2019/12/10 26:56
 * client文件上传消息处理类
 */

public class FileUploadClient {
    public void connect(int port, String host,
                        final FileUploadFile fileUploadFile) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline()
                                    .addLast(
                                            new ObjectDecoder(
                                                    ClassResolvers
                                                            .weakCachingConcurrentResolver(null)));
                            ch.pipeline()
                                    .addLast(
                                            new FileUploadClientHandler(
                                                    fileUploadFile));
                        }
                    });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
            System.out.println("FileUploadClient connect()结束");
        } finally {
            group.shutdownGracefully();
        }
    }

}
