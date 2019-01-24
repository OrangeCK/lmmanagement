package com.ck.lmmanagement.util;

import org.springframework.util.Base64Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 01378803
 * @date 2019/1/24 10:00
 * Description  : md5加密工具
 */
public class Md5Util {

    private static final String ARITHMETIC_MD5 = "md5";

    /**
     * 进行MD5加密
     * @param data
     * @return
     */
    public static String encryptToMD5(String data){
        MessageDigest md;
        try {
            //利用哈希算法，MD5
            md = MessageDigest.getInstance(ARITHMETIC_MD5);
            //面向字节处理，所以可以处理字节的东西，如图片，压缩包。。
            byte[] input = data.getBytes();
            byte[] output = md.digest(input);
            //将md5处理后的output结果利用Base64转成原有的字符串,不会乱码
            String str = Base64Utils.encodeToString(output);
            return str;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MD5加密失败");
            return "";
        }
    }
}
