package com.imwj.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wj
 * @create 2023-04-27 11:26
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // 本地ip
        InetAddress host = InetAddress.getLocalHost();
        String ip =host.getHostAddress();
        System.out.println("本机ip地址：" + ip);

        // ping命令
        Process p = Runtime.getRuntime().exec("ping " + "www.baidu.com");
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            if (line.length() != 0) {
                sb.append(line + "\r\n");
            }
        }
        System.out.println(sb.toString());
    }

}
