package com.nights.retarded.notes.service;

import com.nights.retarded.notes.model.CrazyNotes;

public interface CrazyNotesService {

	/**
	 * 新建疯狂账本
	 */
	public CrazyNotes addCrazyNotes(String openId,double dailyLimit ,int settleDate) throws Exception;

	/**
	 * 获取疯狂账本信息
	 * @param notesId
	 */
	public CrazyNotes getCrazyNoteInfo(String notesId);

	/**
	 * 修改疯狂账本
	 * @param crazyNotesJson
	 */
	public void editCrazyNote(String crazyNotesJson);

	/**
	 * 删除疯狂账本
	 * @param crazyNotesId
	 */
	public void del(String crazyNotesId);

}