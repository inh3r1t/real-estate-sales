package com.zx.business.service;

import com.zx.base.model.PagerModel;
import com.zx.business.common.BusConstants;
import com.zx.business.dao.*;
import com.zx.business.model.*;
import com.zx.business.notify.Notify;
import com.zx.business.notify.model.YunzhixunSmsMessage;
import com.zx.business.vo.BusDealVO;
import com.zx.lib.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BusDealService {

    private static final Logger logger = LoggerFactory.getLogger(BusDealService.class);

    @Resource
    private BusDealMapper busDealMapper;

    @Resource
    private BusCustomerMapper busCustomerMapper;

    @Resource
    private BusRealEstateMapper busRealEstateMapper;

    @Resource
    private BusNotifyMsgMapper busNotifyMsgMapper;

    @Resource
    private BusUserMapper busUserMapper;

    @Resource(name = "yunzhixunMessageNotify")
    private Notify notify;

    @Value("${custom.is_open_notify}")
    private String isOpenNotify;

    public BusDeal getById(Integer id) {
        return busDealMapper.selectByPrimaryKey(id);
    }

    public PagerModel<BusDeal> getPage(Integer page, Integer pageSize, BusDeal busDeal) {
        Long count = busDealMapper.countByModel(busDeal);
        int start = (page - 1) * pageSize;
        List<BusDeal> busDeals = busDealMapper.selectByPage(start, pageSize, "create_time", "desc", busDeal);
        return new PagerModel<>(pageSize, page, count.intValue(), busDeals);
    }

    public Map<String, Long> countByState(BusDeal busDeal) {
        List<BusDeal> busDeals = busDealMapper.selectByPage(null, null, null, null, busDeal);
        Map<String, Long> result = busDeals.stream().collect(Collectors.groupingBy(deal -> BusConstants.DEAL_STATE_INFO.get(deal.getState()),
                Collectors.counting()));
        result.put("total_count", Long.valueOf(busDeals.size()));
        return result;
    }

    public List<BusDeal> getList(BusDeal busDeal) {
        return busDealMapper.selectByPage(null, null, null, null, busDeal);
    }

    @Transactional
    public void report(BusDealVO busDealVO) {
        Integer agentId = busDealVO.getReportUserId();
        BusUser agent = busUserMapper.selectByPrimaryKey(agentId);

        // add customer record
        String customerName = busDealVO.getCustomerName();
        String customerPhone = busDealVO.getCustomerPhone();
        BusCustomer busCustomer = new BusCustomer(agentId, customerName, customerPhone, busDealVO.getCustomerSex());
        busCustomerMapper.insert(busCustomer);

        String[] realEstateIds = busDealVO.getRealEstateIds().split(",");

        // add deal records
        for (String id : realEstateIds) {
            Integer realEstateId = Integer.valueOf(id);
            BusRealEstate busRealEstate = busRealEstateMapper.selectByPrimaryKey(realEstateId);

            BusDeal busDeal = new BusDeal();
            busDeal.setRealEstateId(realEstateId);
            busDeal.setRealEstateName(busRealEstate.getName());
            busDeal.setState(BusConstants.DEAL_STATE_REPORT);
            busDeal.setCustomerId(busCustomer.getId());
            busDeal.setCustomerName(customerName);
            busDeal.setCustomerPhone(customerPhone);
            busDeal.setReportUserId(agentId);
            busDeal.setReportUserPhone(agent.getPhoneNum());
            busDeal.setReportCompany(agent.getCompanyName());
            busDeal.setReportTime(new Date());
            busDeal.setReportOperateTime(new Date());
            busDeal.setManagerId(busRealEstate.getManagerId());
            busDeal.setCreateTime(new Date());
            busDealMapper.insertSelective(busDeal);

            // add notify msg record
            BusNotifyMsg busNotifyMsg = new BusNotifyMsg();
            busNotifyMsg.setType(0);
            busNotifyMsg.setReceiveUserId(busRealEstate.getManagerId());
            busNotifyMsg.setDealId(busDeal.getId());
            busNotifyMsg.setMsgContent(reportMsg(agent.getCompanyName(), agent.getUserName(), busRealEstate.getName()));
            busNotifyMsgMapper.insertSelective(busNotifyMsg);
        }

    }

    public BusDeal appointment(BusDeal busDeal) {
        BusDeal execBusDeal = busDealMapper.selectByPrimaryKey(busDeal.getId());
        execBusDeal.setState(1);
        execBusDeal.setAppointmentTime(busDeal.getAppointmentTime());
        execBusDeal.setAppointmentOperateTime(new Date());
        execBusDeal.setUpdateTime(new Date());
        busDealMapper.updateByPrimaryKeySelective(execBusDeal);

        // add notify msg record
        BusNotifyMsg busNotifyMsg = new BusNotifyMsg();
        busNotifyMsg.setType(1);
        busNotifyMsg.setReceiveUserId(execBusDeal.getBusRealEstate().getManagerId());
        busNotifyMsg.setDealId(busDeal.getId());
        busNotifyMsg.setMsgContent(appointmentMsg(execBusDeal.getBusCustomer().getName(), execBusDeal.getBusCustomer().getSex(), execBusDeal.getRealEstateName(),
                DateUtil.toDateString(busDeal.getAppointmentTime(), "yyyy-MM-dd HH:mm")));
        busNotifyMsgMapper.insertSelective(busNotifyMsg);

        return execBusDeal;
    }

    public BusDeal arrive(BusDeal busDeal) {
        BusDeal execBusDeal = busDealMapper.selectByPrimaryKey(busDeal.getId());
        execBusDeal.setState(2);
        execBusDeal.setArriveTime(busDeal.getArriveTime());
        execBusDeal.setArriveCertifyPhotoPath(busDeal.getArriveCertifyPhotoPath());
        execBusDeal.setArriveOperateTime(new Date());
        execBusDeal.setUpdateTime(new Date());
        busDealMapper.updateByPrimaryKeySelective(execBusDeal);

        return execBusDeal;
    }

    public BusDeal subscribe(BusDeal busDeal) {
        BusDeal execBusDeal = busDealMapper.selectByPrimaryKey(busDeal.getId());
        execBusDeal.setState(3);
        execBusDeal.setSubscribeTime(busDeal.getSubscribeTime());
        execBusDeal.setSubscribeMoney(busDeal.getSubscribeMoney());
        execBusDeal.setSubscribePhotoPahts(busDeal.getSubscribePhotoPahts());
        execBusDeal.setSubscribeOperateTime(new Date());
        execBusDeal.setUpdateTime(new Date());
        busDealMapper.updateByPrimaryKeySelective(execBusDeal);

        // add notify msg record
        BusNotifyMsg busNotifyMsg = new BusNotifyMsg();
        busNotifyMsg.setType(2);
        busNotifyMsg.setReceiveUserId(execBusDeal.getReportUserId());
        busNotifyMsg.setDealId(busDeal.getId());
        busNotifyMsg.setMsgContent(subscribeMsg(execBusDeal.getReportUser().getCompanyName(), execBusDeal.getCustomerName(), execBusDeal.getBusCustomer().getSex(),
                execBusDeal.getRealEstateName(), DateUtil.toDateString(execBusDeal.getSubscribeTime(), "yyyy-MM-dd HH:mm")));
        busNotifyMsgMapper.insertSelective(busNotifyMsg);

        // send sms message
        if (Boolean.valueOf(isOpenNotify)) {
            sendMessage(execBusDeal.getReportUser().getPhoneNum(), "认购通知", String.valueOf(execBusDeal.getId()),
                    execBusDeal.getManager().getUserName());
        }
        return execBusDeal;
    }

    public BusDeal sign(BusDeal busDeal) {
        BusDeal execBusDeal = busDealMapper.selectByPrimaryKey(busDeal.getId());
        execBusDeal.setState(4);
        execBusDeal.setSignTime(new Date());
        execBusDeal.setSignMoney(busDeal.getSubscribeMoney());
        execBusDeal.setSignPhotoPaths(busDeal.getSignPhotoPaths());
        execBusDeal.setSignOperateTime(new Date());
        execBusDeal.setUpdateTime(new Date());
        busDealMapper.updateByPrimaryKeySelective(execBusDeal);

        // add notify msg record
        BusNotifyMsg busNotifyMsg = new BusNotifyMsg();
        busNotifyMsg.setType(3);
        busNotifyMsg.setReceiveUserId(execBusDeal.getReportUserId());
        busNotifyMsg.setDealId(busDeal.getId());
        busNotifyMsg.setMsgContent(signMsg(execBusDeal.getReportUser().getCompanyName(), execBusDeal.getCustomerName(), execBusDeal.getBusCustomer().getSex(),
                execBusDeal.getRealEstateName(), DateUtil.toDateString(execBusDeal.getSubscribeTime(), "yyyy-MM-dd HH:mm")));
        busNotifyMsgMapper.insertSelective(busNotifyMsg);

        // send sms message
        if (Boolean.valueOf(isOpenNotify)) {
            sendMessage(execBusDeal.getReportUser().getPhoneNum(), "签约通知", String.valueOf(execBusDeal.getId()),
                    execBusDeal.getManager().getUserName());
        }
        return execBusDeal;
    }

    // 报备消息通知
    private String reportMsg(String agentCompanyName, String agentName, String realEstateName) {
        String model = "【%s】 【%s】 报备了 【%s】 楼盘，请尽快联系处理";
        return String.format(model, agentCompanyName, agentName, realEstateName);
    }

    // 预约消息通知
    private String appointmentMsg(String customerName, Integer customerSex, String realEstateName, String appointmentTime) {
        String sex = customerSex == 0 ? "先生" : "女士";
        String model = "您报备的客户【%s】已预约 【%s】 楼盘，预约时间：【%s】";
        return String.format(model, customerName + sex, realEstateName, appointmentTime);
    }

    // 认购消息通知
    private String subscribeMsg(String agentCompanyName, String customerName, Integer customerSex, String realEstateName, String subscribeTime) {
        String sex = customerSex == 0 ? "先生" : "女士";
        String model = "【%s】 客户 【%s】已成功认购 【%s】 楼盘，认购时间：【%s】";
        return String.format(model, agentCompanyName, customerName + sex, realEstateName, subscribeTime);
    }

    // 签约消息通知
    private String signMsg(String agentCompanyName, String customerName, Integer customerSex, String realEstateName, String subscribeTime) {
        String sex = customerSex == 0 ? "先生" : "女士";
        String model = "【%s】 客户 【%s】已成功签约 【%s】 楼盘，签约时间：【%s】";
        return String.format(model, agentCompanyName, customerName + sex, realEstateName, subscribeTime);
    }

    private void sendMessage(String mobile, String notifyType, String busDealId, String contactName) {
        YunzhixunSmsMessage message = new YunzhixunSmsMessage();
        message.setMobile(mobile);
        message.setParam(notifyType + "," + contactName + "," + busDealId);
        notify.notify(message);
    }
}
