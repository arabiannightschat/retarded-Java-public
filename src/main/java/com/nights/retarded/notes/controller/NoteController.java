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
import java.util.Map;

@RestController
@RequestMapping("api/notes/note")
public class NoteController extends BaseController {

	@Resource(name="noteService")
	private NoteService noteService;

	@ApiOperation(value="查询所有")
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public Map getAll() {
		return Success(noteService.getAll());
	}

	@PostMapping("createNote")
    public Map createNote(BigDecimal monthBudget){
        return Success(noteService.createNote(getOpenId(), monthBudget));
    }

    @GetMapping("getCurrNote")
    public Map getCurrNote(){
	    return Success(noteService.getCurrNote(getOpenId()));
    }

    @PostMapping("unfreeze")
    public Map unfreeze(){
	    noteService.unfreeze(getCurrNoteId());
	    return Success();
    }
}