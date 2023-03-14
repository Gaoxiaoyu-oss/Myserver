package com.gxy.service;

public class UserService {
    //模拟登录逻辑
    public boolean doLogin(String uname,String password){
        if("admin".equals(uname) && "123456".equals(password)){
            System.out.println("登录成功！");
            return true;
        }else{
            System.out.println("登录失败！");
            return false;
        }
    }
}
