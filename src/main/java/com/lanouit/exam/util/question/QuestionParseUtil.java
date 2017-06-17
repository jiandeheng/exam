package com.lanouit.exam.util.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 试题解析工具类
 *
 */
public class QuestionParseUtil {

	/**
	 * 解析自定义选项结构的options，生成数据库存储的结构的option
	 * 
	 * @param options
	 * @return
	 */
	public static String parseOptions(List<Map<String, String>> options) {
		StringBuffer stringBuffer = new StringBuffer();
		for (Map<String, String> optionMap : options) {
			String index = optionMap.get("index").trim();
			String value = optionMap.get("value").trim();
			stringBuffer.append(index + "." + value + "|");
		}
		if (stringBuffer.length() > 0) {
			stringBuffer = stringBuffer.deleteCharAt(stringBuffer
					.lastIndexOf("|"));
		}
		String option = stringBuffer.toString();
		return option;
	}

	/**
	 * 解析自定义选项结构的answers，生成数据库存储的结构的answer
	 * 
	 * @param answers
	 * @return
	 */
	public static String parseAnswers(List<String> answers) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String answerValue : answers) {
			stringBuffer.append(answerValue + ",");
		}
		if (stringBuffer.length() > 0) {
			stringBuffer = stringBuffer.deleteCharAt(stringBuffer
					.lastIndexOf(","));
		}
		String answer = stringBuffer.toString();
		return answer;
	}

	/**
	 * 解析数据库结构的option，生成自定义结构的options
	 * 
	 * @param option
	 * @return
	 */
	public static List<Map<String, String>> parseOption(String option) {
		List<Map<String, String>> options = new ArrayList<Map<String, String>>();
		if (option.length() > 0) {
			// 多个选项
			if (option.lastIndexOf("|") != -1) {
				String[] optionsArr = option.split("\\|");
				System.out.println(Arrays.toString(optionsArr));
				for (String str : optionsArr) {
					if (str.lastIndexOf(".") != -1) {
						String[] optionArr = str.split("\\.");
						System.out.println(Arrays.toString(optionArr));
						Map<String, String> map = new HashMap<String, String>();
						map.put("index", optionArr[0]);
						map.put("value", optionArr[1]);
						options.add(map);
					}
				}
			}
			// 一个选项
			else {
				if (option.lastIndexOf(".") != -1) {
					String[] optionArr = option.split("\\.");
					Map<String, String> map = new HashMap<String, String>();
					map.put("index", optionArr[0]);
					map.put("value", optionArr[1]);
					options.add(map);
				}
			}
		}
		return options;
	}

	/**
	 * 解析数据库结构的answer，生成自定义结构的answers
	 * 
	 * @param answer
	 * @return
	 */
	public static List<String> parseAnswer(String answer) {
		List<String> answers = new ArrayList<String>();
		if (answer.length() > 0) {
			// 多个答案
			if (answer.lastIndexOf(",") != -1) {
				String[] answersArr = answer.split(",");
				for (String value : answersArr) {
					answers.add(value);
				}
			}
			// 一个答案
			else {
				answers.add(answer);
			}
		}
		return answers;
	}

}
