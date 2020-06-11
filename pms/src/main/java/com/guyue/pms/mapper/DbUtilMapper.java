package com.guyue.pms.mapper;

import com.guyue.pms.bean.db.InsertModel;
import org.springframework.stereotype.Repository;

@Repository
public interface DbUtilMapper {
    int insert(InsertModel insertModel);
}