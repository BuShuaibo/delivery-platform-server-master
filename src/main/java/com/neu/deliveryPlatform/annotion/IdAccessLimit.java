package com.neu.deliveryPlatform.annotion;

import java.lang.annotation.*;

/**
 * @author JiuYou
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IdAccessLimit {

    /**
     * 限流key
     */
    String key() default "limiter:";

    /**
     * 指定时间
     */
    int seconds() default 60;

    /**
     * 指定时间内的访问次数
     */
    int maxCount() default 30;
}

