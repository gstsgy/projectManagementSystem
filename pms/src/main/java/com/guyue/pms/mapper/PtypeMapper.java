package com.guyue.pms.mapper;

import com.guyue.pms.bean.db.Ptype;
import com.guyue.pms.bean.db.Subtype;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PtypeMapper {
    @Select("select * from ptype")
    List<Ptype> queryall();

    @Select("select * from ptype where id = #{id}")
    Ptype querybyid(Long id);

    @Select("select * from ptype where name = #{name}")
    Ptype querybyname(String name);
    @Delete("delete from ptype where id = #{id}")
    int delete(Long id);
    @Select("select * from subtype")
    List<Subtype> queryAllChildren();

    @Select("select * from subtype where tid = #{id}")
    List<Subtype> queryChildren(Long id);
    @Delete("delete from subtype where subid = #{subid}")
    int deleteChildren(Long subid);
}
