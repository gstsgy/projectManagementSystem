package com.guyue.pms.bean.db;

import java.util.List;

public class InsertModel {
    // 表名，字段名
    private String tableName;

    private List<String> fields;

    private List<List<String>> values;

    public InsertModel(String tableName, List<String> fields, List values) {
        this.tableName = tableName;
        this.fields = fields;
        this.values = values;
    }

    public InsertModel() {
    }

    @Override
    public String toString() {
        return "InertModel{" +
                "tableName='" + tableName + '\'' +
                ", fields=" + fields +
                ", values=" + values +
                '}';
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List getValues() {
        return values;
    }

    public void setValues(List values) {
        this.values = values;
    }


}
