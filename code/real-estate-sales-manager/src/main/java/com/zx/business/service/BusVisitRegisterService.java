package com.zx.business.service;

import com.zx.base.model.PagerModel;
import com.zx.business.dao.BusVisitRegisterMapper;
import com.zx.business.model.BusVisitRegister;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class BusVisitRegisterService {

    @Resource
    private BusVisitRegisterMapper busVisitRegisterMapper;

    public BusVisitRegister insert(BusVisitRegister model) {
        busVisitRegisterMapper.insert(model);
        return model;
    }
    public BusVisitRegister updateByPrimaryKey(BusVisitRegister model) {
        busVisitRegisterMapper.updateByPrimaryKey(model);
        return model;
    }

    public PagerModel<BusVisitRegister> getPage(Integer page, Integer pageSize, String startDateTime, String endDateTime) {
        Integer count = busVisitRegisterMapper.countByModel(startDateTime, endDateTime);
        int start = (page - 1) * pageSize;
        List<BusVisitRegister> busVisitRegisters = busVisitRegisterMapper.selectByPage(start, pageSize, "createtime", "desc", startDateTime, endDateTime);
        return new PagerModel<>(pageSize, page, count.intValue(), busVisitRegisters);
    }

    public List<BusVisitRegister> getList(String startDateTime, String endDateTime) {
        return busVisitRegisterMapper.selectByPage(null, null, "createtime", "desc", startDateTime, endDateTime);
    }

    public BusVisitRegister getById(Integer id) {
        return busVisitRegisterMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        busVisitRegisterMapper.deleteByPrimaryKey(id);
    }
}