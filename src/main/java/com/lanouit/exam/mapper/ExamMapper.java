package com.lanouit.exam.mapper;

import com.lanouit.exam.po.Exam;
import com.lanouit.exam.po.ExamExample;
import com.lanouit.exam.po.ExamWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    int countByExample(ExamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    int deleteByExample(ExamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    int insert(ExamWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    int insertSelective(ExamWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    List<ExamWithBLOBs> selectByExampleWithBLOBs(ExamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    List<Exam> selectByExample(ExamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    ExamWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    int updateByExampleSelective(@Param("record") ExamWithBLOBs record, @Param("example") ExamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    int updateByExampleWithBLOBs(@Param("record") ExamWithBLOBs record, @Param("example") ExamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    int updateByExample(@Param("record") Exam record, @Param("example") ExamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    int updateByPrimaryKeySelective(ExamWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    int updateByPrimaryKeyWithBLOBs(ExamWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam
     *
     * @mbggenerated Fri Jun 16 14:28:21 CST 2017
     */
    int updateByPrimaryKey(Exam record);
}