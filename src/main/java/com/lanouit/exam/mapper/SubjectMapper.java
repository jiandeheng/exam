package com.lanouit.exam.mapper;

import com.lanouit.exam.po.Subject;
import com.lanouit.exam.po.SubjectExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface SubjectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int countByExample(SubjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int deleteByExample(SubjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int insert(Subject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int insertSelective(Subject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    List<Subject> selectByExample(SubjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    Subject selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int updateByExampleSelective(@Param("record") Subject record, @Param("example") SubjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int updateByExample(@Param("record") Subject record, @Param("example") SubjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int updateByPrimaryKeySelective(Subject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    int updateByPrimaryKey(Subject record);
}