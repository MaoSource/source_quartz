package com.source.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/10/12/14:44
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CronTag {
    @AliasFor(value = "value",annotation = Component.class)
    String value() default "";
}
