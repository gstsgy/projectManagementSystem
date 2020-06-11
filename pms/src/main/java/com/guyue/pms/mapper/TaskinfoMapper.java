package com.guyue.pms.mapper;

import com.guyue.pms.bean.db.Taskinfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskinfoMapper {
    @Select("select * from taskinfo")
    List<Taskinfo> queryall();

    @Select("select * from taskinfo where show = 1 order by type,seq")
    List<Taskinfo> queryallonshow();
    @Delete("delete from taskinfo where id = #{id}")
    int delete(Long id);
    @Update("update taskinfo set show = #{show},type=#{type},content=#{content},seq=#{seq},date=#{date}" +
            " where id = #{id}")
    int update(Taskinfo taskinfo);
}
