package com.lanouit.exam.api;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

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
import com.lanouit.exam.exception.SubjectExistException;
import com.lanouit.exam.exception.SubjectNotFoundException;
import com.lanouit.exam.po.Subject;
import com.lanouit.exam.service.SubjectService;
import com.lanouit.exam.util.Constant;
import com.lanouit.exam.validator.group.SubjectSaveGroup;
import com.lanouit.exam.validator.group.SubjectUpdateGroup;

@RestController
@RequestMapping("/api/subject")
public class SubjectApi {

	@Resource
	SubjectService subjectService;

	/**
	 * 添加科目
	 * 
	 * @param Subject
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> saveSubject(
			@Validated({ SubjectSaveGroup.class }) @RequestBody Subject Subject,
			BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			Subject = this.subjectService.saveSubject(Subject);
			result.put("statusCode", "201");
			result.put("msg", "CREATED");
			result.put("data", Subject);
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.CREATED);
		} catch (SubjectExistException e) {
			result.put("statusCode", Constant.SUBJECT_EXIST);
			result.put("msg", "科目已存在");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	/**
	 * 更新科目
	 * 
	 * @param id
	 * @param subject
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateSubject(
			@PathVariable("id") Integer id,
			@Validated({ SubjectUpdateGroup.class }) @RequestBody Subject subject,
			BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		subject.setId(id);
		try {
			subject = this.subjectService.updateSubject(subject);
			result.put("statusCode", "200");
			result.put("msg", "OK");
			result.put("data", subject);
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.OK);
		} catch (SubjectExistException e) {
			result.put("statusCode", Constant.SUBJECT_EXIST);
			result.put("msg", "科目已存在");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	/**
	 * 删除科目
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deleteSubject(
			@PathVariable("id") Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Subject subject = new Subject();
		subject.setId(id);
		try {
			this.subjectService.deleteSubject(subject);
			result.put("statusCode", "204");
			result.put("msg", "NO_CONTENT");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NO_CONTENT);
		} catch (SubjectNotFoundException e) {
			result.put("statusCode", "404");
			result.put("msg", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 分页查询科目
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listSubject(
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = Constant.PAGE_SIZE_VALUE) Integer pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		PageInfo<Subject> subjects = this.subjectService
				.list(pageNum, pageSize);
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("total", subjects.getTotal());
		result.put("data", subjects.getList());
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 根据主键查询单个科目
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getSubject(
			@PathVariable("id") Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Subject subject = this.subjectService.getById(id);
		// 找不到科目
		if (subject == null) {
			result.put("statusCode", "404");
			result.put("msg", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NOT_FOUND);
		}
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("data", subject);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

}
