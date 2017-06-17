package com.lanouit.exam.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanouit.exam.exception.QuestionNotFoundException;
import com.lanouit.exam.mapper.QuestionMapper;
import com.lanouit.exam.mapper.QuestionSubjectMapper;
import com.lanouit.exam.mapper.SubjectMapper;
import com.lanouit.exam.po.QuestionExample;
import com.lanouit.exam.po.QuestionSubject;
import com.lanouit.exam.po.QuestionSubjectExample;
import com.lanouit.exam.po.QuestionWithBLOBs;
import com.lanouit.exam.po.Subject;
import com.lanouit.exam.service.QuestionService;
import com.lanouit.exam.util.Constant;
import com.lanouit.exam.util.excel.ExcelUtil;
import com.lanouit.exam.util.question.QuestionParseUtil;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

	@Resource
	QuestionMapper questionMapper;

	@Resource
	SubjectMapper subjectMapper;

	@Resource
	QuestionSubjectMapper questionSubjectMapper;

	@Override
	public QuestionWithBLOBs saveQuestion(QuestionWithBLOBs question) {
		// 如果是选择题，解析选项
		Integer questionType = question.getType();
		if (Constant.SINGLE_ANSWER == questionType
				|| Constant.MULTIPLE_CHOICE_ANSWERS == questionType
				|| Constant.JUDGE_ANSWER == questionType) {
			// 解析选项
			List<Map<String, String>> options = question.getOptions();
			if (options != null) {
				String option = QuestionParseUtil.parseOptions(options);
				question.setQuestionOption(option);
			}
			// 解析答案
			List<String> answers = question.getAnswers();
			if (answers != null) {
				String answer = QuestionParseUtil.parseAnswers(answers);
				question.setAnswer(answer);
			}
		}
		question.setCreateTime(new Date());
		System.out.println(question);
		// 保存试题
		this.questionMapper.insert(question);
		// 添加试题科目关系
		List<Subject> subjects = question.getSubjects();
		if (subjects != null) {
			for (Subject subject : subjects) {
				QuestionSubject questionSubject = new QuestionSubject();
				questionSubject.setQuestionId(question.getId());
				questionSubject.setSubjectId(subject.getId());
				this.questionSubjectMapper.insert(questionSubject);
			}
		}
		question = this.getById(question.getId());
		return question;
	}

	@Override
	public QuestionWithBLOBs updateQuestion(QuestionWithBLOBs question) {
		// 如果是选择题，解析选项
		Integer questionType = question.getType();
		if (Constant.SINGLE_ANSWER == questionType
				|| Constant.MULTIPLE_CHOICE_ANSWERS == questionType
				|| Constant.JUDGE_ANSWER == questionType) {
			// 解析选项
			List<Map<String, String>> options = question.getOptions();
			if (options != null) {
				String option = QuestionParseUtil.parseOptions(options);
				question.setQuestionOption(option);
			}
			// 解析答案
			List<String> answers = question.getAnswers();
			if (answers != null) {
				String answer = QuestionParseUtil.parseAnswers(answers);
				question.setAnswer(answer);
			}
		}
		// 更新试题
		this.questionMapper.updateByPrimaryKeySelective(question);
		// 添加试题科目关系
		List<Subject> subjects = question.getSubjects();
		if (subjects != null) {
			for (Subject subject : subjects) {
				// 删除之前的
				QuestionSubjectExample questionSubjectExample = new QuestionSubjectExample();
				questionSubjectExample.createCriteria().andQuestionIdEqualTo(
						question.getId());
				this.questionSubjectMapper
						.deleteByExample(questionSubjectExample);
				// 添加现有的
				QuestionSubject questionSubject = new QuestionSubject();
				questionSubject.setQuestionId(question.getId());
				questionSubject.setSubjectId(subject.getId());
				this.questionSubjectMapper.insert(questionSubject);
			}
		}
		question = this.getById(question.getId());
		return question;
	}

	@Override
	public void deleteQuestion(QuestionWithBLOBs question)
			throws QuestionNotFoundException {
		int row = this.questionMapper.deleteByPrimaryKey(question.getId());
		// 没有该记录
		if (row == 0) {
			throw new QuestionNotFoundException("找不到该试题");
		}
		// 删除试题与科目的关联
		QuestionSubjectExample questionSubjectExample = new QuestionSubjectExample();
		questionSubjectExample.createCriteria().andQuestionIdEqualTo(
				question.getId());
		this.questionSubjectMapper.deleteByExample(questionSubjectExample);
	}

	@Override
	public QuestionWithBLOBs getById(Integer id) {
		QuestionWithBLOBs question = this.questionMapper.selectByPrimaryKey(id);
		// 如果是选择题，解析选项,解析答案
		Integer questionType = question.getType();
		if (Constant.SINGLE_ANSWER == questionType
				|| Constant.MULTIPLE_CHOICE_ANSWERS == questionType
				|| Constant.JUDGE_ANSWER == questionType) {
			List<Map<String, String>> options = QuestionParseUtil
					.parseOption(question.getQuestionOption());
			List<String> answers = QuestionParseUtil.parseAnswer(question
					.getAnswer());
			question.setOptions(options);
			question.setAnswers(answers);
		}
		// 获取试题所属科目
		List<Subject> subjects = new ArrayList<Subject>();
		QuestionSubjectExample questionSubjectExample = new QuestionSubjectExample();
		questionSubjectExample.createCriteria().andQuestionIdEqualTo(
				question.getId());
		List<QuestionSubject> questionSubjects = this.questionSubjectMapper
				.selectByExample(questionSubjectExample);
		for (QuestionSubject questionSubject : questionSubjects) {
			Subject subject = this.subjectMapper
					.selectByPrimaryKey(questionSubject.getSubjectId());
			subjects.add(subject);
		}
		question.setSubjects(subjects);
		return question;
	}

	@Override
	public PageInfo<QuestionWithBLOBs> list(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		QuestionExample questionExample = new QuestionExample();
		List<QuestionWithBLOBs> questions = this.questionMapper
				.selectByExampleWithBLOBs(questionExample);
		// 遍历所有试题
		for (QuestionWithBLOBs question : questions) {
			// 如果是选择题，解析选项,解析答案
			Integer questionType = question.getType();
			if (Constant.SINGLE_ANSWER == questionType
					|| Constant.MULTIPLE_CHOICE_ANSWERS == questionType
					|| Constant.JUDGE_ANSWER == questionType) {
				List<Map<String, String>> options = QuestionParseUtil
						.parseOption(question.getQuestionOption());
				List<String> answers = QuestionParseUtil.parseAnswer(question
						.getAnswer());
				question.setOptions(options);
				question.setAnswers(answers);
			}
			// 获取试题所属科目
			List<Subject> subjects = new ArrayList<Subject>();
			QuestionSubjectExample questionSubjectExample = new QuestionSubjectExample();
			questionSubjectExample.createCriteria().andQuestionIdEqualTo(
					question.getId());
			List<QuestionSubject> questionSubjects = this.questionSubjectMapper
					.selectByExample(questionSubjectExample);
			for (QuestionSubject questionSubject : questionSubjects) {
				Subject subject = this.subjectMapper
						.selectByPrimaryKey(questionSubject.getSubjectId());
				subjects.add(subject);
			}
			question.setSubjects(subjects);
		}
		return new PageInfo<QuestionWithBLOBs>(questions);
	}

	@Override
	public Map<String, Object> batchInsertForExcel(InputStream is)
			throws EncryptedDocumentException, InvalidFormatException,
			IOException {
		ExcelUtil excelUtil = new ExcelUtil(is, this.subjectMapper);
		Map<String, Object> result = excelUtil.buildBeans();
		@SuppressWarnings("unchecked")
		List<QuestionWithBLOBs> questions = (List<QuestionWithBLOBs>) result.get("questions");
		if(!questions.isEmpty()) {
			for(int i = 0;i<questions.size();i++){
				QuestionWithBLOBs question = questions.get(i);
				question.setCreateTime(new Date());
				question = this.saveQuestion(question);
				questions.set(i, question);
			}
		}
		result.put("questions", questions);
		return result;
	}

}
