package com.zx.base.model;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/11/28 10:25
 */
public class ResultData {

    private String resultCode;
    private String data;
    private String msg;

    public ResultData() {
    }

    public ResultData(String resultCode, String msg) {
        this.resultCode = resultCode;
        this.msg = msg;
    }

    public ResultData(String resultCode, String data, String msg) {
        this.resultCode = resultCode;
        this.data = data;
        this.msg = msg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
