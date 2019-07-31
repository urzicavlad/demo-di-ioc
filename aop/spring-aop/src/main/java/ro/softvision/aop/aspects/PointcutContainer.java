package ro.softvision.aop.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class PointcutContainer {

    /**
     * This Pointcut selects joint points being method executions witch name starts with "provide"
     * in the "ro.softvision.aop.service" package in class which name starts with "Message"
     * having arbitrary number of parameters.
     */
    @Pointcut("@target(org.springframework.stereotype.Service))")
    public void onProvideMessage(){}
}
