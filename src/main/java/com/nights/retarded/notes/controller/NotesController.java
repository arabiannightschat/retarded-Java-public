package com.nights.retarded.notes.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.service.NotesService;

import io.swagger.annotations.ApiOperation;

//若返回JSON，需要加ResponseBody注解
//使用RestController代替Controller和ResponseBody,需要引入spring-boot-starter-web模块
@RestController
@RequestMapping("api/notes")
public class NotesController {
	
	@Resource(name="notesService")
	private NotesService notesService;
	
	@ApiOperation(value="获取用户全部账本")
	@RequestMapping(value = "getNotes", method = RequestMethod.GET)
	public String getNotes(HttpServletRequest request) {
		String openId = JsonUtils.requestToOpenId(request);
		return notesService.getNotes(openId);
	}
	
}
