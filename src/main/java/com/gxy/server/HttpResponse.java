package com.gxy.server;
//封装了服务器返回给客户端的数据
public class HttpResponse {
    private StringBuffer stringBuffer = new StringBuffer();
    public void print(String s) {
        stringBuffer.append(s);
    }
    public String AckMsg(){
        return stringBuffer.toString();
    }
}
