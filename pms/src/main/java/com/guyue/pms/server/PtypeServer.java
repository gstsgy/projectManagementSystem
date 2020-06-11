package com.guyue.pms.server;

import com.guyue.pms.bean.db.Ptype;
import com.guyue.pms.bean.db.Subtype;
import com.guyue.pms.mapper.PtypeMapper;
import com.guyue.pms.util.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PtypeServer {
    @Autowired
    private PtypeMapper ptypeMapper;
    @Autowired
    private DbUtilServer dbUtilServer;
@Autowired
    private IDUtil idUtil;
    public boolean addPtype(Ptype ptype){
        ptype.setId(idUtil.nextId());
        return dbUtilServer.insert(ptype)>0;
    }
    public boolean addSubtype(Subtype subtype){
        subtype.setSubid(idUtil.nextId());
        return dbUtilServer.insert(subtype)>0;
    }

    public List<Ptype> queryall() {
        return ptypeMapper.queryall();
    }
    public List<Map> queryallEnum() {
        List<Ptype> list = ptypeMapper.queryall();

        return list.stream().map(item->{
            Map map = new HashMap();
            map.put("code",item.getId());
            map.put("name",item.getName());
            return map;
        }).collect(Collectors.toList());
    }
    public List<Map>  querysubEnum() {
        List<Subtype> list= ptypeMapper.queryAllChildren();
        return list.stream().map(item->{
            Map map = new HashMap();
            map.put("code",item.getSubid());
            map.put("name",item.getName());
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map>  querysubEnum(Long id) {
        List<Subtype> list= ptypeMapper.queryChildren(id);
        return list.stream().map(item->{
            Map map = new HashMap();
            map.put("code",item.getSubid());
            map.put("name",item.getName());
            return map;
        }).collect(Collectors.toList());
    }

    @Transactional
    public boolean delete(List<Ptype> list) {
        list.stream().forEach(item -> {
            ptypeMapper.delete(item.getId());
            List<Subtype> tmp = ptypeMapper.queryChildren(item.getId());
            tmp.stream().mapToLong(Subtype::getSubid).forEach(ptypeMapper::deleteChildren);
        });
        return true;
    }

    public List<Subtype> queryAllChildren() {
        return ptypeMapper.queryAllChildren();
    }

    public List<Subtype> queryAllChildren(Long id) {
        return ptypeMapper.queryChildren(id);
    }

    public boolean deleteChildren(List<Subtype> list) {
        list.stream().mapToLong(Subtype::getSubid).forEach(ptypeMapper::deleteChildren);
        return 1 > 0;
    }
}
