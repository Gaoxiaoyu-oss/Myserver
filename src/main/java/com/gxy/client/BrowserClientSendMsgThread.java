package com.gxy.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class BrowserClientSendMsgThread extends Thread{
    private Socket client ;
    private boolean ClientSendFlag = true;
    public BrowserClientSendMsgThread(Socket client){
        this.client = client;
    }

    @Override
    public void run(){
        while (ClientSendFlag){
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println("请输入向服务器发送的消息: ");
                String msg = scan.next();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"utf-8"));
                writer.write(msg+"\n");
                writer.flush();
            } catch (IOException e) {
                System.out.println("client"+client.getPort()+"与服务器断开连接");
                ClientSendFlag = false;
            }
        }
    }

}
