package com.tionkior.pms.entity.vo;

/**
 * 返回结果枚举类
 * @author TionKior
 */
public enum ResultCodeEnum {
    SUCCESS("0000", "操作成功"),
    FAIL("0001", "操作失败"),
    NOREPEAT("0004","参数不可重复");


    String code;
    String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }



}