package com.guyue.pms.bean.view;

public class ExportMoney {
    private Long fid;//id
    private String compname;
    private String proname;
    private String contractno;
    private String contact;

    private Double sumofmoney;
    private Double advancecharge;
    private Double finalpayment;

    private String finshname;
    private String billName;
    private String remark;

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

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

    public Double getSumofmoney() {
        return sumofmoney;
    }

    public void setSumofmoney(Double sumofmoney) {
        this.sumofmoney = sumofmoney;
    }

    public Double getAdvancecharge() {
        return advancecharge;
    }

    public void setAdvancecharge(Double advancecharge) {
        this.advancecharge = advancecharge;
    }

    public Double getFinalpayment() {
        return finalpayment;
    }

    public void setFinalpayment(Double finalpayment) {
        this.finalpayment = finalpayment;
    }

    public String getFinshname() {
        return finshname;
    }

    public void setFinshname(String finshname) {
        this.finshname = finshname;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
