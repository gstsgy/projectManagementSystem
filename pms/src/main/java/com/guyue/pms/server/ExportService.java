package com.guyue.pms.server;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guyue.pms.bean.ExcelData;
import com.guyue.pms.bean.db.Profinance;
import com.guyue.pms.bean.db.Prole;
import com.guyue.pms.bean.db.Puser;
import com.guyue.pms.bean.view.*;
import com.guyue.pms.mapper.ProinfoMapper;
import com.guyue.pms.mapper.ProleMapper;
import com.guyue.pms.mapper.PuserMapper;
import com.guyue.pms.util.ExportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExportService {
    @Autowired
    private ProinfoMapper proinfoMapper;
    @Autowired
    private PuserMapper puserMapper;
    @Autowired
    private ProleMapper proleMapper;
    public PageInfo<ExportDataView> query(SearchDataView searchDataView){
        Puser puser = puserMapper.queryOne(searchDataView.getUsername());
        Prole p = proleMapper.queryone(puser.getUserroleid());
        if ("".equals(searchDataView.getRegisterdateE())) {
            searchDataView.setRegisterdateE(null);
        }
        if ("".equals(searchDataView.getRegisterdateS())) {
            searchDataView.setRegisterdateS(null);
        }
        PageHelper.startPage(searchDataView.getPageNum(), searchDataView.getPageSize());
        if (p != null && p.getRemark1().equals("0")) {

            List<ExportDataView> list = proinfoMapper.queryData(searchDataView);
            PageInfo<ExportDataView> pageModel = new PageInfo<>(list);
            return pageModel;
        }else{
            searchDataView.setUsername(puser.getRemark1());
            List<ExportDataView> list = proinfoMapper.queryDataByUser(searchDataView);
            PageInfo<ExportDataView> pageModel = new PageInfo<>(list);
            return pageModel;
        }

    }

    public PageInfo<ExportMoney> queryMoney(SearchMoneyView searchMoneyView){

        PageHelper.startPage(searchMoneyView.getPageNum(), searchMoneyView.getPageSize());
        List<ExportMoney> list = proinfoMapper.queryMoney(searchMoneyView);
        PageInfo<ExportMoney> pageModel = new PageInfo<>(list);
        return pageModel;
    }


    public ExcelData exportdata(SearchDataView searchDataView){
        Puser puser = puserMapper.queryOne(searchDataView.getUsername());
        Prole p = proleMapper.queryone(puser.getUserroleid());
        ExcelData excelData = new ExcelData();
        if ("".equals(searchDataView.getRegisterdateE())) {
            searchDataView.setRegisterdateE(null);
        }
        if ("".equals(searchDataView.getRegisterdateS())) {
            searchDataView.setRegisterdateS(null);
        }
        List<ExportDataView> list ;
        if (p != null && p.getRemark1().equals("0")) {
            list =proinfoMapper.queryData(searchDataView);
        }else{
            searchDataView.setUsername(puser.getRemark1());
            list = proinfoMapper.queryDataByUser(searchDataView);
        }
        excelData.setCols(Arrays.asList(0,1,2,3));
        excelData.setCol(2);
        excelData.setName("sheet1");
        excelData.setRows(ExportExcelUtil.ObjtoList(list));
        List<String> titles = Arrays.asList("建设单位","建设项目","合同编号","联系人","主类型","次类型","负责人","编制人员","完成情况","进度","备注");
        excelData.setTitles(titles);

        return excelData;
    }
    public ExcelData exportmoney(SearchMoneyView searchMoneyView){
        ExcelData excelData = new ExcelData();
        List<ExportMoney> list = proinfoMapper.queryMoney(searchMoneyView);
        excelData.setCols(new ArrayList<>());
        excelData.setCol(0);
        excelData.setName("sheet1");
        excelData.setRows(ExportExcelUtil.ObjtoList(list));
        List<String> titles = Arrays.asList("建设单位","建设项目","合同编号","联系人","总金额","预付款","尾款","完成支付情况","开票情况","备注");
        excelData.setTitles(titles);

        return excelData;
    }

    public Profinance querymoney(Long fid){
        return proinfoMapper.queryMoney1(fid);
    }

    public boolean updatemoney(Profinance profinance){
        return proinfoMapper.updateMoney(profinance)>0;
    }
}
