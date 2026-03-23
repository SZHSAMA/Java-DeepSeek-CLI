package com.szh.deepseek_api;

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public static <T>  Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static <T>  Result<T> error(String message) {
        Result<T> result = new Result<T>();
        result.setCode(500);
        result.setMsg(message);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
