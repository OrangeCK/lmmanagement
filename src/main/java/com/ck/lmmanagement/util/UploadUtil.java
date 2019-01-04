package com.ck.lmmanagement.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * @author 01378803
 * @date 2019/1/4 13:48
 * Description  : 上传工具类
 */
public class UploadUtil {

    /**
     * 上传文件
     * @param multipartFile
     * @param path
     * @return
     */
    public static String uploadFile(MultipartFile multipartFile, String path, String rootFileName) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
        // 获取文件的后缀
        String suffix = rootFileName.substring(rootFileName.lastIndexOf(".") + 1);
        // 获取时间戳
        long currTimestamp = System.currentTimeMillis();
        // 给文件重命名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + currTimestamp + suffix;
        // 输出流
        FileOutputStream fo = new FileOutputStream(path + File.separator + fileName);
        BufferedOutputStream bo = new BufferedOutputStream(fo);
        // 定义字节缓冲区，减少I/O次数，提高读写效率
        byte[] but = new byte[1024];
        int len = 0;
        while ((len = fileInputStream.read(but)) != -1){
            // 简单来说就是将数组but的len个字节按顺序写入输入流，0是偏移量，就是说从0开始的len个字节
            bo.write(but, 0, len);
        }
        // flush的作用是写入磁盘，调用close之前，默认会flush;
        bo.flush();
        bo.close();
        fileInputStream.close();
        fo.close();
        return fileName;
    }
}
