package com.lanouit.exam.mapper;

import com.lanouit.exam.po.Question;
import com.lanouit.exam.po.QuestionExample;
import com.lanouit.exam.po.QuestionWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuestionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    int countByExample(QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    int deleteByExample(QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    int insert(QuestionWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    int insertSelective(QuestionWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    List<QuestionWithBLOBs> selectByExampleWithBLOBs(QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    List<Question> selectByExample(QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    QuestionWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    int updateByExampleSelective(@Param("record") QuestionWithBLOBs record, @Param("example") QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    int updateByExampleWithBLOBs(@Param("record") QuestionWithBLOBs record, @Param("example") QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    int updateByExample(@Param("record") Question record, @Param("example") QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    int updateByPrimaryKeySelective(QuestionWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    int updateByPrimaryKeyWithBLOBs(QuestionWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbggenerated Mon Jun 12 15:14:43 CST 2017
     */
    int updateByPrimaryKey(Question record);
    
    /**
     * 根据科目，题型，随机查询有限个记录
     * @param subjectId 科目ID
     * @param type 题型
     * @param limit 查询个数
     * @return
     */
    List<QuestionWithBLOBs> selectRandBySubjectAndType(@Param("subjectId") Integer subjectId,@Param("type") Integer type,@Param("limit") Integer limit);
}