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
import com.lanouit.exam.exception.PositionExistException;
import com.lanouit.exam.exception.PositionNotFoundException;
import com.lanouit.exam.po.Position;
import com.lanouit.exam.service.PositionService;
import com.lanouit.exam.util.Constant;
import com.lanouit.exam.validator.group.PositionSaveGroup;
import com.lanouit.exam.validator.group.PositionUpdateGroup;

@RestController
@RequestMapping("/api/position")
public class PositionApi {

	@Resource
	PositionService positionService;

	/**
	 * 添加职业
	 * 
	 * @param position
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> savePosition(
			@Validated({ PositionSaveGroup.class }) @RequestBody Position position,
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
			position = this.positionService.savePosition(position);
			result.put("statusCode", "201");
			result.put("msg", "CREATED");
			result.put("data", position);
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.CREATED);
		} catch (PositionExistException e) {
			result.put("statusCode", Constant.POSITION_EXIST);
			result.put("msg", "职业已存在");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	/**
	 * 更新职业
	 * 
	 * @param position
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updatePosition(
			@PathVariable("id") Integer id,
			@Validated({ PositionUpdateGroup.class }) @RequestBody Position position,
			BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据验证不通过
		if (bindingResult.hasErrors()) {
			result.put("statusCode", "422");
			result.put("msg", "fields validation errors");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		position.setId(id);
		try {
			position = this.positionService.updatePosition(position);
			result.put("statusCode", "200");
			result.put("msg", "OK");
			result.put("data", position);
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.OK);
		} catch (PositionExistException e) {
			result.put("statusCode", Constant.POSITION_EXIST);
			result.put("msg", "职业已存在");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	/**
	 * 删除职位
	 * 
	 * @param position
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deletePosition(
			@PathVariable("id") Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Position position = new Position();
		position.setId(id);
		try {
			this.positionService.deletePosition(position);
			result.put("statusCode", "204");
			result.put("msg", "NO_CONTENT");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NO_CONTENT);
		} catch (PositionNotFoundException e) {
			result.put("statusCode", "404");
			result.put("msg", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listPosition(
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = Constant.PAGE_SIZE_VALUE) Integer pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		PageInfo<Position> positions = this.positionService.list(pageNum,
				pageSize);
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("total", positions.getTotal());
		result.put("data", positions.getList());
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 根据主键查询职业
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getPosition(
			@PathVariable("id") Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Position position = this.positionService.getById(id);
		// 找不到职业
		if (position == null) {
			result.put("statusCode", "404");
			result.put("msg", "NOT_FOUND");
			return new ResponseEntity<Map<String, Object>>(result,
					HttpStatus.NOT_FOUND);
		}
		result.put("statusCode", "200");
		result.put("msg", "OK");
		result.put("data", position);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

}
