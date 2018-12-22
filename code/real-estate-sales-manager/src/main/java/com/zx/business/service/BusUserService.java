package com.zx.business.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zx.base.exception.BusinessException;
import com.zx.business.common.DataStore;
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

    public BusUser getBusUser(BusUser busUser) {
        return busUserMapper.selectByModel(busUser).get(0);
    }

    public String login(String openid) {
        BusUser busUser = new BusUser();
        busUser.setOpenId(openid);
        List<BusUser> busUsers = busUserMapper.selectByModel(busUser);

        if (busUsers != null && busUsers.size() > 0) {
            // 生成token
            String token = JWT.create().withAudience(String.valueOf(busUser.getId()))
                    .sign(Algorithm.HMAC256(busUser.getPasswd()));
            DataStore.getInstance().expireInfo.put(busUser.getId(), System.currentTimeMillis());
            return token;
        } else
            throw new BusinessException("该用户未注册，请先注册再登录！");
    }
}
