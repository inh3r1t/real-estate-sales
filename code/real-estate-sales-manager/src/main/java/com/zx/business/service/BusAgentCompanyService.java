package com.zx.business.service;

import com.zx.base.model.PagerModel;
import com.zx.business.dao.BusAgentCompanyMapper;
import com.zx.business.model.BusAgentCompany;
import com.zx.business.model.BusDeal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author: ytxu3
 * @Description:
 * @Date: 2018/12/30 14:05
 */
@Service
public class BusAgentCompanyService {

    @Resource
    private BusAgentCompanyMapper busAgentCompanyMapper;

    public List<BusAgentCompany> getList(BusAgentCompany busAgentCompany) {
        return busAgentCompanyMapper.selectByPage(null, null, null, null, busAgentCompany);
    }

    public BusAgentCompany addOrUpdate(BusAgentCompany busAgentCompany) {
        if (busAgentCompany.getId() == null) {
            busAgentCompany.setPollCode(generateShortUuid());
            busAgentCompanyMapper.insertSelective(busAgentCompany);
        } else {
            busAgentCompanyMapper.updateByPrimaryKeySelective(busAgentCompany);
        }
        return busAgentCompany;
    }

    public BusAgentCompany getById(Integer id) {
        return busAgentCompanyMapper.selectByPrimaryKey(id);
    }

    public BusAgentCompany delete(Integer id) {
        busAgentCompanyMapper.deleteByPrimaryKey(id);
        return null;
    }

    public PagerModel<BusAgentCompany> getPage(Integer page, Integer pageSize, BusAgentCompany busAgentCompany) {
        Long count = busAgentCompanyMapper.countByModel(busAgentCompany);
        int start = (page - 1) * pageSize;
        List<BusAgentCompany> busAgentCompanies = busAgentCompanyMapper.selectByPage(start, pageSize, null, null, busAgentCompany);
        return new PagerModel<>(pageSize, page, count.intValue(), busAgentCompanies);
    }

    public BusAgentCompany add(BusAgentCompany busRealEstate) {
        busAgentCompanyMapper.insertSelective(busRealEstate);
        return busRealEstate;
    }

    public int update(BusAgentCompany busRealEstate) {
        return busAgentCompanyMapper.updateByPrimaryKeySelective(busRealEstate);
    }

    public String generateShortUuid() {
        List<BusAgentCompany> busAgentCompanies = busAgentCompanyMapper.selectByPage(null, null, null, null, new BusAgentCompany());
        List<String> pollCodes = busAgentCompanies.stream()
                .map(BusAgentCompany::getPollCode)
                .collect(Collectors.toList());
        Random random = new Random();
        String pollCode;
        do {
            pollCode = String.valueOf(random.nextInt(10000));
        } while (pollCodes.contains(pollCode));
        return pollCode;
    }

}
