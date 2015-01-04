Hi, all. This is a very very simple and not enough strong URL-Router
, we all know that in php or python, we often use a web framework as
route dispatcher. In java, we also can use this feature by use Spring
Framework. But Sping is so heavy. So I create a very very light URL-Router
. It is only 10k and only one jar package.

How to use it:
--------------------------------------
Add it to your web project, /WBE-INF/lib/this-package. And, add
```
<listener> 
  <listener-class>com.zhang.listener.RouterListener</listener-class> 
</listener> 
       
<servlet>
  <servlet-name>your servlet name</servlet-name>
  <servlet-class>com.zhang.controller.ServletDispacher</servlet-class>
/servlet>
<servlet-mapping>
  <servlet-name>all</servlet-name>
  <url-pattern>/*</url-pattern>
</servlet-mapping>
```

OK, now you can use our dispatcher to do your url-router.

The url pattern will like this:
```
website/your project name/class name/method name/other parameters
```

the class name must same as your controller's name, and it should 
be lowercase, the method name must be same as your controller's 
method's name.

for example:

A controller as below:

```
public class Hello {

  public void sayHello(HttpServletRequest request, HttpServletResponse response)
{
  try {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=utf-8");
    PrintWriter writer = response.getWriter();
    writer.print("Hello");
  } catch (Exception e) {
    //do what?
  }
}
}
```

OK, now you can enter a browser and input `http://localhost:8080/you projectname/hello/sayHello`. it will be return **hello**.


