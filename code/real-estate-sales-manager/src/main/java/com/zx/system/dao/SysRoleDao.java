package com.zx.system.dao;

import com.zx.system.model.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * 角色是系统权限与资源分配的最小单位，一个用户可以有多个角色并继承了所拥有角色的所有权限和资源 数据库操作接口
 * @author wangx
 */
@Repository("sysRoleDao")
public interface SysRoleDao {
	/**
	 * 插入记录
	 *
	 * @param sysRole
	 * @return
	 */
	int insert(SysRole sysRole);

	/**
	 * 批量插入记录
	 *
	 * @param sysRoleList
	 * @return
	 */
	int insertBatch(List<SysRole> sysRoleList);

	/**
	 * 根据Id删除
	 *
	 * @param id
	 * @return
	 */
	int deleteById(Integer id);

	/**
	 * 根据Id获取对象
	 *
	 * @param id 对象主键
	 * @return
	 */
	SysRole selectById(Integer id);

	/**
	 * 查询符合条件的数据量
	 *
	 * @param map 查询条件
	 * @return
	 */
	int selectCount(Map map);

	/**
	 * Method description
	 *
	 * @param role 更新SysRole
	 * @return
	 */
	int update(SysRole role);

	/**
	 * 根据条件查询数据
	 *
	 * @param map 查询条件
	 * @return
	 */
	List<SysRole> selectList(Map map);

	/**
	 * 获取用户所有角色
	 * @param userid
	 * @return
	 */
    List<SysRole> getListByUserId(Integer userid);
}
