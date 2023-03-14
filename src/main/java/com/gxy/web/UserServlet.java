package com.gxy.web;

import com.gxy.server.HttpRequest;
import com.gxy.server.HttpResponse;
import com.gxy.server.HttpServlet;
import com.gxy.service.UserService;

public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();
    @Override
    public void service(HttpRequest requeste, HttpResponse response) {
        System.out.println("UserServlet.service"+System.currentTimeMillis());
        //获取用户输入的用户名密码
        String uname = requeste.getParameter("uname");
        String password = requeste.getParameter("password");
        //打印信息
        System.out.println(uname+"------"+password);
        //调用业务逻辑
        boolean loginSuccess =  this.userService.doLogin(uname,password);
        //讲信息返回给客户端
        response.print(loginSuccess ? "登录成功" : "登录失败");
    }
}
