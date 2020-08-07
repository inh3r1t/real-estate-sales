package com.zx.business.dao;

import com.zx.business.model.BusDeal;
import com.zx.business.model.BusVisitRegister;
import com.zx.system.model.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BusVisitRegisterMapper {

    int insert(BusVisitRegister record);

    int updateByPrimaryKey(BusVisitRegister record);

    Integer countByModel(
            @Param("startDateTime") String startDateTime,
            @Param("endDateTime") String endDateTime,
            @Param("createrid") Integer createrid);

    List<BusVisitRegister> selectByPage(@Param("start") Integer start,
                                        @Param("pageSize") Integer pageSize,
                                        @Param("orderField") String orderField,
                                        @Param("orderType") String orderType,
                                        @Param("startDateTime") String startDateTime,
                                        @Param("endDateTime") String endDateTime,
                                        @Param("createrid") Integer createrid);

    BusVisitRegister selectByPrimaryKey(Integer id);

    void deleteByPrimaryKey(Integer id);


    /**
     * 根据条件统计数量
     *
     * @param map 条件
     **/
    int countByModel(Map map);

    /**
     * 查询
     *
     * @param map 查询条件
     **/
    List<BusVisitRegister> selectByPage(Map map);
}