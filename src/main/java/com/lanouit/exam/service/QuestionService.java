package com.lanouit.exam.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.github.pagehelper.PageInfo;
import com.lanouit.exam.exception.QuestionNotFoundException;
import com.lanouit.exam.po.QuestionWithBLOBs;

public interface QuestionService {

	/**
	 * 添加试题
	 * 
	 * @param question
	 * @return
	 */
	public QuestionWithBLOBs saveQuestion(QuestionWithBLOBs question);

	/**
	 * 更新试题
	 * 
	 * @param question
	 * @return
	 */
	public QuestionWithBLOBs updateQuestion(QuestionWithBLOBs question);

	/**
	 * 删除试题
	 * 
	 * @param question
	 */
	public void deleteQuestion(QuestionWithBLOBs question)
			throws QuestionNotFoundException;

	/**
	 * 跟住主键查询单个试题
	 * 
	 * @param id
	 * @return
	 */
	public QuestionWithBLOBs getById(Integer id);

	/**
	 * 分页查询试题
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<QuestionWithBLOBs> list(Integer pageNum, Integer pageSize);

	/**
	 * excel批量录入试题
	 * 
	 * @param is
	 * @return
	 */
	public Map<String, Object> batchInsertForExcel(InputStream is)
			throws EncryptedDocumentException, InvalidFormatException,
			IOException;

}
