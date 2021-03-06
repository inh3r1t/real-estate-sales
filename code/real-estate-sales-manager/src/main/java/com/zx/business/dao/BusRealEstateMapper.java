package com.zx.business.dao;

import com.zx.business.model.BusRealEstate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BusRealEstateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_real_estate
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_real_estate
     *
     * @mbg.generated
     */
    int insert(BusRealEstate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_real_estate
     *
     * @mbg.generated
     */
    int insertSelective(BusRealEstate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_real_estate
     *
     * @mbg.generated
     */
    BusRealEstate selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_real_estate
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BusRealEstate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_real_estate
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BusRealEstate record);

    List<BusRealEstate> selectByPage(@Param("start") Integer start, @Param("pageSize") Integer size, @Param("orderField") String orderField,
                                     @Param("orderType") String orderType, @Param("busRealEstate") BusRealEstate busRealEstate);

    Long countByModel(BusRealEstate busRealEstate);

    String selectThumbnail(String realEstateId);

    List<String> selectImages(String realEstateId);
}