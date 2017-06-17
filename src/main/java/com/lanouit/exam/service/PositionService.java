package com.lanouit.exam.service;

import com.github.pagehelper.PageInfo;
import com.lanouit.exam.exception.PositionExistException;
import com.lanouit.exam.exception.PositionNotFoundException;
import com.lanouit.exam.po.Position;

public interface PositionService {

	/**
	 * 添加职业
	 * 
	 * @param position
	 * @return
	 */
	public Position savePosition(Position position) throws PositionExistException;
	
	/**
	 * 更新职业
	 * 
	 * @param position
	 * @return
	 */
	public Position updatePosition(Position position) throws PositionExistException;
	
	/**
	 * 删除该职业
	 * 
	 * @param position
	 */
	public void deletePosition(Position position) throws PositionNotFoundException;
	
	/**
	 * 根据主键查询职业
	 * 
	 * @param id
	 * @return
	 */
	public Position getById(Integer id);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Position> list(Integer pageNum, Integer pageSize);
	
}














 