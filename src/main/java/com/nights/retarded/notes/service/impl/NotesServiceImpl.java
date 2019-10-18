package com.nights.retarded.notes.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.dao.CrazyNotesDao;
import com.nights.retarded.notes.dao.NotesDao;
import com.nights.retarded.notes.model.CrazyNotes;
import com.nights.retarded.notes.model.Notes;
import com.nights.retarded.notes.service.NotesService;

@Service("notesService")
public class NotesServiceImpl implements NotesService{
	
	@Resource(name = "notesDao")
	private NotesDao notesDao;
	
	@Resource(name = "crazyNotesDao")
	private CrazyNotesDao crazyNotesDao;

	@Override
	public String getNotes(String openId) {
		List<Notes> dieds = notesDao.findAllByOpenIdAndState(openId,0);
		List<Notes> notes = notesDao.findAllByOpenIdAndState(openId,1);
		List<CrazyNotes> diedsCrazy = crazyNotesDao.findAllByOpenIdAndState(openId,0);
		List<CrazyNotes> notesCrazy = crazyNotesDao.findAllByOpenIdAndState(openId,1);
		Map<String,Object> map = new HashMap<>();
		map.put("dieds", dieds);
		map.put("notes", notes);
		map.put("diedsCrazy", diedsCrazy);
		map.put("notesCrazy", notesCrazy);
		return JsonUtils.mapToJson(map);
	}

}
