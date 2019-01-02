package com.zx.business.notify.model;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2019/1/2 16:58
 */
public class Message implements Serializable {

    private String target;
    private Map<String, Object> params;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
