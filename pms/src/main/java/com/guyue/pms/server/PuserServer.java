package com.guyue.pms.server;

import com.guyue.pms.bean.db.Puser;
import com.guyue.pms.mapper.PuserMapper;
import com.guyue.pms.util.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class PuserServer {
    @Value("${password}")
    private String pw;
    @Autowired
    private IDUtil idUtil;
    @Autowired
    private DbUtilServer dbUtilServer;
    @Autowired
    private PuserMapper puserMapper;

    public boolean add(Puser puser){
        if(puser!=null){
            puser.setUserid(idUtil.nextId());
            puser.setUserpw(pw);
          return   dbUtilServer.insert(puser)>0;
        }
        return false;
    }

    public boolean checkIsexistBycode(String code){
        Puser p = puserMapper.queryOne(code);
        return p!=null;
      //  return false;
    }
    public boolean checkIsexistByName(String name){
        Puser p = puserMapper.queryOneByName(name);
        return p!=null;
        //  return false;
    }

    public boolean delete(List<Puser> list){
        list.stream().filter(Objects::nonNull).forEach(item->puserMapper.delete(item.getUserid()));
        return true;
    }

    public boolean update(Puser puser){
      //  System.out.println(puser);
        return puserMapper.update(puser)>0;
    }

    public boolean update1(Puser puser){
        puser.setUserpw(pw);
        return puserMapper.update1(puser)>0;
    }

    public boolean update2(String username ,String oldpw,String npw){
        //  System.out.println(puser);
        if(username.equals("admin")){
            return  false;
        }
        Puser puser = puserMapper.queryOne(username);
        if(puser.getUserpw().equals(oldpw)){
            puser.setUserpw(npw);
            return puserMapper.update1(puser)>0;
        }
        return  false;
    }

    public List<Puser> query(Puser puser){
        List<Puser> list =   puserMapper.query().stream().filter(item->!"admin".equals(item.getUsername())).collect(Collectors.toList());
        if(puser.getRemark1()!=null&&puser.getRemark1().length()!=0){
            list= list.stream().filter(item->item.getRemark1().contains(puser.getRemark1())).collect(Collectors.toList());
        }
        if(puser.getUseremail()!=null&&puser.getUseremail().length()!=0){
            list= list.stream().filter(item->item.getUseremail().contains(puser.getUseremail())).collect(Collectors.toList());
        }
        if(puser.getUserroleid()!=null&&puser.getUserroleid()!=0){
            list= list.stream().filter(item->item.getUserroleid().equals(puser.getUserroleid())).collect(Collectors.toList());
        }
         return list;
    }

    public Puser login(String username,String password){
        Puser puser = puserMapper.queryOne(username);
        if(puser!=null&&puser.getUserpw().equals(password))return puser;
        return null;
    }
}
