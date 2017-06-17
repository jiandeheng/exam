package com.lanouit.exam.po;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.lanouit.exam.validator.group.QuestionSaveGroup;
import com.lanouit.exam.validator.group.QuestionUpdateGroup;

public class Question {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column question.id
	 *
	 * @mbggenerated Mon Jun 05 10:56:44 CST 2017
	 */
	private Integer id;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column question.type
	 *
	 * @mbggenerated Mon Jun 05 10:56:44 CST 2017
	 */
	@NotNull(message = "试题类型不能为空", groups = { QuestionSaveGroup.class })
	@Null(message = "试题类型不能更改", groups = { QuestionUpdateGroup.class })
	private Integer type;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column question.score
	 *
	 * @mbggenerated Mon Jun 05 10:56:44 CST 2017
	 */
	@NotNull(message = "试题分数不能为空", groups = { QuestionSaveGroup.class,
			QuestionUpdateGroup.class })
	private Double score;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column question.create_time
	 *
	 * @mbggenerated Mon Jun 05 10:56:44 CST 2017
	 */
	private Date createTime;

	/**
	 * 所属科目
	 */
	private List<Subject> subjects;

	/**
	 * 选项格式 key-选项ABCD... value-选项内容
	 */
	@NotNull(message = "试题选项不能为空", groups = { QuestionSaveGroup.class,
			QuestionUpdateGroup.class })
	private List<Map<String, String>> options;

	/**
	 * 答案格式
	 */
	@NotNull(message = "试题答案不能为空", groups = { QuestionSaveGroup.class,
			QuestionUpdateGroup.class })
	private List<String> answers;

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<Map<String, String>> getOptions() {
		return options;
	}

	public void setOptions(List<Map<String, String>> options) {
		this.options = options;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column question.id
	 *
	 * @return the value of question.id
	 *
	 * @mbggenerated Mon Jun 05 10:56:44 CST 2017
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column question.id
	 *
	 * @param id
	 *            the value for question.id
	 *
	 * @mbggenerated Mon Jun 05 10:56:44 CST 2017
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column question.type
	 *
	 * @return the value of question.type
	 *
	 * @mbggenerated Mon Jun 05 10:56:44 CST 2017
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column question.type
	 *
	 * @param type
	 *            the value for question.type
	 *
	 * @mbggenerated Mon Jun 05 10:56:44 CST 2017
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column question.score
	 *
	 * @return the value of question.score
	 *
	 * @mbggenerated Mon Jun 05 10:56:44 CST 2017
	 */
	public Double getScore() {
		return score;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column question.score
	 *
	 * @param score
	 *            the value for question.score
	 *
	 * @mbggenerated Mon Jun 05 10:56:44 CST 2017
	 */
	public void setScore(Double score) {
		this.score = score;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column question.create_time
	 *
	 * @return the value of question.create_time
	 *
	 * @mbggenerated Mon Jun 05 10:56:44 CST 2017
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column question.create_time
	 *
	 * @param createTime
	 *            the value for question.create_time
	 *
	 * @mbggenerated Mon Jun 05 10:56:44 CST 2017
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", type=" + type + ", score=" + score
				+ ", createTime=" + createTime + ", subjects=" + subjects
				+ ", options=" + options + ", answers=" + answers + "]";
	}

}