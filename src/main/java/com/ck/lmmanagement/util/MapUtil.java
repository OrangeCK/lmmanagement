package com.ck.lmmanagement.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/**
 * Title: MapUtil.java  
 * Description: 调用tencent地图的api
 * Copyright: Copyright (c) 2018
 * Company: www.sf-express.com 
 * @author Kang Chen  
 * @date 2018年9月29日 上午10:45:33
 * @version 1.0  
 */
public class MapUtil {
	private final static Logger logger = LoggerFactory.getLogger(MapUtil.class);
	/**
	 * 腾讯地图地址解析(地址转坐标URL)
	 */
	private static String tencentMapAddsToCoorUrl = "https://apis.map.qq.com/ws/geocoder/v1/";
	/**
	 * 腾讯地图的KEY
	 */
	private static String tencentMapkey = "GUKBZ-VM236-UL6SG-MWAVM-BVTHE-N5FZZ";
	/**
	 * 腾讯地图的sk
	 */
	private static String tencentMapSk = "ZdOsF3p7C2VjtX1RoSOo3RPzjEeOAp7";
	private static final String ARITHMETIC_MD5 = "md5";
	/**
	 * 得到坐标
	 * @param address 地址  省市区县**
	 * @return
	 */
	public static Map<String, Object> getCoordinateByAddress(String address){
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("address", address);
        paramsMap.put("key", tencentMapkey);
		String params = "address=" + address + "&key=" + tencentMapkey + "&sn=" + getSn(paramsMap);
		System.out.println("map拼接传入的参数是：" + params + "********************");
		if(StringUtils.isEmpty(tencentMapAddsToCoorUrl) || StringUtils.isEmpty(tencentMapkey) || StringUtils.isEmpty(address)){
			return null;
		}else{
			return analJsonStr(MapUtil.sendHttpsRequestByGet(tencentMapAddsToCoorUrl, params));
		}
	}
	/**
	 * 通过http方式发起GET请求
	 */
	public static String sendHttpsRequestByGet(String url, String params){
		String result = "";
        BufferedReader in = null;
        String urlNameString = url + "?" + params;
        try {
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
			    System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
			    result += line;
			}
		} catch (MalformedURLException e) {
			logger.error("发送GET请求失败1!" + e.getStackTrace());
		} catch (IOException e) {
			logger.error("发送GET请求失败2!" + e.getStackTrace());
		} finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
    			logger.error("发送GET请求失败3!" + e2.getStackTrace());
                e2.printStackTrace();
            }
        }
        return result;
	}
	/**
	 * 解析json字符串
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> analJsonStr(String jsonStr){
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		Integer status = jsonObject.getInteger("status");
		Map<String, Object> map = new HashMap<String, Object>();
		if(status == 0){
			JSONObject jsonResult = (JSONObject) jsonObject.get("result");
			JSONObject jsonLocation = (JSONObject) jsonResult.get("location");
			Double longitude = jsonLocation.getDouble("lng");
			Double latitude = jsonLocation.getDouble("lat");
			map.put("status", status);
			map.put("succFlag", true);
			map.put("longitude", longitude);
			map.put("latitude", latitude);
		}else{
			map.put("status", status);
			map.put("succFlag", false);
		}
		return map;
	}
	
	/**
	 * 对Map内所有value作utf8编码，拼接返回结果
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 */
    public static String toQueryString(Map<?, ?> data)
                    throws UnsupportedEncodingException {
            StringBuffer queryString = new StringBuffer();
            for (Entry<?, ?> pair : data.entrySet()) {
                    queryString.append(pair.getKey() + "=");
                    queryString.append(URLEncoder.encode((String) pair.getValue(),
                                    "UTF-8") + "&");
            }
            if (queryString.length() > 0) {
                    queryString.deleteCharAt(queryString.length() - 1);
            }
            return queryString.toString();
    }
    /**
     * 进行MD5编码
     * @param md5
     * @return
     */
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                            .getInstance(ARITHMETIC_MD5);
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                    sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                                    .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
    /**
     * 获得sn码
     * @param map
     * @return
     */
    public static String getSn(Map<String, String> map) {
    	String sn = ""; 
    	try {
			String paramStr = toQueryString(map);
			paramStr = "/ws/geocoder/v1/?"+ paramStr + tencentMapSk;
			sn = MD5(URLEncoder.encode(paramStr,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("编辑SN码失败");
		}
    	return sn;
    }
	
	public static void main(String[] args) {
		String url = "https://apis.map.qq.com/ws/geocoder/v1/";
		String sk = "ZdOsF3p7C2VjtX1RoSOo3RPzjEeOAp7";
		String sn = "";
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("address", "北京市海淀区彩和坊路海淀西大街74号");
        paramsMap.put("key", "GUKBZ-VM236-UL6SG-MWAVM-BVTHE-N5FZZ");
        String paramsStr = "";
		try {
			paramsStr = toQueryString(paramsMap);
			System.out.println(paramsStr + "***********************");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String param = new String("/ws/geocoder/v1/?"+ paramsStr +sk);
		try {
			sn = MD5(URLEncoder.encode(param,"UTF-8"));
			System.out.println("sn的值是:"+ sn + "*********************");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String params = "address=北京市海淀区彩和坊路海淀西大街74号&key=GUKBZ-VM236-UL6SG-MWAVM-BVTHE-N5FZZ&sn="+sn; 
		String result = sendHttpsRequestByGet(url, params);
		System.out.println("**********************" + result + "*******************");
	}
}
