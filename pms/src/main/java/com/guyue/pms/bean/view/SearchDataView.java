package com.guyue.pms.bean.view;

public class SearchDataView {
    private String compname;
    private String proname;
    private String registerdateS;//登记日期
    private String registerdateE;//登记日期
    private String contractno;
    private String mainpeople;
    private String preparedby;
    private Integer isarrange;
    private Integer isfinish;//
    private Integer pageNum;//
    private Integer pageSize;//
    private Integer total;//
    private Long ptypeid;//主类型
    private Long subid;//次类型
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRegisterdateS() {
        return registerdateS;
    }

    public void setRegisterdateS(String registerdateS) {
        this.registerdateS = registerdateS;
    }

    public String getRegisterdateE() {
        return registerdateE;
    }

    public void setRegisterdateE(String registerdateE) {
        this.registerdateE = registerdateE;
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno;
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

    public Integer getIsarrange() {
        return isarrange;
    }

    public void setIsarrange(Integer isarrange) {
        this.isarrange = isarrange;
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

    public Integer getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(Integer isfinish) {
        this.isfinish = isfinish;
    }

    @Override
    public String toString() {
        return "SearchDataView{" +
                "compname='" + compname + '\'' +
                ", proname='" + proname + '\'' +
                ", registerdateS='" + registerdateS + '\'' +
                ", registerdateE='" + registerdateE + '\'' +
                ", contractno='" + contractno + '\'' +
                ", mainpeople='" + mainpeople + '\'' +
                ", preparedby='" + preparedby + '\'' +
                ", isarrange=" + isarrange +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", ptypeid=" + ptypeid +
                ", subid=" + subid +
                '}';
    }
}
