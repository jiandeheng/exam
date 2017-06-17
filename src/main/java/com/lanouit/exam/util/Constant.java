package com.lanouit.exam.util;

public class Constant {

	public static final Integer PAGE_SIZE = 10;
	
	public static final String PAGE_SIZE_VALUE = "10";
	
	/**
	 * 用户邮箱已存在
	 */
	public static final String USERMAIL_EXIST = "10001";
	
	/**
	 * 用户添加异常
	 */
	public static final String USER_INSERT_ERROR = "10002";
	
	/**
	 * 发送用户注册激活邮件异常
	 */
	public static final String USER_SEND_REGISTER_EMAIL_ERROR = "10003";
	
	/**
	 * 用户登录账号密码不匹配,登录失败
	 */
	public static final String USER_LOGIN_FAIL = "10004";
	
	/**
	 * 用户更换头像异常
	 */
	public static final String USER_UPDATE_USERPIC_ERROR = "10005";
	
	/**
	 * 职业已存在
	 */
	public static final String POSITION_EXIST = "20001";
	
	/**
	 * 科目已存在
	 */
	public static final String SUBJECT_EXIST = "30001";
	
	/**
	 * 考试已提交,不能再提交
	 */
	public static final String EXAM_HAS_SUBMIT = "40001";
	
	/**
	 * 考试已审阅，不能再审阅
	 */
	public static final String EXAM_HAS_REVIEW = "40002";
	
	
	
	
	/**
	 * 单选题
	 */
	public static final Integer SINGLE_ANSWER = 0;
	
	/**
	 * 多选题
	 */
	public static final Integer MULTIPLE_CHOICE_ANSWERS = 1;
	
	/**
	 * 判断题
	 */
	public static final Integer JUDGE_ANSWER = 2;
	
	/**
	 * 填空题
	 */
	public static final Integer FILL_IN_THE_BLANK_ANSWERS = 3;
	
	/**
	 * 简答题
	 */
	public static final Integer QUESTIONS_AND_ANSWERS = 4;
	
	/**
	 * 单选题
	 */
	public static final Integer PROGRAMMING_ANSWERS = 5;
	
	
	
	
}
