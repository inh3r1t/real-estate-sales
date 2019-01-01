package com.zx.business.service;

import com.zx.base.common.BaseTest;
import com.zx.base.model.PagerModel;
import com.zx.business.model.BusNotifyMsg;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/31 13:57
 */
public class BusNotifyMsgServiceTest extends BaseTest {

    @Resource
    private BusNotifyMsgService busNotifyMsgService;

    @Test
    public void testAddOrUpdate() {
        BusNotifyMsg condition = new BusNotifyMsg();
        condition.setId(1);
        condition.setDealId(12);
//        condition.setMsgContent("【中介公司名】 【中介公司账号】 报备了 【楼盘名称】 楼盘，请尽快联系处理");
        condition.setMsgContent("【中介公司名】 【中介公司账号】 报备了 【楼盘名称】 楼盘，请尽快联系处理");
        condition.setReceiveUserId(11);
        condition.setSendUserId(10);
        condition.setType(1);
        BusNotifyMsg busNotifyMsg = busNotifyMsgService.addOrUpdate(condition);
        System.out.println(busNotifyMsg);
    }

    @Test
    public void testGetPage() {
        final BusNotifyMsg condition = new BusNotifyMsg();
        final PagerModel<BusNotifyMsg> list = busNotifyMsgService.getPage(1, 4, condition);
        System.out.println(list);
    }

    @Test
    public void testDelete() {
        busNotifyMsgService.deleteById(1);
    }
}