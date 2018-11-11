package com.ck.lmmanagement.constant;

/**
 * @author 01378803
 * @date 2018/11/5 16:12
 * Description  : 枚举类
 */
public enum LmEnum {
    /**
     * 全局异常的提示
     */
    SYSTEM_EXCEPTION("系统繁忙，请稍后再试"),
    /**
     * 性别男
     */
    SEX_MALE("男", "1"),
    /**
     * 性别女
     */
    SEX_FEMALE("女", "0");
    /**
     * name
     */
    private String name;
    /**
     * code
     */
    private String code;

    private LmEnum(String name){
        this.name = name;
    }
    private LmEnum(String name, String code){
        this.name = name;
        this.code = code;
    }

    public static String getCode(String name){
        for(LmEnum lmEnum : LmEnum.values()){
            if(lmEnum.getName().equals(name)){
                return lmEnum.getCode();
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
