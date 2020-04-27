package com.nights.retarded.base;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.model.Note;
import com.nights.retarded.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    private NoteService noteService;

    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 203;
    private static final int UNKNOWN_ERROR_CODE = 204;

    public static Map Success(String data){
        return returnState(SUCCESS_CODE,"success",data);
    }

    public static Map Success(){
        return returnState(SUCCESS_CODE,"success", null);
    }

    public static Map Success(Object obj){
        return returnState(SUCCESS_CODE,"success",obj);
    }

    public static Map returnState(int stateCode, String message, Object obj) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", stateCode);
        map.put("message", message);
        if(obj != null){
            map.put("data", obj);
        }
        return map;
    }

    public static Map Error(String message){
        return returnState(ERROR_CODE, message, null);
    }

    public static Map UnknownError(String message) {
        return returnState(UNKNOWN_ERROR_CODE, message, null);
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

    public String objectToJson(Object obj){
        return JsonUtils.objectToJson(obj);
    }
}
