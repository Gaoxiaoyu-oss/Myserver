package com.gxy.server;

import com.gxy.utils.DateForm;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Date;
import java.util.List;

public class TomcatReceiveMsgThread extends Thread{
    private Socket client;
    private int port;
    private boolean TomcatreceiveFlag = true;
    public TomcatReceiveMsgThread(Socket client){
        this.client = client;
        port = client.getPort();
    }
    @Override
    public void run(){
        while (TomcatreceiveFlag){
            //接收客户端发来的消息
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(client.getInputStream(),"utf-8"));
                String queryString = reader.readLine();
                System.out.println(DateForm.getDateStr(new Date())+" 收到来自客户端的消息 : "+queryString);
                //请求的地址
                String path = "";
                //针对每次客户端的请求都会创建两个对象：
                HttpRequest httpRequest = new HttpRequest();
                HttpResponse httpResponse = new HttpResponse();
                //开始解析数据  s?wd=dishini&uname=zhangsan
                if(queryString.contains("?")){
                    //按照?进行切分
                    String[] str = queryString.split("\\?");
                    path = str[0];
                    //开始解析请求信息,将请求参数存放入HttpRequest中
                    httpRequest.parseParameter(str[1]);
                }else {
                    path = queryString;
                }
                //通过HttpServletContainer获取path 对应的Servlet对象
                HttpServlet httpServlet = HttpServletContainer.getServlet(path);
                //调用service方法
                httpServlet.service(httpRequest,httpResponse);
                //讲httpResponse中的信息回复给客户端
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"utf-8"));
                bufferedWriter.write(httpResponse.AckMsg()+"\n");
                bufferedWriter.flush();

            } catch (IOException e) {
                System.out.println("客户端"+client.getPort()+"异常退出");
                TomcatreceiveFlag = false;
                //从客户端socket map中移除
                TomcatServer.removeClient(port);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
