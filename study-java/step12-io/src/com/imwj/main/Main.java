package com.imwj.main;

import com.imwj.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

/**
 * @author wj
 * @create 2023-04-25 11:24
 */
public class Main {

    public static void main(String[] args) throws Exception {
        File f = new File("E:/test.txt");
        System.out.println("当前文件是：" +f);
        //文件是否存在
        System.out.println("判断是否存在："+f.exists());

        //是否是文件夹
        System.out.println("判断是否是文件夹："+f.isDirectory());

        //是否是文件（非文件夹）
        System.out.println("判断是否是文件："+f.isFile());

        //文件长度
        System.out.println("获取文件的长度："+f.length());

        //文件最后修改时间
        long time = f.lastModified();
        Date d = new Date(time);
        System.out.println("获取文件的最后修改时间："+d);
        //设置文件修改时间为1970.1.1 08:00:00
        f.setLastModified(0);

        //文件重命名
        File f2 =new File("E:/test1.txt");
        f.renameTo(f2);
        System.out.println("把test.txt改名成了test1.txt");

        // 字节流输入到内存
        try {
            File f1 = new File("e:/test1.txt");
            // 创建基于文件的输入流
            FileInputStream fis = new FileInputStream(f1);
            // 通过这个输入流，就可以把数据从硬盘，读取到Java的虚拟机中来，也就是读取到内存中
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 字节流输出
        try {
            // 准备文件lol2.txt其中的内容是空的
            File f3 = new File("e:/test3.txt");
            // 准备长度是2的字节数组，用88,89初始化，其对应的字符分别是X,Y
            byte data[] = { 88, 89 };

            // 创建基于文件的输出流
            FileOutputStream fos = new FileOutputStream(f3);
            // 把数据写入到输出流
            fos.write(data);
            // 关闭输出流
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //  拆分文件和合并文件
        // FileUtil.departFile(new File("C:\\Users\\29168\\Desktop\\《手撸-Spring》-•-小傅哥.pdf"), 1024);

        FileUtil.unionFile(new File("E:\\test\\《手撸-Spring》-•-小傅哥.pdf"));

    }




}
