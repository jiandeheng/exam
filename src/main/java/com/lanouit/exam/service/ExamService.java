package com.lanouit.exam.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lanouit.exam.exception.ExamHasReviewException;
import com.lanouit.exam.exception.ExamHasSubmitException;
import com.lanouit.exam.exception.ExamNotFoundException;
import com.lanouit.exam.po.ExamDetailWithBLOBs;
import com.lanouit.exam.po.ExamWithBLOBs;

public interface ExamService {

	/**
	 * 添加考试
	 * 
	 * @param exam
	 * @return
	 */
	public ExamWithBLOBs saveExam(ExamWithBLOBs exam);

	/**
	 * 更新考试
	 * 
	 * @param exam
	 * @return
	 */
	public ExamWithBLOBs updateExam(ExamWithBLOBs exam);
	
	/**
	 * 更新考试详细
	 * 
	 * @param examDetail
	 * @return
	 */
	public ExamDetailWithBLOBs updateExamDetail(ExamDetailWithBLOBs examDetail);

	/**
	 * 删除考试
	 * 
	 * @param exam
	 */
	public void deleteExam(ExamWithBLOBs exam) throws ExamNotFoundException;

	/**
	 * 根据主键查询考试
	 * 
	 * @param id
	 * @return
	 */
	public ExamWithBLOBs getById(Integer id);
	
	/**
	 * 根据考试详细主键获取考试详细
	 * @param id
	 * @return
	 */
	public ExamDetailWithBLOBs getExamDetailById(Integer id);
	
	/**
	 * 根据考试记录获取所有考试详细
	 * @param exam
	 * @return
	 */
	public List<ExamDetailWithBLOBs> getExamDetailsByExam(ExamWithBLOBs exam);

	/**
	 * 分页查询考试
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<ExamWithBLOBs> list(Integer pageNum, Integer pageSize);
	
	/**
	 * 提交考试
	 * 
	 * @param exam
	 * @return
	 */
	public ExamWithBLOBs submitExam(ExamWithBLOBs exam) throws ExamHasSubmitException;
	
	/**
	 * 人工审核考试
	 * 
	 * @param exam
	 * @return
	 */
	public ExamWithBLOBs reviewExam(ExamWithBLOBs exam) throws ExamHasReviewException;

}
