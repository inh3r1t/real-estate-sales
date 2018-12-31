package com.zx.business.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zx.base.common.Const;
import com.zx.base.exception.BusinessException;
import com.zx.business.common.DataStore;
import com.zx.business.dao.BusAgentCompanyMapper;
import com.zx.business.dao.BusRoleMapper;
import com.zx.business.dao.BusUserMapper;
import com.zx.business.model.BusAgentCompany;
import com.zx.business.model.BusRole;
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

    @Resource
    private BusAgentCompanyMapper busAgentCompanyMapper;

    @Resource
    private BusRoleMapper busRoleMapper;

    public int addBusUser(BusUser busUser) {
        busUser.setPasswd(Md5Util.getMd5(busUser.getPasswd()));
        // 根据注册码设置roleId
        BusAgentCompany condition = new BusAgentCompany();
        condition.setPollCode(busUser.getPollCode());
        BusAgentCompany busAgentCompany = busAgentCompanyMapper.selectByModel(condition).get(0);
        if (busAgentCompany == null) {
            throw new BusinessException("注册码不存在，请检查后在输入！");
        } else {
            BusRole busRole = new BusRole();
            busRole.setType(String.valueOf(busAgentCompany.getState()));
            Integer roleId = busRoleMapper.selectByModel(busRole).get(0).getId();
            busUser.setRoleId(roleId);
        }
        return busUserMapper.insertSelective(busUser);
    }

    public int updateByPrimaryKeySelective(BusUser busUser){
        return busUserMapper.updateByPrimaryKeySelective(busUser);
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
            throw new BusinessException(Const.NO_EXIST_USER);
    }

    public Map<String, Object> loginByAccount(String phoneNum, String passwd) {
        BusUser condition = new BusUser();
        condition.setPhoneNum(phoneNum);
        BusUser busUser = getBusUser(condition);
        if (busUser == null)
            throw new BusinessException(Const.NO_EXIST_USER);

        if (!busUser.getPasswd().equals(Md5Util.getMd5(passwd)))
            throw new BusinessException(Const.PASSWORD_ERROR);

        String token = getToken(busUser);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        busUser.setPasswd(null);
        result.put("userInfo", busUser);
        return result;
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

    public List<BusUser> getListByRoleType(Integer roleType) {
        List<BusUser> busUserList = busUserMapper.getListByRoleType(roleType);
        return busUserList;
    }
}