package com.zx.business.service;

import com.zx.business.dao.BusUserDao;
import com.zx.business.model.BusUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BusUserService {

    @Resource
    private BusUserDao busUserDao;

    public int addBusUser(BusUser busUser) {
        return busUserDao.insertSelective(busUser);
    }
}
