package com.gxy.server;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpServletContainer {

    //存放的都是web.xml配置文件中servlet name 和 value的映射关系 servlet_name - servlet_value
    private static Map<String,String> webMap = new HashMap<>();
    //存放的都是创建好的servlet对象 path - servlet
    private static Map<String,HttpServlet> servletMap = new HashMap<>();

    //只有静态代码块保证配置文件只读取一次
    static {
        //根据请求的path调用不同的Servlet
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(HttpServletContainer.class.getClassLoader().getResourceAsStream("web.xml"));
            //开始使用xpath解析
            List<Node> nodeList = document.selectNodes("/web/servlet");
            //遍历nodeList:
            for (Iterator<Node> iterator = nodeList.iterator();iterator.hasNext();){
                //获取Servlet元素
                Element element = (Element) iterator.next();
                //将配置信息存放到map
                String name = element.element("servlet-name").getText();
                String value = element.element("servlet-value").getText();
                webMap.put(name,value);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

    }

    //获取路径对应的servlet对象
    public static HttpServlet getServlet(String path) throws Exception {

        //首先判断一下servlet map中是否已经存在这个对象，如果不存在就创建一个
        if(!servletMap.containsKey(path)){
            //通过配置文件获取path对应的class name
            if(webMap.containsKey(path)){

                //防止多个客户端同时访问造成创建多个对象的问题
                synchronized (HttpServletContainer.class){
                    if(!servletMap.containsKey(path)){
                        //开始获取className
                        String className = webMap.get(path);
                        //通过反射创建对象
                        Class clazz = Class.forName(className);
                        Constructor constructor = clazz.getConstructor();
                        HttpServlet httpServlet = (HttpServlet) constructor.newInstance();
                        //将该对象放入servlet map中
                        servletMap.put(path,httpServlet);
                        return httpServlet;
                    }
                }

            }else{
                throw new Exception("["+path+"] 您输入的path映射不到相应的Servlet");
            }
        }
        return servletMap.get(path);


    }

}
