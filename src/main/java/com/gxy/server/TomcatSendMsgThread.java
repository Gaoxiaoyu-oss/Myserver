package com.gxy.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TomcatSendMsgThread extends Thread{
    private Socket client;

    private int port;
    private boolean TomcatsendFlag = true;
    public TomcatSendMsgThread(Socket client){
        this.client = client;
        port = client.getPort();
    }
    @Override
    public void run(){
        while(TomcatsendFlag){

            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"utf-8"));
                writer.write("消息已收到！"+"\n");
                writer.flush();
            } catch (IOException e) {
                System.out.println("客户端"+client.getPort()+" 异常退出");
                TomcatsendFlag = false;
                //从客户端socket map中移除
                TomcatServer.removeClient(port);
            }

        }
    }
}
