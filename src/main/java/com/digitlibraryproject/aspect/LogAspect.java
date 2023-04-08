package com.digitlibraryproject.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;


@Aspect
@Component
public class LogAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
    }

    @Pointcut("within(com.digitlibraryproject..*)" +
            " || within(com.digitlibraryproject.service..*)" +
            " || within(com.digitlibraryproject.controller..*)")
    public void applicationPackagePointcut() {
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()",throwing = "e")
    public void logAfterThrowingMethod(Throwable e) {
        log.info("ОШИБКА 0_0,А КАКАЯ? А ВОТ ТАКАЯ: " + e);
    }
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.info("ОШИБКА 0_0,А ГДЕ? А ВОТ ГДЕ: {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.info("Входит: {}.{}() с аргументами [s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.info("Выходит: {}.{}() с результатом = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Неправильный аргумент: {} в {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }

    @Around("@annotation(com.digitlibraryproject.annotations.CheckTimeAnnotation)")
    public Object CheckMethodLoadTime(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalTime start = LocalTime.now();
        Object proceed = joinPoint.proceed();
        LocalTime end = LocalTime.now();
        log.info("0." + String.valueOf(end.getNano() - start.getNano()) + " seconds");
        return proceed;
    }
}