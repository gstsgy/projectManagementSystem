package com.guyue.pms.bean.db;

public class Profinance {
    private Long fid;//id
    private Long infoid;//项目id
    private Double sumofmoney;//总金额
    private Integer isfinish;//结清情况
    private Double advancecharge;//预付款

    private Integer isadvancecharge;//预付款是否付
    private Integer isbilling;//是否开票
    private Double billingnum;//开票金额
    private Double finalpayment;//尾款
    private String remark;//备注
    private String remark1;//备用1
    private String remark2;//备用2

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getInfoid() {
        return infoid;
    }

    public void setInfoid(Long infoid) {
        this.infoid = infoid;
    }

    public Double getSumofmoney() {
        return sumofmoney;
    }

    public void setSumofmoney(Double sumofmoney) {
        this.sumofmoney = sumofmoney;
    }

    public Integer getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(Integer isfinish) {
        this.isfinish = isfinish;
    }

    public Double getAdvancecharge() {
        return advancecharge;
    }

    public void setAdvancecharge(Double advancecharge) {
        this.advancecharge = advancecharge;
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

    public Double getBillingnum() {
        return billingnum;
    }

    public void setBillingnum(Double billingnum) {
        this.billingnum = billingnum;
    }

    public Double getFinalpayment() {
        return finalpayment;
    }

    public void setFinalpayment(Double finalpayment) {
        this.finalpayment = finalpayment;
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

    @Override
    public String toString() {
        return "Profinance{" +
                "fid=" + fid +
                ", infoid=" + infoid +
                ", sumofmoney=" + sumofmoney +
                ", isfinish=" + isfinish +
                ", advancecharge=" + advancecharge +
                ", isadvancecharge=" + isadvancecharge +
                ", isbilling=" + isbilling +
                ", billingnum=" + billingnum +
                ", finalpayment=" + finalpayment +
                ", remark='" + remark + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                '}';
    }
}
