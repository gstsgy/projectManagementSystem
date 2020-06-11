package com.guyue.pms.controller;

import com.guyue.pms.bean.db.Puser;
import com.guyue.pms.bean.view.ResponseBean;
import com.guyue.pms.server.PuserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/puser")
public class PuserController {
    @Autowired
    private PuserServer puserServer;
    @Autowired
    private  ResponseBean responseBean;
    @PostMapping("/puser")
    public ResponseBean add(@RequestBody Puser puser){
        return  responseBean.getSuccess(puserServer.add(puser));
    }
    @GetMapping("/checkisexistbycode")
    public ResponseBean checkIsexistBycode(String code){
        return  responseBean.getSuccess(puserServer.checkIsexistBycode(code));
    }

    @GetMapping("/checkisexistbyname")
    public ResponseBean checkIsexistByName(String name){
        return  responseBean.getSuccess(puserServer.checkIsexistByName(name));
    }
   @DeleteMapping("/users")
    public ResponseBean delete(@RequestBody List<Puser> list){
        return responseBean.getSuccess(puserServer.delete(list));
    }
    @PutMapping("/puser")
    public ResponseBean update(@RequestBody Puser puser){
        return responseBean.getSuccess(puserServer.update(puser));
    }
    @PutMapping("/rpw")
    public ResponseBean change1(@RequestBody Puser puser){
        return responseBean.getSuccess(puserServer.update1(puser));
    }
    @PutMapping("/cpw")
    public ResponseBean change2(String username ,String oldpw,String npw){
        return responseBean.getSuccess(puserServer.update2(username,oldpw,npw));
    }
    @GetMapping("/users")
    public ResponseBean query(Puser puser){
        return responseBean.getSuccess(puserServer.query(puser));
    }

}
