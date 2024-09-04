package com.example.demo.anno;

import com.example.demo.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StateValidation.class})
public @interface State {
    //提供校验失败的信息
    String message() default "State参数的值只能是已发布或者草稿";
    //
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}
