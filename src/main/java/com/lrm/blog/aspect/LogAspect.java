package com.lrm.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //execution() - 声明它是一个切面，来规定拦截哪些类
    //web的所有类
    @Pointcut("execution(* com.lrm.blog.web.*.*(..))")
    public void log(){

    }
    //在切面之前执行
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //获取HTTP request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest(); //request for attributes
        String url = request.getRequestURL().toString();  //需要toString 一下
        String ip = request.getRemoteAddr();
        //joinpoint
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("Request : {}",requestLog);
    }

    //在切面之后执行
    @After("log()")
    public void doAfter(){

        logger.info("-----doAfter-----");
    }

    //在返回之后拦截
    @AfterReturning(returning = "result", pointcut = "log()") // 定义result这个参数作为返回值
    public void doAfterReturn(Object result){
        logger.info("Result : " + result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;  //对象数组

        //全参构造
        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        //toString - 转换成字符串
        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
