package com.ck.lmmanagement.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Title: PoNumUtil.java  
 * Description: 订单号的生成  
 * Copyright: Copyright (c) 2018
 * Company: www.sf-express.com 
 * @author Kang Chen  
 * @date 2018年9月25日 下午5:27:52
 * @version 1.0  
 */
public class NumUtil {
	/**
	 * 随机产生客户订单号
	 * @return
	 */
	public static String getCustOrderNum() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String newDate=sdf.format(new Date());
		String result="";
		Random random=new Random();
		for(int i=0;i<3;i++){
			result += random.nextInt(100);
		}
		result = "CCOP" + result + newDate;
		return result;
	}
}
