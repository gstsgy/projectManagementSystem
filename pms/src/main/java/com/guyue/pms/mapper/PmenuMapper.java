package com.guyue.pms.mapper;

import com.guyue.pms.bean.db.Pmenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmenuMapper {
    @Select("select * from pmenu where parentid = #{parentid}")
    List<Pmenu> queryByParent(Long parentid);

    @Select("select * from pmenu where menuid = #{id}")
    Pmenu queryByid(Long id);

    @Select("select m.* from pmenu m,menurole r where r.roleid = #{roleid} and r.menuid = m.menuid ")
    List<Pmenu> queryByroleid(Long roleid);

    @Delete("delete from menurole where roleid = #{roleid}")
    int delete( Long roleid);
}
