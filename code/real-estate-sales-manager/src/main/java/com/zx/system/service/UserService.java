package com.zx.system.service;

import com.zx.system.model.SysUser;
import com.zx.system.model.SysUserlogin;
import com.zx.system.model.SysUserrole;
import org.springframework.http.HttpRequest;

import java.util.List;
import java.util.Map;

/**
 * 用户管理业务逻辑类
 *
 * @author wangx
 * @version 2017-12-04 1.0.0
 */
public interface UserService {

    /**
     * 查询数量
     *
     * @param paramMap
     * @return
     */
    int selectCount(Map<String, Object> paramMap);

    /**
     * 根据主键Id获取用户
     *
     * @param id
     * @return
     */
    SysUser getByID(Integer id);

    /**
     * 根据用户名获取用户
     *
     * @param userName 用户名/登录Id
     * @return
     */
    SysUser getByUserName(String userName);

    /**
     * 获取用户集合
     *
     * @param paramMap
     * @return
     */
    List<SysUser> getList(Map paramMap);

    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    SysUser save(SysUser user);

    /**
     * 删除用户登录Token
     *
     * @param token
     * @return
     */
    void deleteUserLoginByToken(String token);

    /**
     * 用户登录
     *
     * @param loginid
     * @param psd
     * @return
     */
    SysUserlogin login(String loginid, String psd);

    /**
     * 获取用户
     *
     * @param logintoken
     * @return
     */
    SysUserlogin selectByToken(String logintoken);

    /**
     * 获取登录用户
     *
     * @param loginTokens
     * @return
     */
    List<SysUserlogin> selectByToken(List<String> loginTokens);

    /**
     * 添加登录用户
     *
     * @param userlogin
     * @return
     */
    int addUserLogin(SysUserlogin userlogin);


}
