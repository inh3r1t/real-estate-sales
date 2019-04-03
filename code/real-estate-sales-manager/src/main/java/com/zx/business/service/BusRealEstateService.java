package com.zx.business.service;

import com.zx.base.model.PagerModel;
import com.zx.business.dao.BusRealEstateMapper;
import com.zx.business.model.BusNotifyMsg;
import com.zx.business.model.BusRealEstate;
import com.zx.business.model.BusUser;
import com.zx.business.notify.Notify;
import com.zx.business.notify.model.YunzhixunSmsMessage;
import com.zx.lib.utils.StringUtil;
import com.zx.system.service.SysCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/11/28 18:54
 */
@Service
public class BusRealEstateService {
    private static Logger logger = LoggerFactory.getLogger(BusRealEstateService.class);
    @Resource
    private BusNotifyMsgService busNotifyMsgService;
    @Resource
    private BusUserService busUserService;
    @Resource
    private SysCategoryService sysCategoryService;
    @Resource
    private BusRealEstateMapper busRealEstateMapper;
    @Value("${custom.is_open_notify}")
    private String isOpenNotify;
    @Value("${custom.yunzhixun.sms.building.add.templateid}")
    private String addTemplateId;
    @Value("${custom.yunzhixun.sms.building.change.templateid}")
    private String changeTemplateId;
    @Resource(name = "yunzhixunMessageNotify")
    private Notify notify;

    public PagerModel<BusRealEstate> getPage(Integer page, Integer pageSize, BusRealEstate busRealEstate) {
        Long count = busRealEstateMapper.countByModel(busRealEstate);
        int start = (page - 1) * pageSize;
        List<BusRealEstate> busRealEstates = busRealEstateMapper.selectByPage(start, pageSize, "sort_weight",
                "desc", busRealEstate);
        for (BusRealEstate item : busRealEstates) {
            if (StringUtil.isNotEmpty(item.getExtend2())) {
                item.setCategory(sysCategoryService.selectById(Integer.parseInt(item.getExtend2())).getName());
            }else{
                item.setCategory("");
            }
        }
        PagerModel<BusRealEstate> busRealEstatePage = new PagerModel<>(pageSize, page, count.intValue(), busRealEstates);
        return busRealEstatePage;
    }

    public BusRealEstate getById(Integer id) {
        return busRealEstateMapper.selectByPrimaryKey(id);
    }

    public List<BusRealEstate> getList(BusRealEstate busRealEstate) {
        List<BusRealEstate> busRealEstates = busRealEstateMapper.selectByPage(null, null, null, null, busRealEstate);
        return busRealEstates;
    }


    public BusRealEstate add(BusRealEstate busRealEstate) {
        busRealEstateMapper.insertSelective(busRealEstate);
        return busRealEstate;
    }

    public int update(BusRealEstate busRealEstate) {
        return busRealEstateMapper.updateByPrimaryKeySelective(busRealEstate);
    }

    public int delete(Integer id) {
        return busRealEstateMapper.deleteByPrimaryKey(id);
    }


    public void notice(Integer id, Integer type, List<String> userIds) {
        BusRealEstate busRealEstate = busRealEstateMapper.selectByPrimaryKey(id);
        for (String userId : userIds) {
            try {
                Integer uid = Integer.parseInt(userId);
                if (uid > 0) {
                    BusUser busUser = new BusUser();
                    busUser.setId(uid);
                    busUser = busUserService.getBusUser(busUser);
                    // add notify msg record
                    BusNotifyMsg busNotifyMsg = new BusNotifyMsg();
                    busNotifyMsg.setType(type);
                    busNotifyMsg.setReceiveUserId(uid);
                    busNotifyMsg.setMsgContent(String.format("您好，安策系统新签约 【%s】 项目，详情请到楼盘详情页查看", busRealEstate.getName()));
                    busNotifyMsgService.addOrUpdate(busNotifyMsg);

                    // send sms message
                    if (Boolean.valueOf(isOpenNotify)) {
                        String msg = sendMessage(type, busUser.getPhoneNum(), busRealEstate.getName());
                        logger.info("发送通知至【" + busUser.getPhoneNum() + "】结果：" + msg);
                    }
                }
            } catch (Exception ex) {
                logger.info("发送通知异常：" + ex);
            }
        }
    }

    private String sendMessage(Integer type, String mobile, String busRealEstateName) {
        String templateid = "";
        if (type == 4) {
            templateid = addTemplateId;
        } else if (type == 5) {
            templateid = changeTemplateId;
        }

        YunzhixunSmsMessage message = new YunzhixunSmsMessage();
        message.setTemplateid(templateid);
        message.setMobile(mobile);
        message.setParam(busRealEstateName);
        return notify.notify(message);
    }
}
