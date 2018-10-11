package com.example.olive.travelcredit.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by minhntran on 5/16/18.
 */

public class RecordList implements Serializable{

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    private List<Record> recordList;

    public RecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

}
