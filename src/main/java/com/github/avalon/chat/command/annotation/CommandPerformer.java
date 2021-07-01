package com.github.avalon.chat.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandPerformer {

  String command();

  String permissionMessage() default
      "%red%You don't have enough permissions do perform this command.";

  String errorMessage() default
      "%red%Something went horribly wrong. Please contact server administrator.";

  char[] prefix() default {'/'};
}
