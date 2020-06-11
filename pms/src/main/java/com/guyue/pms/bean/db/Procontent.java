package com.guyue.pms.bean.db;

import com.guyue.pms.bean.view.ProcontentView;

import java.util.Objects;

public class Procontent {
    private Long infoid;//项目idpmenu
    private Long id;//内容id
    private Long ptypeid;//主类型
    private Long subid;//次类型
    private Integer isarrange;//是否安排
    private String mainpeople;//负责人
    private String preparedby;//编制人
    private Integer newlyflag;//新增标志
    private Integer isfinish;//完成标志
    private Integer schedule;//进度
    private String remark;//备注
    private String remark1;//备用1
    private String remark2;//备用2
    private String remark3;//备用3

    public Procontent() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Procontent that = (Procontent) o;
        return Objects.equals(infoid, that.infoid) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(infoid, id);
    }

    public Procontent(ProcontentView procontentView) {
        this.infoid=procontentView.getInfoid();
        this.id=procontentView.getId();
        this. ptypeid=procontentView.getPtypeid();
        this.subid=procontentView.getSubid();
        this.isarrange=procontentView.getIsarrange();
        this.newlyflag=procontentView.getNewlyflag();
        this.isfinish=procontentView.getIsfinish();
        this.schedule=procontentView.getSchedule();
        this.mainpeople=procontentView.getMainpeople();
        this.preparedby=procontentView.getPreparedby();
        this.remark=procontentView.getRemark();
    }

    public Long getInfoid() {
        return infoid;
    }

    public void setInfoid(Long infoid) {
        this.infoid = infoid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPtypeid() {
        return ptypeid;
    }

    public void setPtypeid(Long ptypeid) {
        this.ptypeid = ptypeid;
    }

    public Long getSubid() {
        return subid;
    }

    public void setSubid(Long subid) {
        this.subid = subid;
    }

    public Integer getIsarrange() {
        return isarrange;
    }

    public void setIsarrange(Integer isarrange) {
        this.isarrange = isarrange;
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

    public Integer getNewlyflag() {
        return newlyflag;
    }

    public void setNewlyflag(Integer newlyflag) {
        this.newlyflag = newlyflag;
    }

    public Integer getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(Integer isfinish) {
        this.isfinish = isfinish;
    }

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

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    @Override
    public String toString() {
        return "Procontent{" +
                "infoid=" + infoid +
                ", id=" + id +
                ", ptypeid=" + ptypeid +
                ", subid=" + subid +
                ", isarrange=" + isarrange +
                ", mainpeople='" + mainpeople + '\'' +
                ", preparedby='" + preparedby + '\'' +
                ", newlyflag=" + newlyflag +
                ", isfinish=" + isfinish +
                ", schedule=" + schedule +
                ", remark='" + remark + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", remark3='" + remark3 + '\'' +
                '}';
    }
}
