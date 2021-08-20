# framework-parent

## core

### aspect

使用切面方式拦截请求日志
1. 使用execution方式通过包名拦截
2. 使用@annotation方式通过注解拦截，但是该方式**对于实现接口的Controller类无法拦截**

日志单独打印？

### interceptor拦截器

通过拦截器获取自定义注解（@Delete）的接口

### 统一异常处理

自定义注解优先级？