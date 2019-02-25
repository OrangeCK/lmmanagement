package com.ck.lmmanagement.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Title: BSPUtil.java  
 * Description: BSP的工具 
 * Copyright: Copyright (c) 2018
 * @author Kang Chen
 * @date 2018年9月25日 上午11:14:16
 * @version 1.0  
 */
public class BspUtil {
	/**
	 * 连接URL
	 */
	private static String httpBspUrl = "";
	/**
	 * 接入编码(顾客编码)
	 */
	private static String clientCode = "";
	/**
	 * 校验码
	 */
	private static String checkWord = "";
	/**
	 * bsp下订单
	 * @return
	 */
	public static String orderByBsp(String xml){
		// 加密报文，拼接传入参数
		String params = getHttpParams(xml, checkWord);
		return connectBspByHttp(httpBspUrl, params);
	}
	/**
	 * 加密报文，拼接传入参数
	 * @param xml 报文
	 * @param checkWord 校验码
	 * @return
	 */
	public static String getHttpParams(String xml, String checkWord){
		String verifyCodeWithOutEncode = xml+checkWord;
		// 进行MD5和Base64的加密得到真正的校验码
		String verifyCode = Md5Util.encryptToMD5(verifyCodeWithOutEncode);
		String params = "";
		try {
			// 拼接参数
			params = "xml=" + URLEncoder.encode(xml,"utf-8") + "&verifyCode=" + URLEncoder.encode(verifyCode,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return params;
	}
	/**
	 * 调用BSP的连接 
	 * @param bspUrl BSP的连接地址
	 * @param param 传入的参数
	 * @return
	 */
	public static String connectBspByHttp(String bspUrl, String param){
		String result = "";
        DataOutputStream dataout = null;
        BufferedReader br = null;
		try {
			URL url = new URL(bspUrl);
			// 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
            connection.setDoOutput(true);
            // 设置连接输入流为true
            connection.setDoInput(true);
            // 设置请求方式为post
            connection.setRequestMethod("POST");
            // post请求缓存设为false
            connection.setUseCaches(false);
            // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setInstanceFollowRedirects(true);
            // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的form参数) application/x-www-form-urlencoded->表单数据
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            connection.connect();
            // 创建输入输出流,用于往连接里面输出携带的参数
            dataout = new DataOutputStream(connection.getOutputStream());
            // 将参数输出到连接
            dataout.writeBytes(param);
            // 输出完成后刷新并关闭流
            dataout.flush();
            // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            // 销毁连接
            connection.disconnect();
            result = sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(dataout != null){
					dataout.close();	
				}
				if(br != null){
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
