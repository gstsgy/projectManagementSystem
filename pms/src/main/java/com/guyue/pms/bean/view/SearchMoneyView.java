package com.guyue.pms.bean.view;

public class SearchMoneyView {
    private Integer pageNum;//
    private Integer pageSize;//
    private Integer total;//
    private Integer isfinish;
    private Integer isadvancecharge;
    private Integer isbilling;
    private String  proname;
    private String compname;
    public String getProname() {
        return proname;
    }

    public String getCompname() {
        return compname;
    }

    public void setCompname(String compname) {
        this.compname = compname;
    }

    public void setProname(String proname) {
        this.proname = proname;
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

    public Integer getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(Integer isfinish) {
        this.isfinish = isfinish;
    }

    public Integer getIsadvancecharge() {
        return isadvancecharge;
    }

    public void setIsadvancecharge(Integer isadvancecharge) {
        this.isadvancecharge = isadvancecharge;
    }

    public Integer getIsbilling() {
        return isbilling;
    }

    public void setIsbilling(Integer isbilling) {
        this.isbilling = isbilling;
    }

    @Override
    public String toString() {
        return "SearchMoneyView{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", isfinish=" + isfinish +
                ", isadvancecharge=" + isadvancecharge +
                ", isbilling=" + isbilling +
                '}';
    }
}
