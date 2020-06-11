package com.guyue.pms.bean.db;

public class Prodocument {
    private Long mid;
    private Long cid;
    private String mname;
    private String path;
    private Integer ftype;
    private String uodatetime;
    private String fabout;
    private String fsize;
    private String remark;
    private String remark1;

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }

    public String getUodatetime() {
        return uodatetime;
    }

    public void setUodatetime(String uodatetime) {
        this.uodatetime = uodatetime;
    }

    public String getFabout() {
        return fabout;
    }

    public void setFabout(String fabout) {
        this.fabout = fabout;
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
