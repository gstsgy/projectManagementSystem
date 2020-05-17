package com.guyue.pms.server;

import com.guyue.pms.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TestServer {
    @Autowired
    private TestMapper testMapper;


    public List<Map> tsst(){
        return testMapper.test();
    }
}
