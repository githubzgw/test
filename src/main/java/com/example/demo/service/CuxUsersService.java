package com.example.demo.service;

import com.example.demo.dao.CuxUsersMapper;
import com.example.demo.pojo.CuxUsers;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhao Guowei on 2018/7/21.
 */
@Service
public class CuxUsersService {
    @Autowired
    private CuxUsersMapper cuxUsersMapper;
    public int insert(CuxUsers cuxUsers){
        return cuxUsersMapper.insert(cuxUsers);
    }
    public int delete(Long id){
        try{
            return cuxUsersMapper.deleteByPrimaryKey(id);
        }catch(Exception e){
            return 0;
        }
    }
    public int update(CuxUsers cuxUsers){
        return cuxUsersMapper.updateByPrimaryKeySelective(cuxUsers);
    }
    public List<Object> selectAll(){
        return cuxUsersMapper.selectAll();
    }
    public List<Object> selectToTable(int page , int limit){
        PageHelper.startPage(page, limit,true);
        return cuxUsersMapper.selectAll();
    }
    public CuxUsers selectByUsername(String username){
        return  cuxUsersMapper.selectByUsername(username);
    }


}
