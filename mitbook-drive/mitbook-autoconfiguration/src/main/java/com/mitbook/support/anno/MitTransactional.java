package com.mitbook.support.anno;

import com.mitbook.support.enumeration.TransactionalType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事务注解
 *
 * @author pengzhengfa
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MitTransactional {
    
    TransactionalType transType();
}
