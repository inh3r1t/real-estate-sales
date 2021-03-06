package com.zx.business.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zx.base.common.Const;
import com.zx.base.exception.BusinessException;
import com.zx.base.exception.WechatAuthException;
import com.zx.business.common.DataStore;
import com.zx.business.dao.BusAccountManageMapper;
import com.zx.business.dao.BusAgentCompanyMapper;
import com.zx.business.dao.BusRoleMapper;
import com.zx.business.dao.BusUserMapper;
import com.zx.business.model.*;
import com.zx.business.notify.Notify;
import com.zx.business.notify.model.YunzhixunSmsMessage;
import com.zx.lib.utils.encrypt.Md5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BusUserService {

    @Resource
    private BusUserMapper busUserMapper;

    @Resource
    private BusAgentCompanyMapper busAgentCompanyMapper;

    @Resource
    private BusRoleMapper busRoleMapper;

    @Resource
    private BusAccountManageMapper busAccountManageMapper;

    @Resource(name = "yunzhixunMessageNotify")
    private Notify notify;

    @Value("${custom.yunzhixun.sms.verify.templateid}")
    private String templateid;


    public List<BusUser> getList(BusUser busUser) {
        return busUserMapper.selectByPage(null, null, null, null, busUser);
    }

    public int addBusUser(BusUser busUser) {
        busUser.setPasswd(Md5Util.getMd5(busUser.getPasswd()));
        // 根据注册码设置roleId
        BusAgentCompany condition = new BusAgentCompany();
        condition.setPollCode(busUser.getPollCode());
        List<BusAgentCompany> busAgentCompanies = busAgentCompanyMapper.selectByModel(condition);
        if (busAgentCompanies == null || busAgentCompanies.size() == 0) {
            throw new WechatAuthException(Const.POLL_CODE_ERROR);
        } else {
            BusRole busRole = new BusRole();
            BusAgentCompany busAgentCompany = busAgentCompanies.get(0);
            busRole.setType(String.valueOf(busAgentCompany.getState()));
            Integer roleId = busRoleMapper.selectByModel(busRole).get(0).getId();
            busUser.setRoleId(roleId);
            busUser.setCompanyId(busAgentCompany.getId());
            busUser.setCompanyName(busAgentCompany.getName());
        }
        return busUserMapper.insertSelective(busUser);
    }

    public int updateByPrimaryKeySelective(BusUser busUser) {
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

    public String getVerifyCode(String phoneNum) {
        BusAccountManage busAccountManage = busAccountManageMapper.selectVerifyCode(phoneNum);
        return busAccountManage.getVerifyCode();
    }

    public String sendMessage(String phoneNum) {
        BusAccountManage result = busAccountManageMapper.selectVerifyCode(phoneNum);
        if (result != null && result.getLastTime().getTime() + 10 * 60 * 60 * 1000 > System.currentTimeMillis()) // 验证码过期时间为10分钟
            return Const.VERIFY_NOT_EXPIRE;

        String verifyCode = String.valueOf(new Random().nextInt(9000) + 1000);
        BusAccountManage busAccountManage = new BusAccountManage();
        busAccountManage.setVerifyCode(verifyCode);
        busAccountManage.setPhoneNum(phoneNum);
        busAccountManage.setLastTime(new Date());
        busAccountManage.setCreateTime(new Date());
        busAccountManageMapper.insertSelective(busAccountManage);

        YunzhixunSmsMessage message = new YunzhixunSmsMessage();
        message.setTemplateid(templateid);
        message.setMobile(phoneNum);
        message.setParam(verifyCode);
        notify.notify(message);
        return null;
    }

    public void reset(String phoneNum, String password) {
        BusUser condition = new BusUser();
        condition.setPhoneNum(phoneNum);
        List<BusUser> busUsers = busUserMapper.selectByModel(condition);
        if (busUsers.size() == 0)
            throw new BusinessException(Const.NO_EXIST_USER);
        BusUser busUser = busUsers.get(0);
        busUser.setPasswd(Md5Util.getMd5(password));
        busUserMapper.updateByPrimaryKeySelective(busUser);
    }

    public  int selectCount(Map map){
        return busUserMapper.count(map);
    }
    public List<BusUser> getList(Map map) {
        return busUserMapper.selectList(map);
        // return new PagerModel<>(pageSize, page, count.intValue(), busVisitRegisters);
    }

    public int delete(Integer id) {
        return busUserMapper.deleteByPrimaryKey(id);
    }

}