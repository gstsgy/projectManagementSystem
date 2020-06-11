package com.guyue.pms.server;

import com.guyue.pms.bean.db.Prole;
import com.guyue.pms.bean.db.Puser;
import com.guyue.pms.mapper.ProleMapper;
import com.guyue.pms.mapper.PuserMapper;
import com.guyue.pms.util.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProleServer {
    @Autowired
    private ProleMapper proleMapper;
    @Autowired
    private DbUtilServer db;
    @Autowired
    private IDUtil idUtil;
    public boolean add(Prole prole){
       prole.setRoleid(idUtil.nextId());
       return db.insert(prole)>0;
    }

    public boolean delete(List<Prole> list){
        for(Prole p:list){
            proleMapper.delete(p.getRoleid());
        }
        return true;
    }

    public List<Prole> query(){
        return proleMapper.query();
    }
    @Autowired
    private PuserMapper puserMapper;

    public String queryroledata(String username){
        Puser puser = puserMapper.queryOne(username);
        Prole p = proleMapper.queryone(puser.getUserroleid());
        return p.getRemark1();
    }


    public List queryEnum(){
        List<Prole> list= proleMapper.query();
        return list.stream().map(item->
             new Object(){
                public long code = item.getRoleid();
                public String name = item.getRolename();

        }).collect(Collectors.toList());
    }

    public boolean update(Prole p){
        return proleMapper.update(p)>0;
    }
}
