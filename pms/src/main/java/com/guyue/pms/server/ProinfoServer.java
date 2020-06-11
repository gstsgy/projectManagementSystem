package com.guyue.pms.server;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guyue.pms.bean.db.*;
import com.guyue.pms.bean.view.ProcontentView;
import com.guyue.pms.bean.view.ProinfoView;
import com.guyue.pms.bean.view.SearchProinfo;
import com.guyue.pms.mapper.ProinfoMapper;
import com.guyue.pms.mapper.ProleMapper;
import com.guyue.pms.mapper.PuserMapper;
import com.guyue.pms.util.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProinfoServer {
    @Autowired
    private ProinfoMapper proinfoMapper;
    @Autowired
    private DbUtilServer dbUtilServer;
    @Autowired
    private IDUtil idUtil;
    @Transactional
    public boolean add(Proinfo proinfo) {
        proinfo.setProid(idUtil.nextId());
        Profinance profinance = new Profinance();
        profinance.setFid(idUtil.nextId());
        profinance.setInfoid(proinfo.getProid());
        profinance.setIsfinish(0);
        profinance.setIsadvancecharge(0);
        profinance.setIsbilling(0);
        dbUtilServer.insert(profinance);
        return dbUtilServer.insert(proinfo) > 0;
    }

    public boolean addContent(List<Procontent> list) {
        list.stream().forEach(item -> item.setId(idUtil.nextId()));
        return dbUtilServer.insert(list) > 0;
    }

    public boolean delete(List<Proinfo> list) {
        list.forEach(item -> {
            proinfoMapper.deleteMoney(item.getProid());
            proinfoMapper.delete(item.getProid());
            proinfoMapper.deleteChildren1(item.getProid());
        });
        return 1 > 0;
    }

    @Autowired
    private PuserMapper puserMapper;
    @Autowired
    private ProleMapper proleMapper;
    @Autowired
    private  PtypeServer ptypeServer;
    public PageInfo<ProinfoView> query(SearchProinfo searchProinfo) {
        Puser puser = puserMapper.queryOne(searchProinfo.getUsername());
        Prole p = proleMapper.queryone(puser.getUserroleid());
        if ("".equals(searchProinfo.getRegisterdateE())) {
            searchProinfo.setRegisterdateE(null);
        }
        if ("".equals(searchProinfo.getRegisterdateS())) {
            searchProinfo.setRegisterdateS(null);
        }

        PageHelper.startPage(searchProinfo.getPageNum(), searchProinfo.getPageSize());
        if (p != null && p.getRemark1().equals("0")) {

            List<ProinfoView> list = proinfoMapper.query(searchProinfo);
            PageHelper.startPage(0, 0,false,null,true);
            //System.out.println(list.size());
            list.forEach(item -> {
                        item.setChildren(proinfoMapper.queryChildren(item.getProid()));
                        item.getChildren().forEach(it->it.setSubtypeenum(ptypeServer.querysubEnum(it.getPtypeid())));
                    });
            PageInfo<ProinfoView> pageModel = new PageInfo<>(list);
            return pageModel;
        } else {
            searchProinfo.setUsername(puser.getRemark1());
            List<ProinfoView> list = proinfoMapper.queryByid(searchProinfo);
            PageHelper.startPage(0, 0,false,null,true);
            list.forEach(item -> {
                item.setChildren(proinfoMapper.queryChildrenByid(item.getProid(),searchProinfo.getUsername()));
                item.getChildren().forEach(it->it.setSubtypeenum(ptypeServer.querysubEnum(it.getPtypeid())));
            });
            PageInfo<ProinfoView> pageModel = new PageInfo<>(list);
            return pageModel;
        }


    }

    /*  public boolean update(Proinfo proinfo){
          return proinfoMapper.update(proinfo)>0;
      }*/
    @Transactional
    public boolean update(ProinfoView proinfoView) {
        List<ProcontentView>  listtmp=  proinfoMapper.queryChildren(proinfoView.getProid());
        // 更新父
        proinfoMapper.update(proinfoView);
        // 新增子
        List<Procontent> listnew = proinfoView.getChildren().stream().filter(item -> item.getId() == null).map(Procontent::new).collect(Collectors.toList());
        addContent(listnew);
        // 更新子
        List<Procontent> listold = proinfoView.getChildren().stream().filter(item -> item.getId() != null).map(Procontent::new).collect(Collectors.toList());
        listold.forEach(proinfoMapper::updateChildren);
        // 删除子

        listtmp=  listtmp.stream().filter(item->!proinfoView.getChildren().contains(item)).collect(Collectors.toList());
        //System.out.println(proinfoView.getChildren());
        //System.out.println(listtmp);
        listtmp.forEach(item->proinfoMapper.deleteChildren(item));
        return true;
    }


    public Map statistics(String  username) {
        Puser puser = puserMapper.queryOne(username);
        Prole p = proleMapper.queryone(puser.getUserroleid());
        Map map = new HashMap();
        if (p != null && p.getRemark1().equals("0")) {
            map.put("sum",proinfoMapper.querysum());
            map.put("about",proinfoMapper.queryusersumnoread(puser.getRemark1()));
        } else {
            map.put("sum",proinfoMapper.queryusersum(puser.getRemark1()));
            map.put("about",proinfoMapper.queryusersumnoread(puser.getRemark1()));
        }
        return map;
    }

    public int getprocountno(String contractno){
        return proinfoMapper.queryproByNo(contractno);
    }

    public Boolean updateread(List<ProcontentView> list) {
        list.stream().map(ProcontentView::getId).forEach(proinfoMapper::updateRead);
        return true;
    }
}
