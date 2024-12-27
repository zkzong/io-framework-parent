# io-framework-parent

## core

OkHttp配置类：OkHttpConfig
RestTemplate配置类：RestTemplateConfig
WebClient配置类：WebClientConfig

### aspect

使用切面方式拦截请求日志
1. 使用execution方式通过包名拦截
2. 使用@annotation方式通过注解拦截，但是该方式**对于实现接口的Controller类无法拦截**

日志单独打印？

### interceptor拦截器

通过拦截器获取自定义注解（@Delete）的接口

### Filter过滤器

1. 过滤器(Filter)：最先进入拦截，只能获取到response,request
2. 拦截器(Interceptor)：可以获取到执行的类名，方法名
3. 切片(Aspect)可以获取到参数

### 统一异常处理

GlobalExceptionHandler
ExceptionController

自定义注解优先级？

### apiversion

通过注解修改uri请求路径

Spring Boot 3：https://blog.csdn.net/chujianboy/article/details/136837325
Spring Boot 2：https://chendd.cn/blog/article/1523485391036940289.html

### traceid

- [x] Feign
  - [x] FeignInterceptor
- [ ] HttpClient
- [ ] Okhttp
- [x] RestTemplate
  - [x] RestTemplateTraceIdInterceptor
  - [x] RestTemplateConfig
- [ ] WebClient
- [ ] 多线程
- [ ] 拦截器
- [ ] rpc

### 统一返回结果

ApiResponse
ApiResponseUtil

## oss

**key**是**目录+文件名**

## fastexcel

https://github.com/HowieYuan/easyexcel-encapsulation
https://github.com/pig-mesh/excel-spring-boot-starter

- [ ] 动态列

## todo

- [ ] 多数据源
- [ ] 多租户
- [ ] core添加spring-cloud依赖
- [ ] DeleteInterceptor日志没有traceid
- [ ] 通用加解密