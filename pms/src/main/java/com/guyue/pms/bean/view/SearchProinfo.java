package com.guyue.pms.bean.view;

import com.guyue.pms.bean.db.Proinfo;

public class SearchProinfo extends Proinfo {
    private String registerdateE;//登记日期
    private String registerdateS;//登记日期
    private Integer pageNum;//
    private Integer pageSize;//
    private Integer total;//
    private String username;

    public String getRegisterdateE() {
        return registerdateE;
    }

    public void setRegisterdateE(String registerdateE) {
        this.registerdateE = registerdateE;
    }

    public String getRegisterdateS() {
        return registerdateS;
    }

    public void setRegisterdateS(String registerdateS) {
        this.registerdateS = registerdateS;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "SearchProinfo{" +
                "registerdateE='" + registerdateE + '\'' +
                ", registerdateS='" + registerdateS + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                '}';
    }
}
