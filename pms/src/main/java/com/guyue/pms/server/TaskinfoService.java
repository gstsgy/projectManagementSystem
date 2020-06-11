package com.guyue.pms.server;

import com.guyue.pms.bean.db.Taskinfo;
import com.guyue.pms.mapper.TaskinfoMapper;
import com.guyue.pms.util.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskinfoService {
    @Autowired
    private TaskinfoMapper taskinfoMapper;
    @Autowired
    private DbUtilServer dbUtilServer;
    @Autowired
    private IDUtil idUtil;

    public Taskinfo add(Taskinfo taskinfo){
        taskinfo.setId(idUtil.nextId());
        dbUtilServer.insert(taskinfo);
        return taskinfo;
    }
    public boolean delete(Taskinfo taskinfo){

        return taskinfoMapper.delete(taskinfo.getId())>0;
    }
    public boolean update(Taskinfo taskinfo){
        return taskinfoMapper.update(taskinfo)>0;
    }
    public List<Taskinfo> queryall(){

        return taskinfoMapper.queryall();
    }
    public List<Taskinfo> queryallonshow(){

        return taskinfoMapper.queryallonshow();
    }
}
