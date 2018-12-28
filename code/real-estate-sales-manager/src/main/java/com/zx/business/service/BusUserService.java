package com.zx.business.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zx.base.exception.BusinessException;
import com.zx.business.common.DataStore;
import com.zx.business.dao.BusUserMapper;
import com.zx.business.model.BusUser;
import com.zx.lib.utils.encrypt.Md5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusUserService {

    @Resource
    private BusUserMapper busUserMapper;

    public int addBusUser(BusUser busUser) {
        busUser.setPasswd(Md5Util.getMd5(busUser.getPasswd()));
        return busUserMapper.insertSelective(busUser);
    }

    public BusUser getBusUser(BusUser busUser) {
        List<BusUser> busUsers = busUserMapper.selectByModel(busUser);
        if (busUsers == null || busUsers.size() == 0)
            return null;
        return busUsers.get(0);
    }

    public Map<String, Object> loginByWechat(String openid) {
        BusUser condition = new BusUser();
        condition.setOpenId(openid);
        BusUser busUser = getBusUser(condition);

        if (busUser != null) {
            String token = getToken(busUser);
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            busUser.setPasswd(null);
            result.put("userInfo", busUser);
            return result;
        } else
            throw new BusinessException("该用户未注册，请先注册再登录！");
    }

    public Map<String, Object> loginByAccount(String phoneNum, String passwd) {
        BusUser condition = new BusUser();
        condition.setPhoneNum(phoneNum);
        condition.setPasswd(Md5Util.getMd5(passwd));
        BusUser busUser = getBusUser(condition);
        if (busUser != null) {String token = getToken(busUser);
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            busUser.setPasswd(null);
            result.put("userInfo", busUser);
            return result;
        } else
            throw new BusinessException("该用户未注册，请先注册再登录！");
    }

    private String getToken(BusUser busUser) {
        // 生成token
        String token = JWT.create().withAudience(String.valueOf(busUser.getId()))
                .sign(Algorithm.HMAC256(busUser.getPasswd()));
        DataStore.getInstance().expireInfo.put(busUser.getId(), System.currentTimeMillis());
        return token;
    }

    public BusUser getUserFromToken(String token) {
        int id = Integer.parseInt(JWT.decode(token).getAudience().get(0));
        BusUser model = new BusUser();
        model.setId(id);
        return getBusUser(model);
    }
}