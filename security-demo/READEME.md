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
    DeferredResult

spring security 核心功能
    认证
    授权
    防护攻击(身份伪造)



spring security
    1 基本原理
        过滤器链
            请求
            是否有username和password信息
                有 -> 认证(UsernamePasswordAuthenticationFilter,BasicAuthenticationFilter ...)  -> FilterSecurityInterceptor -> 是否有认证成功的信息
                                                                                                                                 有  -> 目标资源

                                                                                                                                 无  -> 抛出异常  -> ExceptionTranslationFilter  -> 跳转到当前配置的认证方式对应的登录页


                无 -> FilterSecurityInterceptor -> 是否有认证成功的信息
                                                    有  -> 目标资源

                                                    无  -> 抛出异常  -> ExceptionTranslationFilter  -> 跳转到当前配置的认证方式对应的登录页

    2 自定义用户认证逻辑
        实现UserDetailsService接口,加@Component,实现loadUserByUsername方法,返回UserDetails接口的实例对象
        其中 UserDetails的实现类 可以自定义,springSecurity提供默认实现org.springframework.security.core.userdetails.User

        密码加密解密
            定义类实现PasswordEncoder接口  springSecurity提供的默认实现:BCryptPasswordEncoder
            其中 encode方法实现加密逻辑,密码入库前调用encode加密; matches方法判断密码是否匹配,由springSecurity调用


    3 自定义登录页面
        .loginPage("/sign.html")
        .loginProcessingUrl("/authenticate/form")

        处理不同类型的请求 html,json
            所有需要认证的请求都跳转到自定义 Controller[ @RequestMapping("/authenticate/require")] 在controller内判断引发跳转的请求类型
                html页面认证页面请求 -> 客户端指定的页面(客户端未指定跳到默认页面)
                json数据型请求 -> 返回401(未授权) 让客户端自行引导用户进行认证

    4 自定义登录成功和失败处理逻辑 返回格式支持配置
        实现AuthenticationSuccessHandler, @Component
        指定自定义成功处理器: .successHandler(successHandler)

        实现AuthenticationFailureHandler, @Component
        指定自定义失败处理器: .failureHandler(failureHandler)

    5 认证原理源码解析
        认证流程
        认证结果在多个请求间共享 SecurityContextPersistenceFilter
            请求进入: 检查Session中是否有SecurityContext认证信息 有: -> 从Session中取出SecurityContext存到ThreadLoacal中
            请求离开: 检查ThreadLoacal中是否有SecurityContext认证信息 有: -> 从ThreadLoacal中取出SecurityContext存到Session中

        获取认证用户的信息
            public Object getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {return userDetails;}

            public Object getCurrentUser(Authentication authentication) {return authentication;}

            public Object getCurrentUser() {return SecurityContextHolder.getContext().getAuthentication();}

    6 图形验证码
        生成随机数 -> 随机数存到session -> 输出随机数图片

        验证验证码: 增加一个Filter在UserNamePasswordFilter之前 验证验证码是否正确
            自定义过滤器: ValidateCodeFilter extends OncePerRequestFilter
            添加到指定位置: .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)

    7 remeber me
        RememberMeAuthenticationFilter RememberMeServices

    8 短信验证码登录



















