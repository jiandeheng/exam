package com.lanouit.exam.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanouit.exam.exception.SubjectExistException;
import com.lanouit.exam.exception.SubjectNotFoundException;
import com.lanouit.exam.mapper.PositionMapper;
import com.lanouit.exam.mapper.PositionSubjectMapper;
import com.lanouit.exam.mapper.SubjectMapper;
import com.lanouit.exam.po.Position;
import com.lanouit.exam.po.PositionSubject;
import com.lanouit.exam.po.PositionSubjectExample;
import com.lanouit.exam.po.Subject;
import com.lanouit.exam.po.SubjectExample;
import com.lanouit.exam.service.SubjectService;

@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {

	@Resource
	SubjectMapper subjectMapper;
	
	@Resource
	PositionMapper positionMapper;
	
	@Resource
	PositionSubjectMapper positionSubjectMapper;
	
	@Override
	public Subject saveSubject(Subject subject) throws SubjectExistException {
		SubjectExample subjectExample = new SubjectExample();
		subjectExample.createCriteria().andSubjectNameEqualTo(subject.getSubjectName());
		//已存在该科目
		if(!this.subjectMapper.selectByExample(subjectExample).isEmpty()) {
			throw new SubjectExistException("已存在该科目");
		}
		//添加科目
		this.subjectMapper.insert(subject);
		//添加该科目的职业
		if(subject.getPositions() != null) {
			for(Position position : subject.getPositions()) {
				PositionSubject positionSubject = new PositionSubject();
				positionSubject.setPositionId(position.getId());
				positionSubject.setSubjectId(subject.getId());
				this.positionSubjectMapper.insert(positionSubject);
			}
		}
		subject = this.getById(subject.getId());
		return subject;
	}

	@Override
	public Subject updateSubject(Subject subject) throws SubjectExistException {
		SubjectExample subjectExample = new SubjectExample();
		subjectExample.createCriteria().andSubjectNameEqualTo(subject.getSubjectName());
		//已存在该科目
		List<Subject> list = this.subjectMapper.selectByExample(subjectExample);
		if(!list.isEmpty()) {
			for(Subject s : list) {
				if(!s.getId().equals(subject.getId()) && s.getSubjectName().equals(subject.getSubjectName())) {
					throw new SubjectExistException("已存在该科目");
				}
			}
		}
		//更新科目
		this.subjectMapper.updateByPrimaryKey(subject);
		//更新科目包含的职业
		if(subject.getPositions() != null) {
			//删除之前的职业
			PositionSubjectExample positionSubjectExample = new PositionSubjectExample();
			positionSubjectExample.createCriteria().andSubjectIdEqualTo(subject.getId());
			this.positionSubjectMapper.deleteByExample(positionSubjectExample);
			//添加现有的职业
			for(Position position : subject.getPositions()) {
				PositionSubject positionSubject = new PositionSubject();
				positionSubject.setPositionId(position.getId());
				positionSubject.setSubjectId(subject.getId());
				this.positionSubjectMapper.insert(positionSubject);
			}
		}
		subject = this.getById(subject.getId());
		return subject;
	}

	@Override
	public void deleteSubject(Subject subject) throws SubjectNotFoundException {
		int row = this.subjectMapper.deleteByPrimaryKey(subject.getId());
		if(row == 0) {
			throw new SubjectNotFoundException();
		}
		PositionSubjectExample positionSubjectExample = new PositionSubjectExample();
		positionSubjectExample.createCriteria().andSubjectIdEqualTo(subject.getId());
		this.positionSubjectMapper.deleteByExample(positionSubjectExample);
	}

	@Override
	public Subject getById(Integer id) {
		Subject subject = this.subjectMapper.selectByPrimaryKey(id);
		//获取该科目的职业
		List<Position> positions = new ArrayList<Position>();
		PositionSubjectExample positionSubjectExample = new PositionSubjectExample();
		positionSubjectExample.createCriteria().andSubjectIdEqualTo(id);
		List<PositionSubject> positionSubjects = this.positionSubjectMapper.selectByExample(positionSubjectExample);
		for(PositionSubject positionSubject : positionSubjects) {
			Position position = this.positionMapper.selectByPrimaryKey(positionSubject.getPositionId());
			if(position != null) {
				positions.add(position);
			}
		}
		subject.setPositions(positions);;
		return subject;
	}

	@Override
	public PageInfo<Subject> list(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		SubjectExample subjectExample = new SubjectExample();
		List<Subject> subjects = this.subjectMapper.selectByExample(subjectExample);
		//查询每个职业的科目
		for(Subject subject : subjects) {
			List<Position> positions = new ArrayList<Position>();
			PositionSubjectExample positionSubjectExample = new PositionSubjectExample();
			positionSubjectExample.createCriteria().andSubjectIdEqualTo(subject.getId());
			List<PositionSubject> positionSubjects = this.positionSubjectMapper.selectByExample(positionSubjectExample);
			for(PositionSubject positionSubject : positionSubjects) {
				Position position = this.positionMapper.selectByPrimaryKey(positionSubject.getPositionId());
				if(position != null) {
					positions.add(position);
				}
			}
			subject.setPositions(positions);;
		}
		return new PageInfo<Subject>(subjects);
	}

}
