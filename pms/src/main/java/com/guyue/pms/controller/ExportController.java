package com.guyue.pms.controller;

import com.guyue.pms.bean.ExcelData;
import com.guyue.pms.bean.db.Profinance;
import com.guyue.pms.bean.view.ResponseBean;
import com.guyue.pms.bean.view.SearchDataView;
import com.guyue.pms.bean.view.SearchMoneyView;
import com.guyue.pms.server.ExportService;
import com.guyue.pms.util.DateUtil;
import com.guyue.pms.util.ExportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("export")
public class ExportController {
    @Autowired
    private ExportService exportService;
    @Autowired
    private ResponseBean responseBean;
    @GetMapping("data")
    @ResponseBody
    public ResponseBean queryDate(SearchDataView searchDataView){
        System.out.println(searchDataView);
        return responseBean.getSuccess(exportService.query(searchDataView));
    }
    @GetMapping("exportdata")
    public HttpServletResponse queryexportDate(SearchDataView searchDataView, HttpServletResponse response) throws Exception {
        ExcelData excelData = exportService.exportdata(searchDataView);
        String filename = "项目文件数据"+ DateUtil.format(new Date(),"yyyyMMdd");
        ExportExcelUtil.exportExcel(response,filename,excelData);
        return response;
    }

    @GetMapping("money")
    @ResponseBody
    public ResponseBean queryMoney(SearchMoneyView searchMoneyView){
        //System.out.println(searchDataView);
        return responseBean.getSuccess(exportService.queryMoney(searchMoneyView));
    }
    @GetMapping("exportmoney")
    public HttpServletResponse queryexportMoney(SearchMoneyView searchMoneyView, HttpServletResponse response) throws Exception {
        ExcelData excelData = exportService.exportmoney(searchMoneyView);
        String filename = "财务数据"+ DateUtil.format(new Date(),"yyyyMMdd");
        ExportExcelUtil.exportExcel(response,filename,excelData);
        return response;
    }
    @PutMapping("money")
    @ResponseBody
    public ResponseBean updateMoney(@RequestBody Profinance profinance){
        return responseBean.getSuccess(exportService.updatemoney(profinance));
    }
    @GetMapping("money1")
    @ResponseBody
    public ResponseBean updateMoney(Long fid){
        return responseBean.getSuccess(exportService.querymoney(fid));
    }
}
