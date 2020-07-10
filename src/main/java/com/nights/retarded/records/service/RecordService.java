package com.nights.retarded.records.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nights.retarded.notes.model.Note;
import com.nights.retarded.records.model.RecentRecords;
import com.nights.retarded.records.model.Record;

public interface RecordService {

	List<Record> getAll();

    List<RecentRecords> getRecentRecords(Note note);

    Map recordsLoading(int recordsLoadingCount, String currNoteId);

    void addRecord(String recordTypeId, BigDecimal money, String description, Date dt, String openId);

    void delRecord(String recordId);

    BigDecimal getDynamicDayBudget(Date dt, BigDecimal balance);

    BigDecimal getDynamicDayBudgetTask(Date date, BigDecimal balance);

    int countByNoteIdAndDt(String noteId, Date yesterday);

}