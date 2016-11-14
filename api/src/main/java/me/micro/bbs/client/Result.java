package me.micro.bbs.client;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 通用返回结果
 *
 * Created by microacup on 2016/11/14.
 */
public class Result<T> {
    private int code; // 详细代码
    private String msg; // 提示信息

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private T data; // 业务数据

    public Result() {
        this.code = 200;
        this.msg = "成功";
    }

    public Result(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> Result<T> ok(T data) {
        return new Result<T>().setData(data);
    }
}
