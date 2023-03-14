package com.gxy.server;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    //存储请求参数的map:
    private Map<String,String> map = new HashMap<>();

    // 根据传入的参数的name返回参数的值
    public String getParameter(String name){
        return map.get(name);
    }

    // 将client发来的请求消息中的参数存放入Map中
    public void parseParameter(String str){
        if(str!=null && str.length()>0){
            String[] groups = str.split("&");
            for (int i = 0; i < groups.length; i++) {
                String group = groups[i];
                if(group!=null && group.length()>0){
                    String[] kv = group.split("=");
                    map.put(kv[0],kv[1]);
                }
            }
        }
    }

}
