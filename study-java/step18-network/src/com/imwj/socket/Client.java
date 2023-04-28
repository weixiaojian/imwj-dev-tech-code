package com.imwj.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author wj
 * @create 2023-04-27 11:29
 */
public class Client {
    public static void main(String[] args)  {

        try {
            //连接到本机的8888端口
            Socket s = new Socket("127.0.0.1",8888);
            // 打开输出流
            OutputStream os = s.getOutputStream();
            // 发送数字110到服务端
            os.write(110);
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
