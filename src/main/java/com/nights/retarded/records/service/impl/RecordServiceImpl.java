package com.nights.retarded.records.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.notes.model.Note;
import com.nights.retarded.notes.service.NoteService;
import com.nights.retarded.records.model.RecentRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nights.retarded.records.model.Record;
import com.nights.retarded.records.dao.RecordDao;
import com.nights.retarded.records.service.RecordService;

@Service("recordService")
public class RecordServiceImpl implements RecordService{

	@Resource(name = "recordDao")
	private RecordDao recordDao;

	@Autowired
    private NoteService noteService;

	@Override
	public List<Record> getAll() {
		return this.recordDao.findAll();
	}

    @Override
    public List<RecentRecords> getRecentRecords(String openId) {
        Note note = noteService.getCurrNote(openId);
        Date now = new Date();
        now = DateUtils.toDaySdf(now);
        Date startTime = DateUtils.addDay(now, -5);
        List<Record> list = recordDao.findByNoteIdAndDtBetweenOrderByCreateDtDesc(note.getNoteId(), now, startTime);

        List<RecentRecords> result = new ArrayList<>();
        List<Record> recordsTemp = new ArrayList<>();
        BigDecimal totalSpendingTemp = BigDecimal.valueOf(0);
        RecentRecords recentRecords = new RecentRecords();
        for(int i = 0;i < list.size() ;i++){
            Record record = list.get(i);
            if(i == 0 || record.getDt().getTime() < list.get(i-1).getDt().getTime()){
                if(i != 0){
                    result.add(recentRecords);
                } else {
                    recentRecords = new RecentRecords();
                    recordsTemp = new ArrayList<>();
                    totalSpendingTemp = BigDecimal.valueOf(0);
                }
                recentRecords.setDt(record.getDt());
                SimpleDateFormat sdf = new SimpleDateFormat("M月dd日 EEEE");
                String date = sdf.format(record.getDt());
                recentRecords.setDate(date);
            }
            recordsTemp.add(record);
            totalSpendingTemp = totalSpendingTemp.add(record.getMoney());
        }

        result.add(recentRecords);
        return result;
    }

    @Override
    public void addRecord(String recordTypeId, BigDecimal money, String description, Date dt, String openId) {
	    Record record = new Record();
	    record.setCreateDt(new Date());
	    record.setDescription(description);
        record.setDt(dt);
        record.setMoney(money);
        record.setTypeId(recordTypeId);
        Note note = noteService.getCurrNote(openId);
        record.setNoteId(note.getNoteId());
        recordDao.save(record);

        // 计算统计数据 TODO

    }

}