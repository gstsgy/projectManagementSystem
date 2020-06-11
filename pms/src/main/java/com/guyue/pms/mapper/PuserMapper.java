package com.guyue.pms.mapper;

import com.guyue.pms.bean.db.Puser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuserMapper {
    @Select("select * from puser")
    List<Puser> query();

    @Select("select * from puser where username=#{value}")
    Puser queryOne(String username);

    @Select("select * from puser where remark1=#{value}")
    Puser queryOneByName(String remark1);

    int update(Puser puser);
    @Update("update puser set userpw = #{userpw} where userid = #{userid}")
    int update1(Puser puser);

    @Delete("delete from puser where userid = #{userid}")
    int delete(long userid);
}
