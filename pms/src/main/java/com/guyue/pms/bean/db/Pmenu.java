package com.guyue.pms.bean.db;

public class Pmenu {
    private Long menuid;//id
    private String menuname;//名称
    private Long parentid;//父菜单id
    private int level;//菜单级别
    private String menuico;//菜单图标
    private String menupath;//菜单路由
    private String menuposi;//菜单位置
    private int order;//显示顺序
    private String remark;//备注
    private String remark1;//备用1
    private String remark2;//备用2
    private String remark3;//备用3

    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMenuico() {
        return menuico;
    }

    public void setMenuico(String menuico) {
        this.menuico = menuico;
    }

    public String getMenupath() {
        return menupath;
    }

    public void setMenupath(String menupath) {
        this.menupath = menupath;
    }

    public String getMenuposi() {
        return menuposi;
    }

    public void setMenuposi(String menuposi) {
        this.menuposi = menuposi;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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
        return "Pmenu{" +
                "menuid=" + menuid +
                ", menuname='" + menuname + '\'' +
                ", parentid=" + parentid +
                ", level=" + level +
                ", menuico='" + menuico + '\'' +
                ", menupath='" + menupath + '\'' +
                ", menuposi='" + menuposi + '\'' +
                ", order=" + order +
                ", remark='" + remark + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", remark3='" + remark3 + '\'' +
                '}';
    }
}
