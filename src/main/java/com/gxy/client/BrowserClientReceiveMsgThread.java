package com.gxy.client;

import com.gxy.utils.DateForm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class BrowserClientReceiveMsgThread extends Thread{
    private Socket client;
    private boolean ClientReceive = true;
    public BrowserClientReceiveMsgThread(Socket client){
        this.client=client;
    }
    @Override
    public void run(){
        while(ClientReceive){
            //接收来自服务器的回复消息
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(client.getInputStream(),"utf-8"));
                System.out.println(DateForm.getDateStr(new Date())+"  收到来自服务器的回复消息:  "+reader.readLine());
            } catch (IOException e) {
                System.out.println("client"+client.getPort()+"与服务器断开连接");
                ClientReceive = false;
            }

        }
    }
}
