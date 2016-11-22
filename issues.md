## 开发问题

* 1、Thymeleaf中inline script导致调用问题：
    
元凶：

    <script th:inline="javascript"  type="text/javascript">
        var categories = /*[[${categories}]]*/
    </script>    

会导致调用：    
    
    public Category getCategory() {
         throw new UnsupportedOperationException("请调用getCategoryId"); // 为什么 thymeleaf admin/tags 会调用此方法？
     }

异常：

    java.lang.UnsupportedOperationException: 请调用getCategoryId
    at me.micro.bbs.tag.Tag.getCategory(Tag.java:41) ~[main/:na]
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_92]
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_92]
    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_92]
    at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_92]
    at org.thymeleaf.util.JavaScriptUtils.printObject(JavaScriptUtils.java:360) ~[thymeleaf-2.1.5.RELEASE.jar:2.1.5.RELEASE]
    at org.thymeleaf.util.JavaScriptUtils.print(JavaScriptUtils.java:184) ~[thymeleaf-2.1.5.RELEASE.jar:2.1.5.RELEASE]
    
解决：放弃这种用法~~|||