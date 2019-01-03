package com.zx.business.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
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
        busDealService.report(busDealVO);
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

    @Test
    public void test10() {
        try {
            //设置超时时间-可自行调整
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient需要的几个参数
            final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
            final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
            //替换成你的AK
//        final String accessKeyId = "yourAccessKeyId";//你的accessKeyId,参考本文档步骤2
//        final String accessKeySecret = "yourAccessKeySecret";//你的accessKeySecret，参考本文档步骤2
            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIJ2EjiIWq73En",
                    "KngDphy5Q2Ia1GdIo4BtnHuVS8lWBw");
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
            request.setPhoneNumbers("153951580022");
            //必填:短信签名-可在短信控制台中找到
            request.setSignName("无");
            //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            request.setTemplateCode("SMS_154585158");
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam("{\"flowType\":\"报备\", \"phoneNum\":\"15395158002\"}"); //"{\"flowType\":\"报备\", \"phoneNum\":\"13855281192\"}"
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("0");
            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
                System.out.println("短信发送成功!");
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}