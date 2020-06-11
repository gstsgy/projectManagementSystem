package com.guyue.pms.controller;

import com.guyue.pms.bean.db.Ptype;
import com.guyue.pms.bean.db.Subtype;
import com.guyue.pms.bean.view.ResponseBean;
import com.guyue.pms.server.PtypeServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ptype")
public class PtypeController {
    @Autowired
    private ResponseBean responseBean;
    @Autowired
    private PtypeServer ptypeServer;

    @PostMapping("ptype")
    public ResponseBean addPtype(@RequestBody Ptype ptype) {

        return responseBean.getSuccess(ptypeServer.addPtype(ptype));
    }

    @PostMapping("subtype")
    public ResponseBean addSubtype(@RequestBody Subtype subtype) {

        return responseBean.getSuccess(ptypeServer.addSubtype(subtype));
    }

    @GetMapping("ptypes")
    public ResponseBean queryall() {
        return responseBean.getSuccess(ptypeServer.queryall());
    }

    @DeleteMapping("ptype")
    public ResponseBean delete(@RequestBody List<Ptype> list) {
        return responseBean.getSuccess(ptypeServer.delete(list));
    }

    @GetMapping("subtype")
    public ResponseBean queryAllChildren() {
        return responseBean.getSuccess(ptypeServer.queryAllChildren());
    }

    @GetMapping("subtypebyid")
    public ResponseBean queryAllChildren(Long id) {
        return responseBean.getSuccess(ptypeServer.queryAllChildren(id));
    }

    @DeleteMapping("subtype")
    public ResponseBean deleteChildren(@RequestBody List<Subtype> list) {
        return responseBean.getSuccess(ptypeServer.deleteChildren(list));
    }

    @GetMapping("ptypeenum")
    public ResponseBean queryallenum() {
        return responseBean.getSuccess(ptypeServer.queryallEnum());
    }

    @GetMapping("subenum")
    public ResponseBean querysubtypeenum() {
        return responseBean.getSuccess(ptypeServer.querysubEnum());
    }

    @GetMapping("subenumidbyid")
    public ResponseBean querysubtypeenum(Long infoid) {
        return responseBean.getSuccess(ptypeServer.querysubEnum(infoid));
    }
}
