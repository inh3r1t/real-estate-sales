package com.zx.business.dao;

import com.zx.business.model.BusAccountManage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusAccountManageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_account_manage
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_account_manage
     *
     * @mbg.generated
     */
    int insert(BusAccountManage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_account_manage
     *
     * @mbg.generated
     */
    int insertSelective(BusAccountManage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_account_manage
     *
     * @mbg.generated
     */
    BusAccountManage selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_account_manage
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BusAccountManage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_account_manage
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BusAccountManage record);

    BusAccountManage selectVerifyCode(@Param("phoneNum") String phoneNum);

    List<BusAccountManage> selectByModel(BusAccountManage busAccountManage);

    long countByModel(BusAccountManage busAccountManage);

    List<BusAccountManage> selectByPage(@Param("start") Integer start, @Param("pageSize") Integer size, @Param("orderField") String orderField,
                                        @Param("orderType") String orderType, @Param("busAccountManage") BusAccountManage busAccountManage);
}