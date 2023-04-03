package pack1;

import pojo.Photos;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Aspect
public class MyAspect {
    @SneakyThrows
    @Around("@annotation(annotation.Timetest) && args(ph))")
    public void timeAdvice(ProceedingJoinPoint jp, Photos ph) {
        long time = System.currentTimeMillis();
        jp.proceed(new Object[]{ph});
        System.out.println("Finding the largest image has taken " + (System.currentTimeMillis() - time) + " ms");
    }

    @SneakyThrows
    @Around("@annotation(annotation.Timetest) && args(file))")
    public void timeAdviceWithoutParameters(ProceedingJoinPoint jp, File file) {
        long time = System.currentTimeMillis();
        jp.proceed(new Object[]{file});
        System.out.println("Reading json from url has taken " + (System.currentTimeMillis() - time) + " ms");
    }

}
