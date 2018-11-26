package com.zx.business.dao;

import com.zx.business.model.BusCustomer;
import org.springframework.stereotype.Repository;

@Repository
public interface BusCustomerDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_customer
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_customer
     *
     * @mbg.generated
     */
    int insert(BusCustomer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_customer
     *
     * @mbg.generated
     */
    int insertSelective(BusCustomer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_customer
     *
     * @mbg.generated
     */
    BusCustomer selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_customer
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BusCustomer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_customer
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BusCustomer record);
}