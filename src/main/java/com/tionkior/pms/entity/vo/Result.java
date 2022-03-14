package com.tionkior.pms.entity.vo;
import java.io.Serializable;
import java.text.MessageFormat;

/**
 * @param <T>
 * @author
 * @Description 返回信息包装类
 */
public class Result<T> implements Serializable {

    /**
     * 操作编码
     */
    public String code;

    /**
     * 提示信息
     */
    public String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 无参构造
     */
    public Result() {
    }

    /**
     * 根据code，msg创建一个Result
     * @param code
     * @param msg
     */
    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据code，msg，data创建一个Result
     * @param code
     * @param msg
     * @param data
     */
    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 根据枚举创建一个Result
     * @param resultCodeEnum
     */
    public Result(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
    }

    public static final Result success() {
        Result result = new Result(ResultCodeEnum.SUCCESS);
        return result;
    }

    public static final <T> Result<T> success(T data) {
        Result result = new Result(ResultCodeEnum.SUCCESS);
        result.setData(data);
        return result;
    }

    public static final Result failure() {
        Result result = new Result(ResultCodeEnum.FAIL);
        return result;
    }

    public static final <T> Result<T> failure(ResultCodeEnum resultCodeEnum) {
        return new Result(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }

    public static final Result failure(String code, String msg) {
        Result result = new Result(code, msg);
        return result;
    }

    public static final Result failure(ResultCodeEnum codeEnum, String message) {
        Result result = new Result(codeEnum.getCode(), MessageFormat.format("{0}: {1}", codeEnum.getMsg(), message));
        return result;
    }

    /**
     * 根据枚举创建一个Result
     * @param resultCodeEnum
     * @return
     */
    public static final Result result(ResultCodeEnum resultCodeEnum) {
        Result result = new Result(resultCodeEnum);
        return result;
    }

    /**
     * 根据枚举创建一个Result
     * @param resultCodeEnum
     * @return
     */
    public static final <T> Result<T> result(ResultCodeEnum resultCodeEnum, T data) {
        Result result = new Result(resultCodeEnum);
        return result;
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}