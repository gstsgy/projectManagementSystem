package com.guyue.pms.controller;

import com.guyue.pms.bean.db.Procontent;
import com.guyue.pms.bean.db.Proinfo;
import com.guyue.pms.bean.view.ProcontentView;
import com.guyue.pms.bean.view.ProinfoView;
import com.guyue.pms.bean.view.ResponseBean;
import com.guyue.pms.bean.view.SearchProinfo;
import com.guyue.pms.server.ProinfoServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pro")
public class ProinfoController {
    @Autowired
    private ProinfoServer proinfoServer;
    @Autowired
    private ResponseBean responseBean;
    @PostMapping("proinfo")
    public ResponseBean addPro(@RequestBody Proinfo proinfo){
        return responseBean.getSuccess(proinfoServer.add(proinfo));
    }
    @GetMapping("proinfo")
    public ResponseBean query(SearchProinfo searchProinfo){
        return responseBean.getSuccess(proinfoServer.query(searchProinfo));
    }
    @PutMapping("proinfo")
    public ResponseBean update(@RequestBody ProinfoView proinfoView){
        return responseBean.getSuccess(proinfoServer.update(proinfoView));
    }
    @DeleteMapping("proinfo")
    public ResponseBean delete(@RequestBody List<Proinfo> list){
        return responseBean.getSuccess(proinfoServer.delete(list));
    }
   /* @PostMapping("/procontent")
    public ResponseBean addChildren(@RequestBody List<Procontent> list){
        return responseBean.getSuccess(proinfoServer.addContent(list));
    }*/
   @GetMapping("statistics")
   public ResponseBean getStatistics(String username){
       return responseBean.getSuccess(proinfoServer.statistics(username));
   }

    @PutMapping("read")
    public ResponseBean read(@RequestBody List<ProcontentView> list){
        return responseBean.getSuccess(proinfoServer.updateread(list));
    }
    @GetMapping("procountno")
    public ResponseBean getprocountno(String contractno){
        return responseBean.getSuccess(proinfoServer.getprocountno(contractno));
    }
}
