package com.whh.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-30 15:11
 **/

/**
 * 切面类
 * @Aspect： 告诉Spring当前类是一个切面类
 */
@Aspect
public class LogAspects {
    //抽取公共点表达式
    @Pointcut("execution(public int com.whh.aop.MathCalculator.*(..))")
    public void pointCut(){};

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println("" + joinPoint.getSignature().getName()
                + "运行...@Before：参数是:{ " + Arrays.asList(args) + " }");

    }
    @After("com.whh.aop.LogAspects.pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println("" + joinPoint.getSignature().getName()+"结束...@After");
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint,Object result){
        System.out.println("" + joinPoint.getSignature().getName() +"正常返回..，@AfterReturning：运行结果：{"
        + result + "}");
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception){
        System.out.println(""+joinPoint.getSignature().getName() + "异常信息是:{"+exception + "}");
    }


}
