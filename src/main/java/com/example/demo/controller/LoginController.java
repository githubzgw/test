package com.example.demo.controller;

import com.example.demo.service.LoginService;
import com.example.demo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Created by Zhao Guowei on 2018/7/21.
 */
@RestController
public class LoginController {
    @Autowired
    LoginService loginService;
    private static Logger log = Logger.getLogger(String.valueOf(LoginController.class));
    @RequestMapping(value = "/Login")
    public String Login(HttpServletRequest request,HttpSession session) throws NoSuchAlgorithmException {
        if(loginService.Login(request.getParameter("username"), MD5Util.GetMD5(request.getParameter("password"))) != null){
            return "success";
        }else{
            return "fail";
        }
    }
    @RequestMapping(value = "/Logout")
    public String Logout(HttpServletRequest request,HttpSession session){
        session.removeAttribute(request.getParameter("username"));
        return  "success";
    }
}
