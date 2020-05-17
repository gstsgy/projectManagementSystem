package com.guyue.pms.server;

import com.guyue.pms.bean.db.Puser;
import com.guyue.pms.mapper.PuserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PuserServer {
    @Autowired
    private PuserMapper puserMapper;

    public boolean insert(Puser puser){
        return puserMapper.insert(puser)>0;
    }
}
