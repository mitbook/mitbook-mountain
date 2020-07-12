package com.mitbook.support.anno;
import com.mitbook.support.enumaration.TransactionalTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author pengzhengfa
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MitTransactional {

    TransactionalTypeEnum transType();
}
