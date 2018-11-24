package com.zx.system.dao;

import com.zx.system.model.SysUserlogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户登录信息表 数据库操作接口
 *
 * @author wangx
 */
@Repository("sysUserloginDao")
@Mapper
public interface SysUserloginDao {
    /**
     * 查询
     *
     * @param selectMap
     * @return
     */
    List<SysUserlogin> selectList(Map selectMap);

    /**
     * 查询
     *
     * @param objectMap
     * @return
     */
    int selectCount(Map<String, Object> objectMap);

    /**
     * 添加
     *
     * @param sysUserlogin
     * @return
     */
    int insert(SysUserlogin sysUserlogin);

    /**
     * 根据Logintoken更新
     *
     * @param sysUserlogin
     * @return
     */
    int updateByLogintoken(SysUserlogin sysUserlogin);

    /**
     * 根据Logintoken删除
     *
     * @param logintoken
     * @return
     */
    int deleteByLogintoken(String logintoken);

    /**
     * 根据Logintoken获取对象
     *
     * @param logintoken
     * @return
     */
    SysUserlogin selectByLogintoken(@Param("logintoken") String logintoken);

}
