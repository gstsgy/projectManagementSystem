package com.guyue.pms.controller;

import com.guyue.pms.bean.db.Prole;
import com.guyue.pms.bean.db.Puser;
import com.guyue.pms.bean.view.ResponseBean;
import com.guyue.pms.server.PmenuServer;
import com.guyue.pms.server.ProleServer;
import com.guyue.pms.server.PuserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("system")
public class SystemMangeController {
    @Autowired
    private PuserServer puserServer;
    @Autowired
    private ProleServer proleServer;
    @Autowired
    private PmenuServer pmenuServer;
    @Autowired
    private ResponseBean responseBean;
    @PostMapping("/login")
    public ResponseBean login(String username,String password){
       // System.out.println(username);
        Puser p = puserServer.login(username,password);
        return p==null?responseBean.getError("账号密码不匹配"):responseBean.getSuccess(p);
    }
    @PostMapping("/prole")
    public ResponseBean addrole(@RequestBody Prole prole){
       return  responseBean.getSuccess(proleServer.add(prole));
    }

    @DeleteMapping("/prole")
    public ResponseBean deleterole(@RequestBody List<Prole> list){
        return  responseBean.getSuccess(proleServer.delete(list));
    }
    @PutMapping("/prole")
    public ResponseBean putrole(@RequestBody Prole prole){
        return  responseBean.getSuccess(proleServer.update(prole));
    }

    @GetMapping("/proles")
    public ResponseBean queryrole( ){
        return  responseBean.getSuccess(proleServer.query());
    }
    @GetMapping("/proleenum")
    public ResponseBean queryroleenum( ){
        return  responseBean.getSuccess(proleServer.queryEnum());
    }

    @PostMapping("/menurole")
    public ResponseBean saveRole(@RequestBody Map map ){
        //System.out.println(map);
        Long roleid = Long.valueOf(map.get("roleid").toString()) ;
        List<Integer> menuid = (ArrayList<Integer>) map.get("menuid");
        //menuid= menuid.stream().map(it->Long.valueOf(it.toString())).collect(Collectors.toList());
        return  responseBean.getSuccess(pmenuServer.saveRole( roleid,menuid));
    }




    @GetMapping("/menus")
    public ResponseBean getMenus(Long roleid){
        return responseBean.getSuccess(pmenuServer.getMenus(roleid));
    }

    @GetMapping("/menus1")
    public ResponseBean getMenus(){
       // System.out.println("wqeeeeee");
        return responseBean.getSuccess(pmenuServer.getMenus());
    }

    @GetMapping("/controls")
    public ResponseBean getControls(Long roleid){
        return responseBean.getSuccess(pmenuServer.getControls(roleid));
    }


    @GetMapping("/proledata")
    public ResponseBean queryroledata(String username ){

        return  responseBean.getSuccess(proleServer.queryroledata(username));
    }

}
