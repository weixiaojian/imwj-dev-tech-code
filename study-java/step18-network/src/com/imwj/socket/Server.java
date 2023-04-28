package com.imwj.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wj
 * @create 2023-04-27 11:28
 */
public class Server {

    public static void main(String[] args)  {
        try {
            //服务端打开端口8888
            ServerSocket ss = new ServerSocket(8888);

            //在8888端口上监听，看是否有连接请求过来
            System.out.println("监听在端口号:8888");
            Socket s =  ss.accept();
            //打开输入流
            InputStream is = s.getInputStream();

            //读取客户端发送的数据
            int msg = is.read();
            //打印出来
            System.out.println(msg);

            s.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
