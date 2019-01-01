package com.zx.business.service;

import com.zx.base.common.BaseTest;
import com.zx.business.dao.BusDealMapper;
import com.zx.business.model.BusDeal;
import com.zx.business.vo.BusDealVO;
import com.zx.lib.http.kit.HttpKit;
import org.junit.Test;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/25 20:56
 */
public class BusDealServiceTest extends BaseTest {

    @Resource
    private BusDealService busDealService;

    @Resource
    private BusDealMapper busDealMapper;

    @Test
    public void test1() {
        BusDeal busDeal = new BusDeal();
        busDeal.setRealEstateId(2);
        busDeal.setSubscribeMoney("100");
        busDeal.setSubscribeTime(new Date());
        busDeal.setSubscribeOperateTime(new Date());
        busDeal.setArriveOperateTime(new Date());
        busDeal.setAppointmentOperateTime(new Date());
        busDeal.setCustomerId(3);
        busDeal.setCustomerPhone("132423421223");
        busDeal.setCustomerName("Tony");
        busDeal.setRealEstateName("碧春园");
        busDeal.setReportCompany("testCompany");
        final int id = busDealMapper.insertSelective(busDeal);
    }

    @Test
    public void test2() {
        BusDeal busDeal = new BusDeal();
        busDeal.setRealEstateId(2);
        busDeal.setSubscribeMoney("100");
        busDeal.setCustomerId(3);
        busDeal.setCustomerPhone("132423421223");
        busDeal.setCustomerName("Tony");
        busDeal.setRealEstateName("碧春园");
        busDeal.setReportCompany("testCompany");

        final List<BusDeal> busDeals = busDealMapper.selectByPage(null, null, null, null, busDeal);
        System.out.println(busDeals);
    }

    @Test
    public void test3() {
        BusDeal busDeal = new BusDeal();
        busDeal.setRealEstateName("碧春园");

//        final Long aLong = busDealMapper.countByModel(busDeal);
//        System.out.println(aLong);

        final List<BusDeal> busDeals = busDealMapper.selectByPage(1, 4,null,null, busDeal);
        System.out.println(busDeals);

    }

    @Test
    public void test4() {
        BusDealVO busDealVO = new BusDealVO();
        busDealVO.setCustomerName("tony");
        busDealVO.setCustomerPhone("123421231234");
        busDealVO.setCustomerSex(1);
        busDealVO.setRealEstateIds("1,2");
        busDealVO.setReportUserId(4);
        busDealVO.setReportTime(new Date());
        busDealService.report(busDealVO, null);
    }

    @Test
    public void test5() {

        BusDeal busDeal = new BusDeal();
        busDeal.setId(19);
        busDeal.setAppointmentTime(new Date());
        busDealService.appointment(busDeal);
    }

    @Test
    public void test6() {
        BusDeal busDeal = new BusDeal();
        busDeal.setId(19);
        busDeal.setArriveTime(new Date());
        busDealService.arrive(busDeal);
    }

    @Test
    public void test7() {
        BusDeal busDeal = new BusDeal();
        busDeal.setId(19);
        busDeal.setSubscribeTime(new Date());
        busDealService.subscribe(busDeal);
    }

    @Test
    public void test8() {
        final BusDeal byId = busDealService.getById(11);
        System.out.println(byId);
    }

    @Test
    public void test9() {
        String access_token = HttpKit.get(String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=%s&appid=%s&secret=%s",
                "client_credential", "wx4476c55348a31df8", "26754ef3189b3ef8a18d9b5e05d3bb23")).getHtml();
        System.out.println(access_token);
        String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + access_token;
        Map<String, Object> params = new HashMap<>();
        params.put("touser", "oFnXq0MuKtmlWuj3tTCh6HagZb24");
        params.put("template_id", "h6AadLhcNf-UdHfZlw2epbxZRlpuZ7LULyDigLl65ig");
        Map<String, String> data = new HashMap<>();
        data.put("keyword1", "蓝光香江国际");
        data.put("keyword2", "张三丰");
        data.put("keyword3", "138****6789");
        data.put("keyword4", "李四水");
        data.put("keyword5", "链家");
        params.put("data", data);
    }
}