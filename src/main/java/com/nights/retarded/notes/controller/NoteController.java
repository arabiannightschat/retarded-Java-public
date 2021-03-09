package com.nights.retarded.notes.controller;

import com.nights.retarded.base.baseController.BaseController;
import com.nights.retarded.base.baseController.Result;
import com.nights.retarded.notes.service.NoteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("api/notes/note")
public class NoteController extends BaseController {

	@Resource(name="noteService")
	private NoteService noteService;

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public Result getAll() {
		return Success(noteService.getAll());
	}

	@PostMapping("createNote")
    public Result createNote(BigDecimal monthBudget){
        return Success(noteService.createNote(getOpenId(), monthBudget));
    }

    @GetMapping("getCurrNote")
    public Result getCurrNote(){
	    return Success(noteService.getCurrNote(getOpenId()));
    }

    /**
     * 解冻账本
     * @return
     */
    @PostMapping("unfreeze")
    public Result unfreeze(){
	    noteService.unfreeze(noteService.getCurrNoteContainFreeze(getOpenId()));
	    return Success();
    }

}