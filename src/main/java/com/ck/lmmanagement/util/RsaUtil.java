package com.ck.lmmanagement.util;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 01378803
 * @date 2019/1/24 10:21
 * Description  : RSA非对称加密工具
 */
public class RsaUtil {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * 生成密钥对(公钥和私钥)
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
    /**
     * 用私钥对信息生成数字签名
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encodeToString(signature.sign());
    }
    /**
     * 校验数字签名
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decodeFromString(sign));
    }
    /**
     * 私钥解密
     * @param encryptedData
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
    /**
     * 公钥解密
     * @param encryptedData
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
    /**
     * 公钥加密
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
    /**
     * 私钥加密
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
    /**
     * 获取私钥
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encodeToString(key.getEncoded());
    }
    /**
     * 获取公钥
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encodeToString(key.getEncoded());
    }
    /**
     * java端公钥加密
     * @param data
     * @param PUBLICKEY
     * @return
     */
    public static String encryptedDataOnJava(String data, String PUBLICKEY) {
        try {
            data = Base64Utils.encodeToString(encryptByPublicKey(data.getBytes(), PUBLICKEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    /**
     * java端私钥解密
     * @param data
     * @param PRIVATEKEY
     * @return
     */
    public static String decryptDataOnJava(String data, String PRIVATEKEY) {
        String temp = "";
        try {
            byte[] rs = Base64Utils.decodeFromString(data);
            //以utf-8的方式生成字符串
            temp = new String(RsaUtil.decryptByPrivateKey(rs, PRIVATEKEY),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static void main(String[] args) throws Exception {
    //	   Map<String, Object> map = genKeyPair();
    //	   String PUBLICKEY = RSAUtil.getPublicKey(map);
    //	   String PRIVATEKEY = RSAUtil.getPrivateKey(map);
        String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCtcwKni/Yok99cN5E+CnvVxymkVQZ+BYdXPVcTI7uq6HJdob+sQTenmwovFjg38yJb89E1L1GnXA1v22jDutEpP2B7d7p/gF/FIpXuGAaMG4CAuou4193qwo7rgZW3oS1Kx8HMArFbD0YkVWNivqVfOoDwbSYnekFJ0HtFrBf4JQIDAQAB";
        String PRIVATEKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK1zAqeL9iiT31w3kT4Ke9XHKaRVBn4Fh1c9VxMju6rocl2hv6xBN6ebCi8WODfzIlvz0TUvUadcDW/baMO60Sk/YHt3un+AX8Uile4YBowbgIC6i7jX3erCjuuBlbehLUrHwcwCsVsPRiRVY2K+pV86gPBtJid6QUnQe0WsF/glAgMBAAECgYAPgPJBy67rHZyff5AJ/aHoD+9W8wqrhQ7960u3OEB0HGbsm6KoYQP6hU85ukC4L5FoqriOinZAAWhqETCiHjbH1WJa0yE4iuTwEOF4zuugzSjqZdkzQTs27lYDAkdXsKJ7ZcytrCRJKMUAqm7MpfBoZ42DPwyzVxukdJChZYGIQQJBANp7goD08oyqtY00PVRnY4iznDt6Kt0yMaVU0KJ2XRjx5IGGzM5GfxlLSev8bAO0mN4zr1fi+Lp2HqQavs1eB+0CQQDLO9c/1m731n5qv+xa5S31OioI6zlxV4xL8h1OXqJKQbDnOKj9nkg0o9NbWpfHkVEoCgHd8HvCMqvoPlq547oZAkEAp348RCSEtbZBTKkZw1Unhys6wJYdukm6qabl80aOhIscxPfUluaQlZGUcCepnwWak+5HpDbKvBfF0cT3NXW62QJASzjl7dHvlroEZnyM/gOvpAGYR17RDxuJaR6/LB+oz/VKhfqZye2I6jrs4vZAEXS+5iP2FvCIzhM1P6u8puscGQJAGodx7RHuV38D9rGP0/+1iZxMYnVV6X9fXZro5fP/Uwci5q3K9u7sGvA+G6yYpGn/k51ZHStF04lbKKMXkDUZxA==";
        String data = "01378805.ccop";
        System.out.println("公钥是："+PUBLICKEY);
        String encodeTomd5 = Md5Util.encryptToMD5(data);
        System.out.println("MD5加密是："+ encodeTomd5);
        System.out.println("私钥是："+PRIVATEKEY);
        String encryptData = RsaUtil.encryptedDataOnJava(data, PUBLICKEY);
        System.out.println("加密后数据是：" + encryptData);
        String encodeURl = URLEncoder.encode(encryptData,"UTF-8");
        System.out.println("加密后数据经过URL编码是：" + encodeURl);
        String decodeURL = URLDecoder.decode(encodeURl,"UTF-8");
        System.out.println("加密后数据经过URL编码是：" + decodeURL);
        System.out.println("加密后数据经过URL编码位数是：" + decodeURL.length());
        System.out.println("解密后数据是：" + RsaUtil.decryptDataOnJava(encryptData, PRIVATEKEY));

    }
}
