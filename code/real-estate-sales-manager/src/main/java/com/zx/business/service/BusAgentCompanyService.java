package com.zx.business.service;

import com.zx.business.dao.BusAgentCompanyMapper;
import com.zx.business.model.BusAgentCompany;
import com.zx.business.model.BusDeal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/30 14:05
 */
@Service
public class BusAgentCompanyService {

    @Resource
    private BusAgentCompanyMapper busAgentCompanyMapper;

    public List<BusDeal> getList(BusAgentCompany busAgentCompany) {
        return busAgentCompanyMapper.selectByPage(null, null, null, null, busAgentCompany);
    }

    public BusAgentCompany addOrUpdate(BusAgentCompany busAgentCompany) {
        if (busAgentCompany.getId() == null) {
            busAgentCompany.setPollCode(UUID.randomUUID().toString());
            busAgentCompanyMapper.insertSelective(busAgentCompany);
        } else {
            busAgentCompanyMapper.updateByPrimaryKeySelective(busAgentCompany);
        }
        return busAgentCompany;
    }

    public BusAgentCompany delete(Integer id) {
        busAgentCompanyMapper.deleteByPrimaryKey(id);
        return null;
    }
}
