package com.ck.lmmanagement.domain;

import org.springframework.util.StringUtils;

/**
 * @author 01378803
 * @date 2018/11/2 14:43
 * Description  : 返回json格式类
 */
public class ResultData {
    /**
     * 返回提示信息
     */
    private String msg;
    /**
     * 返回code代码
     */
    private Integer code = 200;
    /**
     * 返回内容
     */
    private Object data;
    /**
     * 返回状态
     */
    private String status = "succ";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultData(){

    }

    public ResultData(String msg){
        this.msg = msg;
    }

    public ResultData(String status, String msg){
        this.status = status;
        if(StringUtils.isEmpty(status)){
            status = "succ";
        }
        this.msg = msg;
    }

    public ResultData(Integer code, String status, String msg){
        this.code = code;
        this.status = status;
        if(StringUtils.isEmpty(status)){
            status = "succ";
        }
        this.msg = msg;
    }
}
