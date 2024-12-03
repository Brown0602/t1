package com.tuaev.task.aspect;

import com.tuaev.task.entity.Task;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(com.tuaev.task.annotation.LogMethod)")
    public void logging() {
    }

    @Before("@annotation(com.tuaev.task.annotation.LogBefore)")
    public void beforeMethodLog(JoinPoint joinPoint) {
        logger.info("Вызван метод {} у класса {} ", joinPoint.getSignature().getName(), joinPoint.getTarget());
    }

    @Around("logging()")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object o;
        try {
            logger.info("Вызван метод {} у класса {} ", joinPoint.getSignature().getName(), joinPoint.getTarget());
            o = joinPoint.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            logger.info("Метод {} у класса {} закончил свою работу", joinPoint.getSignature().getName(), joinPoint.getTarget());
        }
        return o;
    }

    @AfterReturning(pointcut = "@annotation(com.tuaev.task.annotation.ResultHandler)", returning = "task")
    public void resultReturning(Task task) {
        logger.info("Добавлена новая задача:\n{}", task);
    }

    @AfterThrowing(pointcut = "@annotation(com.tuaev.task.annotation.LogException)", throwing = "throwable")
    public void taskException(JoinPoint joinPoint, Throwable throwable) {
        logger.info("Вызвано исключение {} у класса {} метода {}", throwable.getClass().getSimpleName(),
                joinPoint.getTarget(), joinPoint.getSignature().getName());
    }
}
