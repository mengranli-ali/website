package com.lrm.blog.service;

import com.lrm.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    //以下是实现接口的方法
    Type saveType(Type type);
    //look for types
    Type getType(Long id);

    Type getTypeByName(String name);

    //分页
    Page<Type> listType(Pageable pageable);
    //修改更新

    List<Type> listType();

    //List<Type>
    List<Type> listTypeTop(Integer size);

    Type updateType (Long id, Type type);
    //delete - 返回一个空
    void deleteType(Long id);
}
