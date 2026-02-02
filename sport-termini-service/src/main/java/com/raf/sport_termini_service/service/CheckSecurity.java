package com.raf.sport_termini_service.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckSecurity {
    // Ovde definišem koje uloge su dozvoljene (npr. ["ROLE_ADMIN", "ROLE_TRAINER"])
    String[] roles() default {};
}
