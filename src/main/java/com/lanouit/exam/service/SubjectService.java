package com.lanouit.exam.service;

import com.github.pagehelper.PageInfo;
import com.lanouit.exam.exception.SubjectExistException;
import com.lanouit.exam.exception.SubjectNotFoundException;
import com.lanouit.exam.po.Subject;

public interface SubjectService {
	
	/**
	 * 添加科目
	 * 
	 * @param subject
	 * @return
	 */
	public Subject saveSubject(Subject subject) throws SubjectExistException;
	
	/**
	 * 更新科目
	 * 
	 * @param subject
	 * @return
	 */
	public Subject updateSubject(Subject subject) throws SubjectExistException;
	
	/**
	 * 删除该科目
	 * 
	 * @param subject
	 */
	public void deleteSubject(Subject subject) throws SubjectNotFoundException;
	
	/**
	 * 根据主键查询科目
	 * 
	 * @param id
	 * @return
	 */
	public Subject getById(Integer id);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Subject> list(Integer pageNum, Integer pageSize);
	
}
