package com.nights.retarded.base.baseController;

import com.nights.retarded.utils.JsonUtils;
import com.nights.retarded.notes.model.entity.Note;
import com.nights.retarded.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    private NoteService noteService;

    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 203;
    private static final int UNKNOWN_ERROR_CODE = 204;

    private static Result buildResult(int code, String message, Object data){
        return new Result(code, message, data);
    }

    public static Result Success(){
        return buildResult(SUCCESS_CODE,"success", null);
    }

    public static Result Success(Object data){
        return buildResult(SUCCESS_CODE,"success",data);
    }

    public static Result Error(String message){
        return buildResult(ERROR_CODE, message, null);
    }

    public static Result UnknownError(String message) {
        return buildResult(UNKNOWN_ERROR_CODE, message, null);
    }

    public String getOpenId(){
        return JsonUtils.requestToOpenId(request);
    }

    public String getCurrNoteId(){
        Note note = noteService.getCurrNote(getOpenId());
        if(note != null) {
            return note.getNoteId();
        } else {
            return null;
        }
    }

}
