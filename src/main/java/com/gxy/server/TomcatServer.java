package com.gxy.server;

import com.gxy.utils.DateForm;
import com.gxy.utils.TomcatConstants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TomcatServer {
    private boolean AcceptAble = true;

    //创建一个容器。保存每个连接客户端的连接socket   clientPort---socket
    private static Map<Integer,Socket> clientMap = new HashMap<>();
    //启动服务器
    public  void start() throws IOException {
        //从配置文件中读取服务器的端口号
        Properties properties = new Properties();
        properties.load(TomcatServer.class.getClassLoader().getResourceAsStream("server.properties"));
        TomcatConstants.serverPort = Integer.parseInt(properties.getProperty("port"));
        System.out.println(TomcatConstants.serverPort);
        //启动一个ServerSocket
        ServerSocket tomcat = new ServerSocket(TomcatConstants.serverPort);
        //打印启动信息
        System.out.println("TomcatServer.main服务器启动成功 ” "+tomcat);

        while(AcceptAble){
            //开始接收客户端的请求
            Socket client = tomcat.accept();
            //将该client连接的socket保存进容器
            clientMap.put(client.getPort(),client);
            System.out.println("客户端连接:"+client.getPort());
            //开启从客户端接收消息的线程
            new TomcatReceiveMsgThread(client).start();
            //哭泣泣向客户端发送消息的线程
            //new TomcatSendMsgThread(client).start();
        }

    }

    // 客户端退出后，清空其socket
    public static void removeClient(int port){
        clientMap.remove(port);
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("java.net.preferIPv4Stack" , "true");
        new TomcatServer().start();
    }
}
