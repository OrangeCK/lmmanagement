package com.ck.lmmanagement.util;

import java.io.UnsupportedEncodingException;

/**
 * Title: TestMain.java  
 * Description:  
 * Copyright: Copyright (c) 2018
 * Company: www.sf-express.com 
 * @author Kang Chen  
 * @date 2018年9月28日 下午5:12:29
 * @version 1.0  
 */
public class WordLengthUtil {

	public static void main( String[] args ) throws UnsupportedEncodingException  
    {  
        String str = "陈康陈康陈康";
        System.out.println(getWordCount(str) + ": *******1*************");  
        System.out.println(getWordCountRegex(str) + ": *******2*************");  
        System.out.println(getWordLengthCode(str,"GBK") + ": *******3*************");  
        System.out.println(getWordLengthCode(str,"UTF-8") + ": *******4*************");  
    }  

    /**
     * 由于Java是基于Unicode编码的，因此，一个汉字的长度为1，而不是2。
     * 但有时需要以字节单位获得字符串的长度。例如，“123abc长城”按字节长度计算是10，而按Unicode计算长度是8。 
     * 为了获得10，需要从头扫描根据字符的Ascii来获得具体的长度。如果是标准的字符，Ascii的范围是0至255，如果是汉字或其他全角字符，Ascii会大于255。 
     * 因此，可以编写如下的方法来获得以字节为单位的字符串长度。
     */
    public static int getWordCount(String s)  
    {  
        int length = 0;  
        for(int i = 0; i < s.length(); i++)  
        {  
            int ascii = Character.codePointAt(s, i);  
            if(ascii >= 0 && ascii <=255) {
                length++;
            }
            else  {
                length += 2;

            }
        }  
        return length;  

    }  

    /**
     * 基本原理是将字符串中所有的非标准字符（双字节字符）替换成两个标准字符（**，或其他的也可以）。这样就可以直接例用length方法获得字符串的字节长度了
     */
    public static  int getWordCountRegex(String s)  
    {  

        s = s.replaceAll("[^\\x00-\\xff]", "**");  
        int length = s.length();  
        return length;  
    }

    /**
     * 按特定的编码格式获取长度
     */
    public static int getWordLengthCode(String str, String code) throws UnsupportedEncodingException{  
        return str.getBytes(code).length;  
    }  

}
