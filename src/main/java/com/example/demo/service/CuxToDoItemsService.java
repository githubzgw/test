package com.example.demo.service;

import com.example.demo.dao.CuxTodoItemsMapper;
import com.example.demo.pojo.CuxTodoItems;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Zhao Guowei on 2018/7/21.
 */
@Service
public class CuxToDoItemsService {
    @Autowired
    private CuxTodoItemsMapper cuxTodoItemsMapper;
    public int insert(CuxTodoItems cuxTodoItems){
        return cuxTodoItemsMapper.insert(cuxTodoItems);
    }
    public int delete(Long id){
        return cuxTodoItemsMapper.deleteByPrimaryKey(id);
    }
    public int update(CuxTodoItems cuxTodoItems){
        return cuxTodoItemsMapper.updateByPrimaryKeySelective(cuxTodoItems);
    }
    public List<Object> selectToTable(int page , int limit){
        PageHelper.startPage(page, limit,true);
        return cuxTodoItemsMapper.selectAll();
    }
}
