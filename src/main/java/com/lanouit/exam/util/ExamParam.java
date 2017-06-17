package com.lanouit.exam.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamParam {

	/**
	 * 总试题数量
	 */
	private Integer total;

	/**
	 * 每种题型的试题数目
	 */
	private List<Integer> questionNum;

	/**
	 * 考试时间，分钟为单位
	 */
	private Integer examMinute;

	private Map<Integer, List<Integer>> numMap = new HashMap<Integer, List<Integer>>();

	public ExamParam(Integer examMinute) {
		init();
		this.examMinute = examMinute;
		this.questionNum = this.numMap.get(this.examMinute);
		this.total = 0;
		for(Integer num : questionNum) {
			total = total + num;
		}
	}
	
	private void init(){
		// 60分钟试题数目
		List<Integer> numList = new ArrayList<Integer>();
		numList.add(5);// 单选
		numList.add(5);// 多选
		numList.add(4);// 判断
		numList.add(3);// 填空
		numList.add(1);// 简答
		numList.add(1);// 编程
		numMap.put(60, numList);
		// 90分钟试题数目
		numList = new ArrayList<Integer>();
		numList.add(8);// 单选
		numList.add(8);// 多选
		numList.add(5);// 判断
		numList.add(4);// 填空
		numList.add(1);// 简答
		numList.add(1);// 编程
		numMap.put(90, numList);
		// 120分钟试题数目
		numList = new ArrayList<Integer>();
		numList.add(10);// 单选
		numList.add(10);// 多选
		numList.add(5);// 判断
		numList.add(5);// 填空
		numList.add(2);// 简答
		numList.add(2);// 编程
		numMap.put(120, numList);
	}

	public Integer getTotal() {
		return total;
	}

	public List<Integer> getQuestionNum() {
		return questionNum;
	}
	
	

}
