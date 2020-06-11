package com.guyue.pms.controller;

import com.guyue.pms.bean.db.Taskinfo;
import com.guyue.pms.bean.view.ResponseBean;
import com.guyue.pms.server.TaskinfoService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskinfoController {
    @Autowired
    private TaskinfoService taskinfoService;
    @Autowired
    private ResponseBean responseBean;


    @PostMapping("task")
    public ResponseBean add(@RequestBody Taskinfo taskinfo){
        return responseBean.getSuccess(taskinfoService.add(taskinfo));
    }
    @DeleteMapping("task")
    public ResponseBean delete(@RequestBody Taskinfo taskinfo){

        return responseBean.getSuccess(taskinfoService.delete(taskinfo));
    }
    @PutMapping("task")
    public ResponseBean update(@RequestBody Taskinfo taskinfo){
        return responseBean.getSuccess(taskinfoService.update(taskinfo));
    }
    @GetMapping("tasks")
    public ResponseBean queryall(){

        return responseBean.getSuccess(taskinfoService.queryall());
    }
    @GetMapping("task")
    public ResponseBean queryallonshow(){

        return responseBean.getSuccess(taskinfoService.queryallonshow());
    }


}
