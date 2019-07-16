package ro.softvision.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MessageProviderMonitor {

    @Before(value = "PointcutContainer.onProvideMessage()")
    public void loggingAdviceBefore( JoinPoint joinPoint) {
        // real object
        Object target = joinPoint.getTarget();
        // proxy
        Object aThis = joinPoint.getThis();
        System.out.printf("*** Before calling method: %s%n", joinPoint.getSignature().toLongString());
    }

    @After(value = "PointcutContainer.onProvideMessage()")
    public void loggingAdviceAfterFinally(JoinPoint joinPoint){
        System.out.printf("*** After calling method: %s%n", joinPoint.getSignature().toLongString());
    }

    @AfterThrowing(value = "PointcutContainer.onProvideMessage()", throwing = "ex")
    public void loggingAdviceAfterThrowing(JoinPoint joinPoint, RuntimeException ex){
        System.out.printf("*** After Throwing calling method: %s%n", joinPoint.getSignature().toLongString());
        System.out.println("Exception : " + ex);
    }

    @AfterReturning(value = "PointcutContainer.onProvideMessage()", returning = "value")
    public void loggingAfterReturning(JoinPoint joinPoint, String value){
        System.out.printf("*** After Returning calling method: %s%n", joinPoint.getSignature().toLongString());
        System.out.println("Return value : " + value);
    }

    @Around(value = "PointcutContainer.onProvideMessage()")
    public Object loggingAround(ProceedingJoinPoint proceedingJoinPoint){
        System.out.printf("*** Around before calling method: %s%n", proceedingJoinPoint.getSignature().toLongString());
        try {
            Object proceed = proceedingJoinPoint.proceed();
            System.out.println("Around after the method was executed. " + proceed);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

}
