package com.netty;

import com.netty.client.FileUploadClient;
import com.netty.client.NettyClient;
import com.netty.common.FileUploadFile;
import com.netty.protocol.RpcRequest;
import io.netty.channel.Channel;

import java.io.File;
import java.util.UUID;

/*
 * @author mal
 * @date 2019/12/10 26:56
 * nettyClient
 */
public class Main {

    public static void main(String[] args) throws Exception {
//        NettyClient client = new NettyClient("127.0.0.1", 8081);
//        //启动client服务
//        client.start();
//
//        Channel channel = client.getChannel();
//        //消息体
//        RpcRequest request = new RpcRequest();
//        request.setId(UUID.randomUUID().toString());
//        request.setData("maliang/client");
//        //channel对象可保存在map中，供其它地方发送消息
//        channel.writeAndFlush(request);
        FileUploadFile uploadFile = new FileUploadFile();
        File file = new File("D:/b.txt");
        String fileMd5 = file.getName();// 文件名
        uploadFile.setFile(file);
        uploadFile.setFile_md5(fileMd5);
        uploadFile.setStarPos(0);// 文件开始位置
        new FileUploadClient().connect(9991, "127.0.0.1", uploadFile);
    }
}
