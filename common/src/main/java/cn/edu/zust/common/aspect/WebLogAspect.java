package cn.edu.zust.common.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志处理类
 * Created by Jianguo.Yin on 2021-06-23.
 */
// @Aspect
// @Component
public class WebLogAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * cn.edu.zust.*.controller 即可切入所有服务
     */
    @Pointcut("execution(public * cn.edu.zust.*.controller.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){ // JoinPoint能够获得被切方法的类名和方法名
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMthod = joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        LOGGER.info("URL : {}", url);
        LOGGER.info("IP : {}", ip);
        LOGGER.info("CLASS_METHOD : {}", classMthod);
        LOGGER.info("ARGS : {}", args);

    }

    @AfterReturning(returning = "returnValue", pointcut = "webLog()")
    public void doAfterReturn(Object returnValue){
        LOGGER.info("ReturnValue : {}", returnValue);
        LOGGER.info("SPEND_TIME : {}", (System.currentTimeMillis() - startTime.get() + " ms"));
        startTime.remove();
    }
}
