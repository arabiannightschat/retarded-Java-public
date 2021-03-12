package com.nights.retarded.notes.model.vo.recentData;

import com.nights.retarded.notes.model.entity.Note;
import com.nights.retarded.records.model.vo.RecentRecords;
import lombok.Data;

import java.util.List;

@Data
public class RecentData {
    private Note note;
    private SimpleData simpleData;
    private ChartsData chartsData;
    private List<RecentRecords> recentRecords;
}
