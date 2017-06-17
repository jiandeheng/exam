package com.lanouit.exam.mapper;

import com.lanouit.exam.po.Position;
import com.lanouit.exam.po.PositionExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface PositionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int countByExample(PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int deleteByExample(PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int insert(Position record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int insertSelective(Position record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    List<Position> selectByExample(PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    Position selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int updateByExampleSelective(@Param("record") Position record, @Param("example") PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int updateByExample(@Param("record") Position record, @Param("example") PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int updateByPrimaryKeySelective(Position record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int updateByPrimaryKey(Position record);
}