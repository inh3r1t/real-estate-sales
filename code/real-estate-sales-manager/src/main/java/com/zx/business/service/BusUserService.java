package com.zx.business.service;

import com.zx.business.dao.BusUserMapper;
import com.zx.business.model.BusUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class BusUserService {

    @Resource
    private BusUserMapper busUserMapper;

    public int addBusUser(BusUser busUser) {
        busUser.setPasswd(UUID.fromString(busUser.getPasswd()).toString());
        return busUserMapper.insertSelective(busUser);
    }

    public BusUser login(BusUser busUser) throws Exception {
        busUser.setPasswd(UUID.fromString(busUser.getPasswd()).toString());
        List<BusUser> busUsers = busUserMapper.selectByModel(busUser);

        if (busUsers != null && busUsers.size() > 0)
            return busUsers.get(0);
        else
            throw new Exception("用户名或密码错误！");
    }
}
