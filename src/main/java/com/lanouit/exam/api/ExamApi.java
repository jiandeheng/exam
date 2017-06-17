package com.lanouit.exam.api;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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

import com.github.pagehelper.PageInfo;
import com.lanouit.exam.exception.ExamHasReviewException;
import com.lanouit.exam.exception.ExamHasSubmitException;
import com.lanouit.exam.exception.ExamNotFoundException;
import com.lanouit.exam.po.ExamDetailWithBLOBs;
import com.lanouit.exam.po.ExamWithBLOBs;
import com.lanouit.exam.po.User;
import com.lanouit.exam.service.ExamService;
import com.lanouit.exam.util.Constant;
import com.lanouit.exam.validator.group.ExamDetailUpdateGroup;
import com.lanouit.exam.validator.group.ExamReviewGroup;
import com.lanouit.exam.validator.group.ExamSaveGroup;
import com.lanouit.exam.validator.group.ExamSubmitGroup;
import com.lanouit.exam.validator.group.ExamUpdateGroup;

@RestController
@RequestMapping("/api/exam")
public class ExamApi {

	@Resource
	ExamService examService;

	/**
	 * 模拟考试 (添加考试记录)
	 * 
	 * @param exam
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> saveExam(
			@Validated({ ExamSaveGroup.class }) @RequestBody ExamWithBLOBs exam,
			BindingResult bindingResult, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		User user = (User) session.getAttribute("user");
		exam.setUserId(user.getId());
		exam = this.examService.saveExam(exam);
		result.put("statusCode", "201");
		result.put("msg", "CREATED");
		result.put("data", exam);
		return new ResponseEntity<Map<String, Object>>(result,
				HttpStatus.CREATED);
	}

	/**
	 * 更新考试记录
	 * 
	 * @param id
	 * @param exam
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Map<String, Object>> updateExam(
			@PathVariable("id") Integer id,
			@Validated({ ExamUpdateGroup.class }) @RequestBody ExamWithBLOBs exam,
			BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		exam.setId(id);
		exam = this.examService.updateExam(exam);
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("data", exam);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 删除考试记录
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deleteExam(
			@PathVariable("id") Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		ExamWithBLOBs exam = new ExamWithBLOBs();
		exam.setId(id);
		try {
			this.examService.deleteExam(exam);
			result.put("statusCode", "204");
			result.put("msg", "NO_CONTENT");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NO_CONTENT);
		} catch (ExamNotFoundException e) {
			result.put("statusCode", "404");
			result.put("msg", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 根据主键查询考试记录
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getExamById(
			@PathVariable("id") Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		ExamWithBLOBs exam = this.examService.getById(id);
		// 找不到考试记录
		if (exam == null) {
			result.put("statusCode", "404");
			result.put("msg", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NOT_FOUND);
		}
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("data", exam);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 分页查询考试记录
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listExam(
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = Constant.PAGE_SIZE_VALUE) Integer pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		PageInfo<ExamWithBLOBs> exams = this.examService
				.list(pageNum, pageSize);
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("total", exams.getTotal());
		result.put("data", exams.getList());
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 更新考试详细
	 * 
	 * @param id
	 * @param examDetail
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/examDetail/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Map<String, Object>> updateExamDetail(
			@PathVariable("id") Integer id,
			@Validated({ ExamDetailUpdateGroup.class }) @RequestBody ExamDetailWithBLOBs examDetail,
			BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		examDetail.setId(id);
		examDetail = this.examService.updateExamDetail(examDetail);
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("data", examDetail);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 根据主键获取考试详细
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/examDetail/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getExamDetail(
			@PathVariable("id") Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		ExamDetailWithBLOBs examDetail = new ExamDetailWithBLOBs();
		examDetail.setId(id);
		examDetail = this.examService.getExamDetailById(id);
		// 找不到考试详细
		if (examDetail == null) {
			result.put("statusCode", "404");
			result.put("msg", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NOT_FOUND);
		}
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("data", examDetail);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 考试记录提交
	 * 
	 * @param id
	 * @param exam
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/{id}/submit", method = RequestMethod.PATCH)
	public ResponseEntity<Map<String, Object>> examSubmit(
			@PathVariable("id") Integer id,
			@Validated({ ExamSubmitGroup.class }) @RequestBody ExamWithBLOBs exam,
			BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		// 提交考试
		exam.setId(id);
		try {
			exam = this.examService.submitExam(exam);
			result.put("statusCode", "200");
			result.put("msg", "OK");
			result.put("data", exam);
		} catch (ExamHasSubmitException e) {
			result.put("statusCode", "40001");
			result.put("msg", "考试已提交,不能再提交");
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 考试人工评审
	 */
	@RequestMapping(value = "/{id}/review", method = RequestMethod.PATCH)
	public ResponseEntity<Map<String, Object>> examReview(
			@PathVariable("id") Integer id,
			@Validated({ ExamReviewGroup.class }) @RequestBody ExamWithBLOBs exam,
			BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		exam.setId(id);
		try {
			exam = this.examService.reviewExam(exam);
			result.put("statusCode", "200");
			result.put("msg", "OK");
			result.put("data", exam);
		} catch (ExamHasReviewException e) {
			result.put("statusCode", "40002");
			result.put("msg", "考试已审阅,不能再审阅");
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

}
