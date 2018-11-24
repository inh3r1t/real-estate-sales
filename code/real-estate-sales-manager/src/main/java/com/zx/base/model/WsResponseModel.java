package com.zx.base.model;


import com.zx.lib.utils.DateUtil;
import org.springframework.ui.Model;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Xiafl.
 * @version 2017/12/12
 */
public class WsResponseModel {

    public WsResponseModel() {
        time = DateUtil.getDateNow();
    }

    private String channel;

    private Date time;

    private String extra;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Date getTime() {
        return time;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

}
