package com.imwj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author wj
 * @create 2023-04-25 14:39
 */
public class FileUtil {

    /**
     * 拆分文件
     * @param metaFile 源文件
     * @param kb 每个文件大小
     * @throws IOException
     */
    public static void departFile(File metaFile, int kb) throws IOException {
        // 将kb转换为字节
        int pageSize = kb * 1024;
        // 得到一个输入流
        FileInputStream fis = new FileInputStream(metaFile);
        long n = metaFile.length()%pageSize == 0?metaFile.length() / pageSize : metaFile.length() / pageSize + 1;
        for(int i = 0; i < n; i++){
            // 只有最后一个文件大小是不一致的
            byte[] part = new byte[i < n - 1? pageSize : (int)(metaFile.length() % pageSize)];
            // 读取文件到part数组中
            fis.read(part);
            // 创建新分片文件（文件绝对路径）
            File temp = new File(metaFile.getAbsolutePath() + "-" + i);
            temp.createNewFile();
            FileOutputStream fos = new FileOutputStream(temp);
            // 写到文件中
            fos.write(part);
            fos.close();
        }
        fis.close();
    }

    /**
     * 文件合并
     * @param metaFile 需要合并的文件
     * @throws IOException
     */
    public static void unionFile(File metaFile) throws IOException {
        File[] files = new File[0];
        byte[] all = new byte[0];
        // 查找所有的文件分片
        for(int i = 0; i < Integer.MAX_VALUE; i++){
            File file = new File(metaFile.getAbsolutePath() + "-" + i);
            // 如果文件不存在说明已经找到所有的分片文件
            if(!file.exists()){
                break;
            }
            // 数组长度增加1
            files = Arrays.copyOf(files, files.length + 1);
            // 将文件存放到指的数组位置
            files[files.length - 1] = file;
        }
        // 遍历文件数组
        for (int i = 0; i < files.length; i++) {
            // 每次创建子串，然后用System.arraycopy将新串连接到all串的末尾
            byte[] tmp = new byte[(int) files[i].length()];
            // all数组长度增加
            all = Arrays.copyOf(all, (i+1) * (int) files[i].length());
            // 文件转换为输入流
            FileInputStream fis = new FileInputStream(files[i]);
            // 读取整个文件
            fis.read(tmp);
            // 将文件拼接
            System.arraycopy(tmp, 0, all, (int) (i * files[i].length()), (int) files[i].length());
            fis.close();
        }
        File newFile = new File(metaFile.getParent(), "New " + metaFile.getName());
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(all);
        fos.close();

    }





}
