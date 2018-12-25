package com.zx.business.service;

import com.zx.business.dao.BusRoleMapper;
import com.zx.business.model.BusRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BusRoleService {

    @Resource
    private BusRoleMapper busRoleMapper;

    public int add(BusRole busRole) {
        return busRoleMapper.insertSelective(busRole);
    }

    public BusRole getBusRole(BusRole busRole) {
        return busRoleMapper.selectByModel(busRole);
    }
}
