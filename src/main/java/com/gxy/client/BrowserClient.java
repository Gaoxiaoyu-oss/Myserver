package com.gxy.client;

import com.gxy.utils.DateForm;
import com.gxy.utils.TomcatConstants;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class BrowserClient {
    public static void main(String[] args) throws IOException {
        //客户端开始连接服务器
        Socket client = new Socket("127.0.0.1", 8090);
        System.out.println(client.getPort());
        //开始向服务器发送消息
        new BrowserClientSendMsgThread(client).start();
        //接收来自服务器的回复消息
        new BrowserClientReceiveMsgThread(client).start();
    }
}
