package com.zx.business.notify.model;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2019/1/2 18:34
 */
public class WechatMessage extends Message {

    private String touser; // openid
    private String form_id;
    private String template_id;
    private Object data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
