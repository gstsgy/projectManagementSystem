package com.guyue.pms.controller;

import com.guyue.pms.bean.db.Puser;
import com.guyue.pms.server.DbUtilServer;
import com.guyue.pms.server.PuserServer;
import com.guyue.pms.server.TestServer;
import com.guyue.pms.util.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private TestServer testServer;
    @Autowired
    private PuserServer puserServer;
    @Autowired
    private DbUtilServer dbUtilServer;
    @Autowired
    private IDUtil idUtil;

    @GetMapping("t1")
    public String test() {
        return "hello world!";
    }

    @GetMapping("t2")
    public List<Map> test1() {
        return testServer.tsst();
    }

  /*  @GetMapping("t3")
    public List<Puser> test2() {
        Puser puser = new Puser();
        Random random = new Random();
        puser.setUserid(idUtil.nextId());
        puser.setUseremail("test"+random.nextInt());
        puser.setUsername("test"+random.nextInt());
        puser.setUsersex(0);
        puser.setUserpw("1234456");
        dbUtilServer.insert(puser);
        return puserServer.query();
    }*/
}
