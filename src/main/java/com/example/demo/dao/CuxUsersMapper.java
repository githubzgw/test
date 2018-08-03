package com.example.demo.dao;

import com.example.demo.pojo.CuxUsers;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuxUsersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cux_users
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cux_users
     *
     * @mbggenerated
     */
    int insert(CuxUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cux_users
     *
     * @mbggenerated
     */
    int insertSelective(CuxUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cux_users
     *
     * @mbggenerated
     */
    CuxUsers selectByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cux_users
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CuxUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cux_users
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CuxUsers record);

    List<Object> selectAll();

    CuxUsers selectByUsername(String userName);
}