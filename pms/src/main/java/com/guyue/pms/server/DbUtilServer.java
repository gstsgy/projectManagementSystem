package com.guyue.pms.server;

import com.guyue.pms.bean.db.InsertModel;
import com.guyue.pms.mapper.DbUtilMapper;
import com.guyue.pms.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class DbUtilServer {
    @Autowired
    private DbUtilMapper dbUtilMapper;



    public<T> int insert(T obj){
        List<T> list = new ArrayList<>();
        list.add(obj);


        return insert(list);
    }

    public<T> int insert(List<T> list) {
        if(list!=null&&list.size()>0){
            InsertModel insertModel = new InsertModel();
            Class c = list.get(0).getClass();
            insertModel.setTableName(c.getSimpleName());
            Field[] fields = c.getDeclaredFields();
            List<String> fs = new ArrayList<>();
            Arrays.stream(fields).forEach(item->{fs.add(item.getName());
                item.setAccessible(true);
            });
            insertModel.setFields(fs);
            List<List<String>>  vs = new ArrayList<>();
            list.forEach(item->{
                List<String> fv = new ArrayList<>();
                Arrays.stream(fields).forEach(it->{
                    String v = "";
                    String type = it.getType().getSimpleName();
                   // System.out.println(type);
                    if(type.equals("String")||type.equals("Integer")||
                            type.equals("Double")){
                        try {
                            v=it.get(item)==null?"null":String.format("'%s'",it.get(item).toString());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(type.equals("Date")){

                        try {
                            Object obj = it.get(item);
                            System.out.println(obj);
                            if(obj!=null){
                                Date d = (Date)obj;
                                v =String.format("'convert(datetime,'%s', 20)'", DateUtil.format(d,DateUtil.YMDHMS));

                            }else {
                                v = "null";
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            v=it.get(item)==null?"null":String.format("'%s'",it.get(item).toString());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    fv.add(v);
                });

                vs.add(fv);

            });
            insertModel.setValues(vs);
            return dbUtilMapper.insert(insertModel);

        }

        return 0;
    }

}
