package com.nights.retarded.records.model;

public enum RecordsTypeEnum {

    ELSE("8a44deb8d83111e89d4100163e02ppoo"),
    SETTLE("8a44deb8d83111e89d4100163e02uuuu");

    String id;

    RecordsTypeEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
