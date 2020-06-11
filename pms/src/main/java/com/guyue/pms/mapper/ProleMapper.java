package com.guyue.pms.mapper;

import com.guyue.pms.bean.db.Prole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProleMapper {
    @Delete("delete from prole where roleid = #{value}")
    int delete(long proleid);
    @Update("update Prole set remark1 = #{remark1},rolename = #{rolename} where roleid = #{roleid}")
    int update(Prole p);
    @Select("select * from prole")
    List<Prole> query();

    @Select("select * from prole where  roleid = #{value}")
    Prole queryone(long proleid);


}
