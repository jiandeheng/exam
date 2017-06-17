package com.lanouit.exam.mapper;

import com.lanouit.exam.po.ExamDetail;
import com.lanouit.exam.po.ExamDetailExample;
import com.lanouit.exam.po.ExamDetailWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    int countByExample(ExamDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    int deleteByExample(ExamDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    int insert(ExamDetailWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    int insertSelective(ExamDetailWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    List<ExamDetailWithBLOBs> selectByExampleWithBLOBs(ExamDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    List<ExamDetail> selectByExample(ExamDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    ExamDetailWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    int updateByExampleSelective(@Param("record") ExamDetailWithBLOBs record, @Param("example") ExamDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    int updateByExampleWithBLOBs(@Param("record") ExamDetailWithBLOBs record, @Param("example") ExamDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    int updateByExample(@Param("record") ExamDetail record, @Param("example") ExamDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    int updateByPrimaryKeySelective(ExamDetailWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    int updateByPrimaryKeyWithBLOBs(ExamDetailWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_detail
     *
     * @mbggenerated Fri Jun 16 20:46:23 CST 2017
     */
    int updateByPrimaryKey(ExamDetail record);
}