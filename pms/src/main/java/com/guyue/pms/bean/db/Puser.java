package com.guyue.pms.bean.db;

public class Puser {
    private Long userid;//工号
    private String username;//用户名
    private Integer usersex;//性别
    private String useremail;//邮箱
    private Long userroleid;//角色id
    private String remark;//备注
    // 姓名
    private String remark1;//备用1
    private String remark2;//备用2
    private String remark3;//备用3
    private String userpw;//密码

    public String getUserpw() {
        return userpw;
    }

    public void setUserpw(String userpw) {
        this.userpw = userpw;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUsersex() {
        return usersex;
    }

    public void setUsersex(Integer usersex) {
        this.usersex = usersex;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public Long getUserroleid() {
        return userroleid;
    }

    public void setUserroleid(Long userroleid) {
        this.userroleid = userroleid;
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
        return "Puser{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", usersex=" + usersex +
                ", useremail='" + useremail + '\'' +
                ", userroleid=" + userroleid +
                ", remark='" + remark + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", remark3='" + remark3 + '\'' +
                '}';
    }
}
