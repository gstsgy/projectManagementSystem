package com.guyue.pms.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TestMapper {
    @Select("select * from test")
    List<Map> test();
}
