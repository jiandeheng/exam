package com.lanouit.exam.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.pagehelper.PageInfo;
import com.lanouit.exam.exception.QuestionNotFoundException;
import com.lanouit.exam.po.QuestionWithBLOBs;
import com.lanouit.exam.service.QuestionService;
import com.lanouit.exam.util.Constant;
import com.lanouit.exam.validator.group.QuestionSaveGroup;
import com.lanouit.exam.validator.group.QuestionUpdateGroup;

@RestController
@RequestMapping("/api/question")
public class QuestionApi {

	@Resource
	QuestionService questionService;

	/**
	 * 添加试题
	 * 
	 * @param Question
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> saveQuestion(
			@Validated({ QuestionSaveGroup.class }) @RequestBody QuestionWithBLOBs question,
			BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		question = this.questionService.saveQuestion(question);
		result.put("statusCode", "201");
		result.put("msg", "CREATED");
		result.put("data", question);
		return new ResponseEntity<Map<String, Object>>(result,
				HttpStatus.CREATED);
	}

	/**
	 * 更新试题
	 * 
	 * @param id
	 * @param question
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateQuestion(
			@PathVariable("id") Integer id,
			@Validated({ QuestionUpdateGroup.class }) @RequestBody QuestionWithBLOBs question,
			BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		question.setId(id);
		question = this.questionService.updateQuestion(question);
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("data", question);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 删除试题
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deleteQuestion(
			@PathVariable("id") Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		QuestionWithBLOBs question = new QuestionWithBLOBs();
		question.setId(id);
		try {
			this.questionService.deleteQuestion(question);
			result.put("statusCode", "204");
			result.put("msg", "NO_CONTENT");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NO_CONTENT);
		} catch (QuestionNotFoundException e) {
			result.put("statusCode", "404");
			result.put("msg", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 分页查询试题
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listQuestion(
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = Constant.PAGE_SIZE_VALUE) Integer pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		PageInfo<QuestionWithBLOBs> questions = this.questionService.list(
				pageNum, pageSize);
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("total", questions.getTotal());
		result.put("data", questions.getList());
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 根据主键查询单个试题
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getQuestion(
			@PathVariable("id") Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		QuestionWithBLOBs question = this.questionService.getById(id);
		// 找不到科目
		if (question == null) {
			result.put("statusCode", "404");
			result.put("msg", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NOT_FOUND);
		}
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("data", question);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	/**
	 * excel批量录入试题
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/batchInsertForExcel", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> batchTest(@RequestParam("file") CommonsMultipartFile file) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> questionResult = this.questionService.batchInsertForExcel(file.getInputStream());
			result.put("data", questionResult);
		} catch (EncryptedDocumentException | InvalidFormatException
				| IOException e) {
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
}
