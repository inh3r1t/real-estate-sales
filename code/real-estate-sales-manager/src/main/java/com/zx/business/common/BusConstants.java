package com.zx.business.common;

import java.util.HashMap;
import java.util.Map;

public class BusConstants {

    public static final Integer DEAL_STATE_REPORT = 0;
    public static final Integer DEAL_STATE_APPOINTMENT = 1;
    public static final Integer DEAL_STATE_ARRIVE = 2;
    public static final Integer DEAL_STATE_SUBSCRIBE = 3;

    public static final Map<Integer, String> DEAL_STATE_INFO = new HashMap<>();

    static {
        DEAL_STATE_INFO.put(0, "report_count");
        DEAL_STATE_INFO.put(1, "appointment_count");
        DEAL_STATE_INFO.put(2, "arrive_count");
        DEAL_STATE_INFO.put(3, "subscribe_count");
        DEAL_STATE_INFO.put(4, "sign_count");
    }
}
