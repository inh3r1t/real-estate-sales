package com.zx.business.dao;

import com.zx.business.model.BusNotifyMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusNotifyMsgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_notify_msg
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_notify_msg
     *
     * @mbg.generated
     */
    int insert(BusNotifyMsg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_notify_msg
     *
     * @mbg.generated
     */
    int insertSelective(BusNotifyMsg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_notify_msg
     *
     * @mbg.generated
     */
    BusNotifyMsg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_notify_msg
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BusNotifyMsg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_notify_msg
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(BusNotifyMsg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_notify_msg
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BusNotifyMsg record);

    List<BusNotifyMsg> selectByPage(@Param("start") Integer start, @Param("pageSize") Integer size, @Param("orderField") String orderField,
                                     @Param("orderType") String orderType, @Param("busNotifyMsg") BusNotifyMsg busNotifyMsg);

    Long countByModel(BusNotifyMsg condition);
}