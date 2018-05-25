打包
    直接打包不包含外部的依赖jar包

    需要在build中引入 spring-boot-maven-plugin

spring boot 自定义检验器 实现特定校验逻辑
    定义校验注解 通过@Constraint的validatedBy 指定校验逻辑的实现类

    创建校验类 implements ConstraintValidator<MyConstraint,Object>(第一个泛型 自定义的校验注解, 第二个泛型 需要校验的类型)

    校验注解必须有 message groups  payload 三个属性

    在需要检验的地方加上检验注解

spring boot 响应格式
    浏览器请求 html

    客户端请求 json

    实现方式: 所有的 /error 请求都由BasicErrorController处理 请求中带有produces = {"text/html"}响应格式为html 否则响应json

spring boot 自定义状态码对应的html页面
    resources下创建 resources/error 目录 在error中以状态码作为页面文件名 如404.html

spring boot 自定义异常处理
    定义异常处理ControllerExceptionHandler类 并加上@ControllerAdvice以捕获所有controller中抛出的exception

    定义不同的方法处理不同类型的exception 通过@ExceptionHandler指定方法处理的是何种类型的exception 方法的返回值直接返回给客户端

spring boot 拦截机制

    1 Filter:
        实现javax.servlet接口,加上@Component

        把没有@Component注解的Filter加入到过滤器链中 此方式比加 @Component的灵活处在于 可以指定要拦截的url
            定义一个config类 加上@Configuration
            定义方法 并通过@Bean返回需要注册的Filter

    Filter是javaee中的servlet规范,只能获取到request和response对象; 不能获取到处理请求具体的controller及方法信息

    2 Interceptor:
        实现HandlerInterceptor, 加上@Component
        定义一个config类 加上@Configuration, 继承WebMvcConfigurerAdapter,重写addInterceptors方法注册拦截器

    Interceptor可以获取到请求的目标controller及方法名称信息,不能获取到目标方法的参数值信息,原因:DispatcherServlet在执行Interceptor中的方法时还没有执行参数绑定操作

    3 Advice:
        切入点    在那些方法上起作用 在什么时候起作用
        增  强    起作用时执行的业务逻辑

        定义切面类 加上@Aspect @Component
        在方法上通过@Around指定那些方法要被切入

    拦截顺序(正常)
        Filter
        Interceptor preHandle
        Aspect
        target
        Aspect
        Interceptor postHandle
        Interceptor afterCompletion
        Filter


    拦截顺序(异常)
        Filter
        Interceptor preHandle
        Aspect
        target
        Aspect throws Throwable exception
        ControllerExceptionHandler
        Interceptor afterCompletion
        Filter

spring boot 文件上传下载


spring boot 多线程异步处理请求
    Callable







