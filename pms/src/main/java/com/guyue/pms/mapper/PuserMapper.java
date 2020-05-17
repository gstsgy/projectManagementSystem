package com.guyue.pms.mapper;

import com.guyue.pms.bean.db.Puser;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface PuserMapper {
    @Insert("insert into puser(username, usersex,userpw,useremail,userroleid) values (#{username}, #{usersex},#{userpw},#{useremail},#{userroleid})")
    int insert(Puser puser);
}
