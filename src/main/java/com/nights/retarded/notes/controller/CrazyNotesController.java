package com.nights.retarded.notes.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.model.CrazyNotes;
import com.nights.retarded.notes.service.CrazyNotesService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/crazyNotes")
public class CrazyNotesController {

	@Resource(name="crazyNotesService")
	private CrazyNotesService crazyNotesService;
	
	@ApiOperation(value="新建一个疯狂账本")
	@RequestMapping(value = "addCrazyNote", method = RequestMethod.GET)
	public String addCrazyNote(HttpServletRequest request, double dailyLimit ,int settleDate) throws Exception {
		String openId = JsonUtils.requestToOpenId(request);
		CrazyNotes notes = crazyNotesService.addCrazyNotes(openId, dailyLimit, settleDate);
		return JsonUtils.objectToJson(notes);
	}
	
	@ApiOperation(value="修改疯狂账本")
	@RequestMapping(value = "editCrazyNote", method = RequestMethod.GET)
	public String editCrazyNote(String crazyNotesJson) throws Exception {
		crazyNotesService.editCrazyNote(crazyNotesJson);
		return "ok";
	}
	
	@ApiOperation(value="删除疯狂账本")
	@RequestMapping(value = "del", method = RequestMethod.GET)
	public String del(String crazyNotesId) throws Exception {
		crazyNotesService.del(crazyNotesId);
		return "ok";
	}
	
	@ApiOperation(value="获取疯狂账本信息")
	@RequestMapping(value = "getCrazyNoteInfo", method = RequestMethod.GET)
	public String getCrazyNoteInfo(String notesId) {
		return JsonUtils.objectToJson(crazyNotesService.getCrazyNoteInfo(notesId));
	}
}