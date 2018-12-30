package com.zx.business.dao;

import com.zx.business.model.BusAgentCompany;
import com.zx.business.model.BusDeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusAgentCompanyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_agent_company
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_agent_company
     *
     * @mbg.generated
     */
    int insert(BusAgentCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_agent_company
     *
     * @mbg.generated
     */
    int insertSelective(BusAgentCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_agent_company
     *
     * @mbg.generated
     */
    BusAgentCompany selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_agent_company
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BusAgentCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_agent_company
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BusAgentCompany record);

    List<BusDeal> selectByPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize,
                               @Param("orderField") String orderField, @Param("orderType") String orderType,
                               @Param("busAgentCompany")BusAgentCompany busAgentCompany);

}