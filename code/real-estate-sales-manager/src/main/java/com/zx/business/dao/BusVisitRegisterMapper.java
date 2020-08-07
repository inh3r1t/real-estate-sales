package com.zx.business.dao;

import com.zx.business.model.BusDeal;
import com.zx.business.model.BusVisitRegister;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BusVisitRegisterMapper {
    
    int insert(BusVisitRegister record);
    int updateByPrimaryKey(BusVisitRegister record);

    Integer countByModel(
            @Param("startDateTime") String startDateTime, @Param("endDateTime") String endDateTime);

    List<BusVisitRegister> selectByPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize,
                                        @Param("orderField") String orderField, @Param("orderType") String orderType,
                                        @Param("startDateTime") String startDateTime, @Param("endDateTime") String endDateTime);

    BusVisitRegister selectByPrimaryKey(Integer id);

    void deleteByPrimaryKey(Integer id);
}