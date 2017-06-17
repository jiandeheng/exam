package com.lanouit.exam.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanouit.exam.exception.PositionExistException;
import com.lanouit.exam.exception.PositionNotFoundException;
import com.lanouit.exam.mapper.PositionMapper;
import com.lanouit.exam.mapper.PositionSubjectMapper;
import com.lanouit.exam.mapper.SubjectMapper;
import com.lanouit.exam.po.Position;
import com.lanouit.exam.po.PositionExample;
import com.lanouit.exam.po.PositionSubject;
import com.lanouit.exam.po.PositionSubjectExample;
import com.lanouit.exam.po.Subject;
import com.lanouit.exam.service.PositionService;

@Service("positionService")
public class PositionServiceImpl implements PositionService {
	
	@Resource
	PositionMapper positionMapper;
	
	@Resource
	PositionSubjectMapper positionSubjectMapper;
	
	@Resource
	SubjectMapper subjectMapper;

	@Override
	public Position savePosition(Position position) throws PositionExistException {
		position.setPositionName(position.getPositionName().trim());
		PositionExample positionExample = new PositionExample();
		positionExample.createCriteria().andPositionNameEqualTo(position.getPositionName());
		//已存在该职业
		if(!this.positionMapper.selectByExample(positionExample).isEmpty()) {
			throw new PositionExistException("职业已存在");
		}
		//添加职位
		this.positionMapper.insert(position);
		//添加职位包含的科目
		if(position.getSubjects() != null) {
			for(Subject subject : position.getSubjects()) {
				PositionSubject positionSubject = new PositionSubject();
				positionSubject.setPositionId(position.getId());
				positionSubject.setSubjectId(subject.getId());
				this.positionSubjectMapper.insert(positionSubject);
			}
		}
		position = this.getById(position.getId());
		return position;
	}

	@Override
	public Position updatePosition(Position position) throws PositionExistException {
		position.setPositionName(position.getPositionName().trim());
		PositionExample positionExample = new PositionExample();
		positionExample.createCriteria().andPositionNameEqualTo(position.getPositionName());
		//已存在该职业
		List<Position> list = this.positionMapper.selectByExample(positionExample);
		if(!list.isEmpty()) {
			for(Position p : list) {
				if(!p.getId().equals(position.getId()) && p.getPositionName().equals(position.getPositionName())) {
					throw new PositionExistException("职业已存在");
				}
			}
		}
		//更新职业信息
		this.positionMapper.updateByPrimaryKey(position);
		//更新该职业的包含科目
		if(position.getSubjects() != null) {
			//先删除之前的科目
			PositionSubjectExample positionSubjectExample = new PositionSubjectExample();
			positionSubjectExample.createCriteria().andPositionIdEqualTo(position.getId());
			this.positionSubjectMapper.deleteByExample(positionSubjectExample);
			//添加现有的科目
			for(Subject subject : position.getSubjects()) {
				PositionSubject positionSubject = new PositionSubject();
				positionSubject.setPositionId(position.getId());
				positionSubject.setSubjectId(subject.getId());
				this.positionSubjectMapper.insert(positionSubject);
			}
		}
		position = this.getById(position.getId());
		return position;
	}

	@Override
	public void deletePosition(Position position) throws PositionNotFoundException {
		int row = this.positionMapper.deleteByPrimaryKey(position.getId());
		if(row == 0) { 
			throw new PositionNotFoundException("找不到该职业");
		}
		PositionSubjectExample positionSubjectExample = new PositionSubjectExample();
		positionSubjectExample.createCriteria().andPositionIdEqualTo(position.getId());
		this.positionSubjectMapper.deleteByExample(positionSubjectExample);
	}

	@Override
	public Position getById(Integer id) {
		Position position = this.positionMapper.selectByPrimaryKey(id);
		List<Subject> subjects = new ArrayList<Subject>();
		PositionSubjectExample positionSubjectExample = new PositionSubjectExample();
		positionSubjectExample.createCriteria().andPositionIdEqualTo(id);
		List<PositionSubject> positionSubjects = this.positionSubjectMapper.selectByExample(positionSubjectExample);
		for(PositionSubject positionSubject : positionSubjects) {
			Subject subject = this.subjectMapper.selectByPrimaryKey(positionSubject.getSubjectId());
			if(subject != null) {
				subjects.add(subject);
			}
		}
		position.setSubjects(subjects);
		return position;
	}

	@Override
	public PageInfo<Position> list(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		PositionExample positionExample = new PositionExample();
		List<Position> positions = this.positionMapper.selectByExample(positionExample);
		//查询每个职业的科目
		for(Position position : positions) {
			List<Subject> subjects = new ArrayList<Subject>();
			PositionSubjectExample positionSubjectExample = new PositionSubjectExample();
			positionSubjectExample.createCriteria().andPositionIdEqualTo(position.getId());
			List<PositionSubject> positionSubjects = this.positionSubjectMapper.selectByExample(positionSubjectExample);
			for(PositionSubject positionSubject : positionSubjects) {
				Subject subject = this.subjectMapper.selectByPrimaryKey(positionSubject.getSubjectId());
				if(subject != null) {
					subjects.add(subject);
				}
			}
			position.setSubjects(subjects);
		}
		return new PageInfo<Position>(positions);
	}

}



