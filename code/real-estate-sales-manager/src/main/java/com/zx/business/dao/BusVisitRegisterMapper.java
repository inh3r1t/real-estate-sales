package com.zx.business.dao;

import com.zx.business.model.BusVisitRegister;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BusVisitRegisterMapper {
    Integer countByModel(
                       @Param("startDateTime") String startDateTime, @Param("endDateTime") String endDateTime);

    List<BusVisitRegister> selectByPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize,
                                        @Param("orderField") String orderField, @Param("orderType") String orderType,
                                        @Param("startDateTime") String startDateTime, @Param("endDateTime") String endDateTime);
}