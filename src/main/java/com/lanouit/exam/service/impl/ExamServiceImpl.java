package com.lanouit.exam.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanouit.exam.exception.ExamHasReviewException;
import com.lanouit.exam.exception.ExamHasSubmitException;
import com.lanouit.exam.exception.ExamNotFoundException;
import com.lanouit.exam.mapper.ExamDetailMapper;
import com.lanouit.exam.mapper.ExamMapper;
import com.lanouit.exam.mapper.PositionMapper;
import com.lanouit.exam.mapper.PositionSubjectMapper;
import com.lanouit.exam.mapper.QuestionMapper;
import com.lanouit.exam.mapper.QuestionSubjectMapper;
import com.lanouit.exam.mapper.SubjectMapper;
import com.lanouit.exam.po.ExamDetailExample;
import com.lanouit.exam.po.ExamDetailWithBLOBs;
import com.lanouit.exam.po.ExamExample;
import com.lanouit.exam.po.ExamWithBLOBs;
import com.lanouit.exam.po.Position;
import com.lanouit.exam.po.PositionSubject;
import com.lanouit.exam.po.PositionSubjectExample;
import com.lanouit.exam.po.QuestionSubject;
import com.lanouit.exam.po.QuestionSubjectExample;
import com.lanouit.exam.po.QuestionWithBLOBs;
import com.lanouit.exam.po.Subject;
import com.lanouit.exam.service.ExamService;
import com.lanouit.exam.util.Constant;
import com.lanouit.exam.util.ExamParam;
import com.lanouit.exam.util.question.QuestionParseUtil;

@Service("examService")
public class ExamServiceImpl implements ExamService {

	@Resource
	ExamMapper examMapper;

	@Resource
	ExamDetailMapper examDetailMapper;

	@Resource
	QuestionMapper questionMapper;

	@Resource
	PositionMapper positionMapper;

	@Resource
	PositionSubjectMapper positionSubjectMapper;

	@Resource
	SubjectMapper subjectMapper;

	@Resource
	QuestionSubjectMapper questionSubjectMapper;

	@Override
	public ExamWithBLOBs saveExam(ExamWithBLOBs exam) {
		// 保存考试记录
		Date startTime = new Date();
		Date endTime = new Date(startTime.getTime() + exam.getExamMinute() * 60
				* 1000);
		exam.setStartTime(startTime);
		exam.setEndTime(endTime);
		exam.setAnalysis("");
		exam.setReviewAnalysis("");
		this.examMapper.insertSelective(exam);
		// 随机抽取考试题型
		List<QuestionWithBLOBs> questions = randomExtractQuestion();
		// 保存考试题型
		if (!questions.isEmpty()) {
			for (QuestionWithBLOBs question : questions) {
				ExamDetailWithBLOBs examDetail = new ExamDetailWithBLOBs();
				examDetail.setExamId(exam.getId());
				examDetail.setQuestionId(question.getId());
				examDetail.setReviewAnalysis("");
				examDetail.setAnswer("");
				examDetail.setCode("");
				this.examDetailMapper.insertSelective(examDetail);
			}
		}
		// 获取最新信息
		exam = this.getById(exam.getId());
		return exam;
	}

	@Override
	public ExamWithBLOBs updateExam(ExamWithBLOBs exam) {
		this.examMapper.updateByPrimaryKeySelective(exam);
		exam = this.getById(exam.getId());
		return exam;
	}

	@Override
	public ExamDetailWithBLOBs updateExamDetail(ExamDetailWithBLOBs examDetail) {
		this.examDetailMapper.updateByPrimaryKeySelective(examDetail);
		examDetail = this.getExamDetailById(examDetail.getId());
		return examDetail;
	}

	@Override
	public void deleteExam(ExamWithBLOBs exam) throws ExamNotFoundException {
		// 删除考试记录
		int row = this.examMapper.deleteByPrimaryKey(exam.getId());
		if (row == 0) {
			throw new ExamNotFoundException("没有找到该考试记录");
		}
		// 删除考试记录的详细
		ExamDetailExample examDetailExample = new ExamDetailExample();
		examDetailExample.createCriteria().andExamIdEqualTo(exam.getId());
		this.examDetailMapper.deleteByExample(examDetailExample);
	}

	@Override
	public ExamWithBLOBs getById(Integer id) {
		// 获取考试记录
		ExamWithBLOBs exam = this.examMapper.selectByPrimaryKey(id);
		List<ExamDetailWithBLOBs> examDetails = new ArrayList<ExamDetailWithBLOBs>();
		ExamDetailExample examDetailExample = new ExamDetailExample();
		examDetailExample.createCriteria().andExamIdEqualTo(exam.getId());
		examDetails = this.examDetailMapper
				.selectByExampleWithBLOBs(examDetailExample);
		// 获取每个考试详细的试题信息
		for (ExamDetailWithBLOBs examDetail : examDetails) {
			QuestionWithBLOBs question = this.questionMapper
					.selectByPrimaryKey(examDetail.getQuestionId());
			examDetail.setQuestion(question);
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
		}
		exam.setExamDetails(examDetails);
		return exam;
	}

	@Override
	public ExamDetailWithBLOBs getExamDetailById(Integer id) {
		ExamDetailWithBLOBs examDetail = this.examDetailMapper
				.selectByPrimaryKey(id);
		QuestionWithBLOBs question = this.questionMapper
				.selectByPrimaryKey(examDetail.getQuestionId());
		examDetail.setQuestion(question);
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
		return examDetail;
	}

	@Override
	public List<ExamDetailWithBLOBs> getExamDetailsByExam(ExamWithBLOBs exam) {
		// 获取该考试的考试详细
		List<ExamDetailWithBLOBs> examDetails = new ArrayList<ExamDetailWithBLOBs>();
		ExamDetailExample examDetailExample = new ExamDetailExample();
		examDetailExample.createCriteria().andExamIdEqualTo(exam.getId());
		examDetails = this.examDetailMapper
				.selectByExampleWithBLOBs(examDetailExample);
		// 获取每个考试详细的试题信息
		for (ExamDetailWithBLOBs examDetail : examDetails) {
			QuestionWithBLOBs question = this.questionMapper
					.selectByPrimaryKey(examDetail.getQuestionId());
			examDetail.setQuestion(question);
		}
		return examDetails;
	}

	@Override
	public PageInfo<ExamWithBLOBs> list(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		ExamExample examExample = new ExamExample();
		List<ExamWithBLOBs> exams = this.examMapper
				.selectByExampleWithBLOBs(examExample);
		return new PageInfo<ExamWithBLOBs>(exams);
	}

	@Override
	public ExamWithBLOBs submitExam(ExamWithBLOBs exam)
			throws ExamHasSubmitException {
		// 检查是否已提交
		ExamWithBLOBs examTmp = this.examMapper
				.selectByPrimaryKey(exam.getId());
		if (examTmp.getIsSubmit() == 1) {
			throw new ExamHasSubmitException("该考试已经提交了,不能再提交");
		}
		// 保存考试记录信息
		this.examMapper.updateByPrimaryKeySelective(exam);
		// 保存用户回答的答案
		if (!exam.getExamDetails().isEmpty()) {
			for (ExamDetailWithBLOBs examDetail : exam.getExamDetails()) {
				this.examDetailMapper.updateByPrimaryKeySelective(examDetail);
			}
		}
		// 根据主键获取考试对象(全信息)
		exam = this.getById(exam.getId());
		// 根据考试的职业名称获取该职业所需的科目
		List<Subject> subjects = new ArrayList<Subject>();
		PositionSubjectExample positionSubjectExample = new PositionSubjectExample();
		positionSubjectExample.createCriteria().andPositionIdEqualTo(
				exam.getPosition());
		List<PositionSubject> positionSubjects = this.positionSubjectMapper
				.selectByExample(positionSubjectExample);
		for (PositionSubject positionSubject : positionSubjects) {
			Subject subject = this.subjectMapper
					.selectByPrimaryKey(positionSubject.getSubjectId());
			if (subject != null) {
				subjects.add(subject);
			}
		}
		// 各科目的试题得分，总分情况
		Map<String, Map<String, Double>> scoreMap = new HashMap<String, Map<String, Double>>();
		// 对答案，计算得分
		for (ExamDetailWithBLOBs examDetail : exam.getExamDetails()) {
			QuestionWithBLOBs question = examDetail.getQuestion();
			// 填空题对答案
			if (question.getType() == Constant.FILL_IN_THE_BLANK_ANSWERS) {
				// 试题答案
				String questionAnswer = question.getAnswer();
				String[] questionAnswerArr = new String[1];
				questionAnswerArr[0] = questionAnswer;
				if (questionAnswer.lastIndexOf("|") != -1) {
					questionAnswerArr = questionAnswer.split("|");
				}
				// 用户答案
				String userAnswer = examDetail.getAnswer();
				String[] userAnswerArr = new String[1];
				userAnswerArr[0] = userAnswer;
				if (userAnswer.lastIndexOf("|") != -1) {
					userAnswerArr = userAnswer.split("|");
				}
				// 对比试题答案与用户答案
				for (int i = 0; i < questionAnswerArr.length; i++) {
					if (i > userAnswerArr.length - 1) {
						examDetail.setScore(0.0);
					} else {
						String qa = questionAnswerArr[i];
						// 多个标准答案
						if (qa.lastIndexOf("&") != -1) {
							// 默认先给个错误分数
							examDetail.setScore(0.0);
							for (String value : qa.split("&")) {
								// 正确
								if (value.trim()
										.equals(userAnswerArr[i].trim())) {
									examDetail.setScore(question.getScore());
									break;
								}
							}
						}
						// 单个标准答案
						else {
							examDetail.setScore(0.0);
							if (qa.trim().equals(userAnswerArr[i])) {
								examDetail.setScore(question.getScore());
							}
						}
					}
				}
			}
			// 其他题型对答案
			else {
				// 默认先给个错误分数
				examDetail.setScore(0.0);
				String questionAnswer = question.getAnswer().trim();
				String userAnswer = examDetail.getAnswer().trim();
				// 正确
				if (questionAnswer.equals(userAnswer)) {
					examDetail.setScore(question.getScore());
				}
			}
			// 更新考试详细到数据库
			this.examDetailMapper.updateByPrimaryKeySelective(examDetail);
			// 获取该试题的所属科目
			List<Subject> qSubjects = new ArrayList<Subject>();
			QuestionSubjectExample questionSubjectExample = new QuestionSubjectExample();
			questionSubjectExample.createCriteria().andQuestionIdEqualTo(
					question.getId());
			List<QuestionSubject> questionSubjects = this.questionSubjectMapper
					.selectByExample(questionSubjectExample);
			for (QuestionSubject questionSubject : questionSubjects) {
				Subject subject = this.subjectMapper
						.selectByPrimaryKey(questionSubject.getSubjectId());
				qSubjects.add(subject);
			}
			// 试题的所属科目跟该次考试的职业的所属科目做交集
			List<Subject> retainSubject = new ArrayList<Subject>();
			for (Subject subject : subjects) {
				retainSubject.add(subject);
			}
			retainSubject.retainAll(qSubjects);
			// 记录各科目的试题得分情况
			for (Subject subject : retainSubject) {
				Map<String, Double> map = scoreMap
						.get(subject.getSubjectName());
				if (map == null) {
					map = new HashMap<String, Double>();
					map.put("userScore", examDetail.getScore());
					map.put("totalScore", question.getScore());
					scoreMap.put(subject.getSubjectName(), map);
				} else {
					Double userScore = map.get("userScore")
							+ examDetail.getScore();
					Double totalScore = map.get("totalScore")
							+ question.getScore();
					map.put("userScore", userScore);
					map.put("totalScore", totalScore);
				}
			}
		}
		// 根据各科目的得分情况，得出系统考试综合分析
		String analysis = this.getAnalysis(scoreMap);
		// 考试记录到数据库
		exam.setAnalysis(analysis);
		exam.setIsSubmit(1);
		this.examMapper.updateByPrimaryKeySelective(exam);
		return exam;
	}

	@Override
	public ExamWithBLOBs reviewExam(ExamWithBLOBs exam) throws ExamHasReviewException {
		// 检查是否已提交
		ExamWithBLOBs examTmp = this.examMapper
				.selectByPrimaryKey(exam.getId());
		if (examTmp.getIsReview() == 1) {
			throw new ExamHasReviewException("该考试已经审阅了,不能再审阅");
		}
		// 更新考试详细
		for (ExamDetailWithBLOBs examDetail : exam.getExamDetails()) {
			this.examDetailMapper.updateByPrimaryKeySelective(examDetail);
		}
		// 更新考试记录
		exam.setIsReview(0);
		this.examMapper.updateByPrimaryKeySelective(exam);
		// 获取最新状态考试记录
		exam = this.getById(exam.getId());
		return exam;
	}

	/**
	 * 随机抽取试题
	 * 
	 * @return
	 */
	private List<QuestionWithBLOBs> randomExtractQuestion() {
		List<QuestionWithBLOBs> resultQuestions = new ArrayList<QuestionWithBLOBs>();
		// 题型科目试题
		List<Map<Integer, List<QuestionWithBLOBs>>> subjectQuestion = new ArrayList<Map<Integer, List<QuestionWithBLOBs>>>();
		Integer positionId = 3;
		Integer examMinute = 60;
		ExamParam examParam = new ExamParam(examMinute);
		List<Integer> questionNum = examParam.getQuestionNum();
		// 获取职位
		Position position = this.positionMapper.selectByPrimaryKey(positionId);
		List<Subject> subjects = new ArrayList<Subject>();
		PositionSubjectExample positionSubjectExample = new PositionSubjectExample();
		positionSubjectExample.createCriteria().andPositionIdEqualTo(
				position.getId());
		List<PositionSubject> positionSubjects = this.positionSubjectMapper
				.selectByExample(positionSubjectExample);
		for (PositionSubject positionSubject : positionSubjects) {
			Subject subject = this.subjectMapper
					.selectByPrimaryKey(positionSubject.getSubjectId());
			if (subject != null) {
				subjects.add(subject);
			}
		}
		position.setSubjects(subjects);
		// 每种科目每种题型先抽取一部分
		for (int i = 0; i < questionNum.size(); i++) {
			Map<Integer, List<QuestionWithBLOBs>> map = new HashMap<Integer, List<QuestionWithBLOBs>>();
			for (Subject subject : position.getSubjects()) {
				List<QuestionWithBLOBs> questions = this.questionMapper
						.selectRandBySubjectAndType(subject.getId(), i,
								questionNum.get(i));
				map.put(subject.getId(), questions);
			}
			subjectQuestion.add(map);
		}
		// 从已抽取的试题中，抽取试题
		for (int i = 0; i < questionNum.size(); i++) { // i是题型
			for (int j = 0; j < questionNum.get(i); j++) { // 该题型的题量累计
				Map<Integer, List<QuestionWithBLOBs>> typeMap = subjectQuestion
						.get(i);
				Random random = new Random();
				Integer index = random.nextInt(subjects.size());
				Integer subjectId = subjects.get(index).getId();
				Iterator<QuestionWithBLOBs> it = typeMap.get(subjectId)
						.iterator();
				if (it.hasNext()) {
					QuestionWithBLOBs question = it.next();
					resultQuestions.add(question);
					it.remove();
				}
			}
		}
		return resultQuestions;
	}

	/**
	 * 根据各科目的得分情况，得出系统考试综合分析
	 * 
	 * @param scoreMap
	 * @return
	 */
	private String getAnalysis(Map<String, Map<String, Double>> scoreMap) {
		Set<Entry<String, Map<String, Double>>> set = scoreMap.entrySet();
		Iterator<Entry<String, Map<String, Double>>> it = set.iterator();
		String analysis = "";
		while (it.hasNext()) {
			Entry<String, Map<String, Double>> entry = it.next();
			Map<String, Double> score = entry.getValue();
			// 该考试的该科目的分析
			String subjectAnalysis = "在'" + entry.getKey() + "'方面,显得";
			// 该科目的情况:不足、欠缺、良好、优秀
			String subjectStatus = "不足";
			// 该考试该科目用户得分
			Double userScore = score.get("userScore");
			// 该考试该科目的总分
			Double totalScore = score.get("totalScore");
			// 满分-优秀
			if (userScore.compareTo(totalScore) == 0) {
				subjectStatus = "优秀";
			}
			// 大于等于一半分，小于总分 - 良好
			else if (userScore.doubleValue() * 2 >= totalScore.doubleValue()) {
				subjectStatus = "良好";
			}
			// 低于一半分 - 欠缺
			else if (userScore.doubleValue() * 2 < totalScore.doubleValue()
					&& userScore > 0.0) {
				subjectStatus = "欠缺";
			}
			// 0分
			else {
				subjectStatus = "不足";
			}
			subjectAnalysis = subjectAnalysis + subjectStatus + ".";
			analysis = analysis + subjectAnalysis;
		}
		return analysis;
	}

}
