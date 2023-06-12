# Myserver
手写tomcat服务器
使用网络编程，多线程，xml解析......
实现简单的Tomcat服务器容器的功能


具体来说，为了具备Tomacat的基础功能，首先要创建以下类：
  1.HttpRequest：
    a.在该类中，用一个Map<String,String>来存储浏览器发来的请求消息中参数的键和值，比如：http://127.0.0.1:8090/user?name=zhangsan&age=20。 这里的name - zhangsan 以及age -       20 要存储在Map中。
    b.同时,也要提供方法，该方法可以通过某key获取map的value。 以及另一个方法，将字符串name=zhangsan&age=20进行解析，存储在Map中.
    
  2.HttpReponse:
    a.在该类中要维护一个可以动态扩增的StringBuffer对象，因为HttpRespons就是用来向浏览器返回信息的，所以需要返回的信息都应该放在这个StringBuffer对象中进行返回。
    
  3.HttpServlet抽象类：
    该类有一个抽象方法，service(HttpRequest request, HttpResponse response)。该方法有具体的类来实现，比如UserServlet，该类可以模拟简单的登录功能。
    另外，还需要可以通过该类获取到一个具体的HttpServlet，比如http://127.0.0.1:8090/user?name=zhangsan&age=20 中，/user就代表了要访问UserServlet里面的服务。
    那么该类就需要根据对请求地址的解析来返回具体的HttpServlet类。
    具体的做法可以通过解析web.xml文件来将servelt-name 和 servlet-value存储到map中，然后根据传入的请求地址，在map中获取servlet-value,然后利用反射创建具体的servlet对象     再返回。

  4.接收客户端请求的线程以及向客户端返回信息的线程：
