package com.lanouit.exam.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.lanouit.exam.mapper.SubjectMapper;
import com.lanouit.exam.po.QuestionWithBLOBs;
import com.lanouit.exam.po.Subject;
import com.lanouit.exam.po.SubjectExample;

public class ExcelUtil {

	private SubjectMapper subjectMapper;

	private InputStream is;

	private QuestionWithBLOBs question;

	private static Map<String, Integer> typeMap = new HashMap<String, Integer>();
	static {
		typeMap.put("单选题", 0);
		typeMap.put("多选题", 1);
		typeMap.put("判断题", 2);
		typeMap.put("填空题", 3);
		typeMap.put("简答题", 4);
		typeMap.put("编程题", 5);
	}

	/**
	 * 构造方法
	 * 
	 * @param mapper
	 * @param is
	 */
	public ExcelUtil(InputStream is, SubjectMapper subjectMapper) {
		super();
		this.subjectMapper = subjectMapper;
		this.is = is;
	}

	public Map<String, Object> buildBeans()
			throws EncryptedDocumentException,
			org.apache.poi.openxml4j.exceptions.InvalidFormatException,
			IOException {
		
		Map<String, Object> result = new HashMap<String, Object>();

		List<QuestionWithBLOBs> list = new ArrayList<QuestionWithBLOBs>();
		
		List<Integer> errorRow = new ArrayList<Integer>();

		Workbook workbook = WorkbookFactory.create(is);

		Sheet sheet = workbook.getSheetAt(0);

		Row headRow = sheet.getRow(0);

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			question = new QuestionWithBLOBs();
			if(row == null){
				continue;
			}
			for (int j = 0; j < headRow.getLastCellNum(); j++) {
				Cell cell = row.getCell(j);
				if (j == 0) {
					if (!parseType(cell)) {
						errorRow.add(j);
						break;
					}
				} else if (j == 1) {
					parseSubjects(cell);
				} else if (j == 2) {
					parseScore(cell);
				} else if (j == 3) {
					parseContent(cell);
				} else if (j == 4) {
					parseQuestionOption(cell);
				} else if (j == 5) {
					parseAnswer(cell);
				} else if (j == 6) {
					parseAnalysis(cell);
				}
			}
			list.add(question);
		}
		result.put("questions", list);
		result.put("errorRow", errorRow);
		return result;
	}

	/**
	 * 解析题型
	 * 
	 * @param cell
	 * @return
	 */
	private Boolean parseType(Cell cell) {
		String value = getCellValue(cell);
		Integer type = typeMap.get(value);
		if (type == null)
			return false;
		this.question.setType(type);
		return true;
	}

	/**
	 * 解析试题科目
	 * 
	 * @param cell
	 */
	private void parseSubjects(Cell cell) {
		String value = getCellValue(cell);
		List<String> subjectsName = new ArrayList<String>();
		if (value.lastIndexOf(",") != -1) {
			String[] subjectName = value.split(",");
			for (String name : subjectName) {
				subjectsName.add(name);
			}
		} else {
			subjectsName.add(value);
		}
		SubjectExample subjectExample = new SubjectExample();
		subjectExample.createCriteria().andSubjectNameIn(subjectsName);
		List<Subject> subjects = this.subjectMapper
				.selectByExample(subjectExample);
		this.question.setSubjects(subjects);
	}

	/**
	 * 解析分数
	 * 
	 * @param cell
	 */
	private void parseScore(Cell cell) {
		String value = getCellValue(cell);
		Double score = 0.0;
		try {
			score = Double.parseDouble(value);
		} catch (NumberFormatException e) {

		}
		this.question.setScore(score);
	}

	/**
	 * 解析试题内容
	 * 
	 * @param cell
	 */
	private void parseContent(Cell cell) {
		String value = getCellValue(cell);
		this.question.setContent(value);
	}

	/**
	 * 解析选项
	 * 
	 * @param cell
	 */
	private void parseQuestionOption(Cell cell) {
		String value = getCellValue(cell);
		value = value.replaceAll(System.getProperty("line.separator"), "|");
		value = value.replaceAll("\r\n", "|");
		value = value.replaceAll("\n", "|");
		value = value.replaceAll("\r", "|");
		this.question.setQuestionOption(value);
	}

	/**
	 * 解析答案
	 * 
	 * @param cell
	 */
	private void parseAnswer(Cell cell) {
		String value = getCellValue(cell);
		this.question.setAnswer(value);
	}

	/**
	 * 解析试题分析
	 * 
	 * @param cell
	 */
	private void parseAnalysis(Cell cell) {
		String value = getCellValue(cell);
		this.question.setAnalysis(value);
	}

	/**
	 * 获取cell的String值
	 * 
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String getCellValue(Cell cell) {
		String value = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				value = String.valueOf(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_BLANK:
				value = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = cell.getBooleanCellValue() ? "1" : "0";
				break;
			default:
				value = cell.getStringCellValue();
				break;
			}
		}
		return value.trim();
	}

}
