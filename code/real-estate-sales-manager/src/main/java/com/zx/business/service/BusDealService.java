package com.zx.business.service;

import com.zx.base.model.PagerModel;
import com.zx.business.common.BusConstants;
import com.zx.business.dao.*;
import com.zx.business.model.*;
import com.zx.business.vo.BusDealVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BusDealService {

    @Resource
    private BusDealMapper busDealMapper;

    @Resource
    private BusCustomerMapper busCustomerMapper;

    @Resource
    private BusRealEstateMapper busRealEstateMapper;

    @Resource
    private BusUserMapper busUserMapper;

    @Resource
    private BusAgentCompanyMapper busAgentCompanyMapper;

    public BusDeal getById(Integer id) {
        return busDealMapper.selectByPrimaryKey(id);
    }

    public PagerModel<BusDeal> getPage(Integer page, Integer pageSize, BusDeal busDeal) {
        Long count = busDealMapper.countByModel(busDeal);
        int start = (page - 1) * pageSize;
        List<BusDeal> busDeals = busDealMapper.selectByPage(start, pageSize, null, null, busDeal);
        return new PagerModel<>(page, pageSize, count.intValue(), busDeals);
    }

    public Map<String, Long> countByState(int busUserId) {
        BusDeal busDeal = new BusDeal();
        busDeal.setManagerId(busUserId);
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
        List<BusDeal> busDealList = new ArrayList<>();

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
            busDeal.setReportTime(busDealVO.getReportTime());
            busDeal.setReportOperateTime(new Date());
            busDeal.setCreateTime(new Date());

            busDealList.add(busDeal);
        }

        busDealMapper.batchInsert(busDealList);
    }

    public BusDeal appointment(BusDeal busDeal) {
        BusDeal execBusDeal = busDealMapper.selectByPrimaryKey(busDeal.getId());
        execBusDeal.setState(1);
        execBusDeal.setAppointmentTime(busDeal.getAppointmentTime());
        execBusDeal.setAppointmentOperateTime(new Date());
        execBusDeal.setUpdateTime(new Date());
        busDealMapper.updateByPrimaryKeySelective(execBusDeal);
        return execBusDeal;
    }

    public BusDeal arrive(BusDeal busDeal) { // TODO 图片上传
        BusDeal execBusDeal = busDealMapper.selectByPrimaryKey(busDeal.getId());
        execBusDeal.setState(2);
        execBusDeal.setArriveTime(busDeal.getArriveTime());
        execBusDeal.setArriveOperateTime(new Date());
        execBusDeal.setUpdateTime(new Date());
        busDealMapper.updateByPrimaryKeySelective(execBusDeal);
        return execBusDeal;
    }

    public BusDeal subscribe(BusDeal busDeal) { // TODO 图片上传
        BusDeal execBusDeal = busDealMapper.selectByPrimaryKey(busDeal.getId());
        execBusDeal.setState(3);
        execBusDeal.setSubscribeTime(busDeal.getSubscribeTime());
        execBusDeal.setSubscribeMoney(busDeal.getSubscribeMoney());
        execBusDeal.setSubscribeOperateTime(new Date());
        execBusDeal.setUpdateTime(new Date());
        busDealMapper.updateByPrimaryKeySelective(execBusDeal);
        return execBusDeal;
    }
}
