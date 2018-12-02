package com.zx.business.service;

import com.zx.business.common.BusinessCons;
import com.zx.business.dao.*;
import com.zx.business.model.*;
import com.zx.business.vo.BusDealVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Transactional
    public void report(BusDealVO busDealVO) {
        Integer agentId = busDealVO.getReportUserId();
        BusUser agent = busUserMapper.selectByPrimaryKey(agentId);

        String customerName = busDealVO.getCustomerName();
        String customerPhone = busDealVO.getCustomerPhone();
        BusCustomer busCustomer = new BusCustomer(agentId, customerName, customerPhone, busDealVO.getCustomerSex());
        int customerId = busCustomerMapper.insert(busCustomer);

        String[] realEstateIds = busDealVO.getRealEstateIds().split(",");
        List<BusDeal> busDealList = new ArrayList<>();
        for (String id : realEstateIds) {
            Integer realEstateId = Integer.valueOf(id);
            BusRealEstate busRealEstate = busRealEstateMapper.selectByPrimaryKey(realEstateId);

            BusDeal busDeal = new BusDeal();
            busDeal.setRealEstateId(realEstateId);
            busDeal.setRealEstateName(busRealEstate.getName());
            busDeal.setState(BusinessCons.DEAL_STATE_REPORT);
            busDeal.setCustomerId(customerId);
            busDeal.setCustomerName(customerName);
            busDeal.setCustomerPhone(customerPhone);
            busDeal.setReportUserId(agentId);
            busDeal.setReportUserPhone(agent.getPhoneNum());
            busDeal.setReportCompany(agent.getCompanyName());
            busDeal.setReportOperateTime(new Date());

            busDealList.add(busDeal);
        }

        busDealMapper.batchInsertSelective(busDealList); // TODO
    }
}
