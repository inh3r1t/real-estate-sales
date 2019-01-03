package com.zx.business.notify.model;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2019/1/3 19:06
 */
public class AliyunSmsMessage extends Message {

    private String phoneNumbers;
    private String templateParam;
    private String outId;

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }
}
