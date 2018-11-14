package com.ck.lmmanagement.exception;

/**
 * @author 01378803
 * @date 2018/11/14 16:37
 * Description  :
 */
public class MyException extends Exception {
    private static final long serialVersionUID = 1L;
    /**
     * 异常代码
     */
    private String code;
    /**
     * 异常信息
     */
    private String msg;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MyException(){}

    public MyException(String msg){
        this.msg = msg;
    }

    public MyException(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
