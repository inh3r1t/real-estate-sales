package com.zx.business.vo;

import java.io.Serializable;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/25 22:52
 */
public class BusUserVO implements Serializable {

    private String js_code;
    private String phoneNum;
    private String passwd;

    public String getJs_code() {
        return js_code;
    }

    public void setJs_code(String js_code) {
        this.js_code = js_code;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
