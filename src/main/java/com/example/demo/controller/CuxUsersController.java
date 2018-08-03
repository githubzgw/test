package com.example.demo.controller;

import com.example.demo.pojo.CuxUsers;
import com.example.demo.service.CuxUsersService;
import com.example.demo.utils.BigIntUtil;
import com.example.demo.utils.JsonDateValueProcessor;
import com.example.demo.utils.JsonToLayuiTable;
import com.example.demo.utils.MD5Util;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Zhao Guowei on 2018/7/21.
 */
@RestController
@RequestMapping(value = "/cuxUsers")
public class CuxUsersController {

    @Autowired
    public CuxUsersService cuxUsersService;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    @RequestMapping(value = "/insert")
    public String insert(CuxUsers cuxUsers) throws NoSuchAlgorithmException {
        cuxUsers.setUserId(BigIntUtil.getUniqueId());
        cuxUsers.setPassword(MD5Util.GetMD5(cuxUsers.getPassword()));
        if(cuxUsersService.insert(cuxUsers)>0){
            return "success";
        }
        else{
            return "fail";
        }
    }
    @RequestMapping(value = "/delete")
    public int delete(@RequestParam(value="id[]") List<Long> ids){
        System.out.println(ids);
        int count = 0;
        for (Long id:ids) {
            if(cuxUsersService.delete(id)>0){
                count++;
            }else{

            }
        }
        return count;
    }
    @RequestMapping(value = "/update")
    public String update(CuxUsers cuxUsers){
        if(cuxUsersService.update(cuxUsers)> 0){
            return "success";
        }else{
            return  "fail";
        }
    }

    @RequestMapping(value = "/selectToTable")
    @ResponseBody
    public JSONObject selectToTable(int page,int limit){
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
        JsonToLayuiTable jsonToLayuiTable = new JsonToLayuiTable();
        jsonToLayuiTable.setCode(0);
        jsonToLayuiTable.setMsg(null);
        jsonToLayuiTable.setCount(new PageInfo(cuxUsersService.selectToTable(page,limit)).getTotal());
        jsonToLayuiTable.setData(cuxUsersService.selectToTable(page,limit));
        return JSONObject.fromObject(jsonToLayuiTable,jsonConfig);
    }
    @RequestMapping(value = "/selectAll")
    @ResponseBody
    public List<Object> selectAll(){
        return cuxUsersService.selectAll();
    }
}
