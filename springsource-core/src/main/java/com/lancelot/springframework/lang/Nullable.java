package com.lancelot.springframework.lang;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierNickname;
import javax.annotation.meta.When;

/**
 * 一个通用的Spring注解，用于声明在某些情况下，被注释的元素可以是{@code null}
 * 利用JSR 305元注解来指示Java中支持JSR 305的通用工具的可空性，Kotlin使用它来推断Spring API的可空性。
 * 
 * <p> 应该在参数、返回值和字段级别使用。重写的方法应该重复父类{@code @Nullable}注释，除非它们的行为不同。	
 * 
 * <p> 可以与{@code @NonNullApi}或{@code @NonNullFields}联合使用，将默认的非空语义覆盖为空。
 * 
 * @author Lancelot Chen 
 * @date 2020年5月7日 下午3:21:51
 * @Copyright：Lancelot Chen个人所有
 * @version 1.0 
 * @see NonNullApi
 * @see NonNullFields
 * @see NonNull
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Nonnull(when = When.MAYBE)
@TypeQualifierNickname
public @interface Nullable {
}
