package com.guyue.pms.bean.view;

public class ExportDataView {
    private String compname;
    private String proname;
    private String contractno;
    private String contact;
    private String ptype;//主类型
    private String subtype;// 次类型
    private String mainpeople;
    private String preparedby;
    private Integer isfinish;
    private String isfinishName;
    private Integer schedule;
    private String remark;

    public String getCompname() {
        return compname;
    }

    public void setCompname(String compname) {
        this.compname = compname;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getMainpeople() {
        return mainpeople;
    }

    public void setMainpeople(String mainpeople) {
        this.mainpeople = mainpeople;
    }

    public String getPreparedby() {
        return preparedby;
    }

    public void setPreparedby(String preparedby) {
        this.preparedby = preparedby;
    }

    public Integer getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(Integer isfinish) {
        this.isfinish = isfinish;
        this.isfinishName = isfinish ==0?"未完成":"已完成";
    }

    public String getIsfinishName() {
        return isfinishName;
    }

   /* public void setIsfinishName(Integer isfinishName) {
        this.isfinishName = isfinishName;
    }*/

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
