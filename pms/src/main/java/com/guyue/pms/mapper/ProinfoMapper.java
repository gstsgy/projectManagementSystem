package com.guyue.pms.mapper;

import com.guyue.pms.bean.db.Procontent;
import com.guyue.pms.bean.db.Prodocument;
import com.guyue.pms.bean.db.Profinance;
import com.guyue.pms.bean.db.Proinfo;
import com.guyue.pms.bean.view.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProinfoMapper {
    List<ProinfoView> query(SearchProinfo proinfo);

    List<ProinfoView> queryByid(SearchProinfo proinfo);

    Proinfo queryone(Long proid);

    int delete(Long proid);

    int update(ProinfoView proinfo);

    @Select("select * from Procontent where infoid = #{infoid}")
    List<ProcontentView> queryChildren(Long infoid);

    @Select("select * from Procontent where infoid = #{infoid} and ( mainpeople = #{username} or preparedby = #{username} )")
    List<ProcontentView> queryChildrenByid(@Param("infoid") Long infoid, @Param("username") String username);

    int updateChildren(Procontent procontent);

    @Delete("delete from procontent where id=#{id}")
    int deleteChildren(ProcontentView procontent);

    @Delete("delete from procontent where infoid=#{infoid}")
    int deleteChildren1(Long infoid);

    List<ExportDataView> queryData(SearchDataView searchDataView);

    List<ExportDataView> queryDataByUser(SearchDataView searchDataView);

    @Delete("delete from Profinance where infoid = #{proid}")
    int deleteMoney(Long proid);

    int updateMoney(Profinance profinance);

    List<ExportMoney> queryMoney(SearchMoneyView searchMoneyView);

    @Select("select * from Profinance where fid = #{fid}")
    Profinance queryMoney1(Long fid);

    List<Prodocument> queryfile(SearchFileView searchFileView);

    @Delete("delete from prodocument where mid = #{mid}")
    int deletefile(Long mid);

    @Update("update prodocument set mname = #{mname},fabout= #{fabout},ftype=#{ftype} where mid=#{mid}")
    int updatefile(Prodocument prodocument);

    @Select("select count(0) from Procontent")
    Long querysum();

    @Select("select count(0) from Procontent where mainpeople = #{username} or preparedby = #{username}  ")
    Long queryusersum(String username);

    @Select("select count(0) from Procontent where ( mainpeople = #{username} or preparedby = #{username} ) and newlyflag =0 ")
    Long queryusersumnoread(String username);

    @Update("update Procontent set newlyflag = 1 where id=#{id}")
    int updateRead(Long id);

    @Select("select count(0) from proinfo   where contractno = #{contractno}")
    int queryproByNo(String contractno);
}
