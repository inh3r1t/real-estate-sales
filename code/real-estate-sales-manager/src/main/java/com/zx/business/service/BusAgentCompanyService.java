package com.zx.business.service;

import com.zx.base.model.PagerModel;
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

    private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

}
