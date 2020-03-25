package com.nights.retarded.notes.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.nights.retarded.base.BaseController;
import com.nights.retarded.notes.model.Note;
import org.springframework.web.bind.annotation.*;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.service.NoteService;

import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/notes/note")
public class NoteController extends BaseController {

	@Resource(name="noteService")
	private NoteService noteService;

	@ApiOperation(value="查询所有")
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public String getAll() {
		return objectToJson(noteService.getAll());
	}

	@PostMapping("createNote")
    public String createNote(BigDecimal monthBudget){
        return objectToJson(noteService.createNote(getOpenId(), monthBudget));
    }

    @GetMapping("getCurrNoteId")
    public String getCurrNote(){
	    return objectToJson(noteService.getCurrNote(getOpenId()));
    }

}