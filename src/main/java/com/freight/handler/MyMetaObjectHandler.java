package com.freight.handler;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override    //在执行mybatisPlus的insert()时，为我们自动给某些字段填充值，这样的话，我们就不需要手动给insert()里的实体类赋值了
    public void insertFill(MetaObject metaObject) {
        System.out.println("sql  "+new Date());
        //  setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject) {
        this.setFieldValByName("create_time",new Date(), metaObject);
        //his.setFieldValByName("id", IdType.ASSIGN_UUID, metaObject);
    }

    @Override//在执行mybatisPlus的update()时，为我们自动给某些字段填充值，这样的话，我们就不需要手动给update()里的实体类赋值了
    public void updateFill(MetaObject metaObject) {
//        this.setFieldValByName("modifierId", new Long(111), metaObject);
//        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}