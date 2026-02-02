package com.raf.sport_user_service.security;
import com.raf.sport_user_service.security.service.TokenService;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;
import java.util.Arrays;


@Aspect
@Configuration
public class SecurityAspect {
    private TokenService tokenService;

    public SecurityAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @Around("@annotation(com.raf.sport_user_service.security.CheckSecurity)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        // 1. Uzimamo potpis metode
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        // 2. Proveravamo da li metoda ima parametar koji se zove "authorization"
        String token = null;
        for (int i = 0; i < methodSignature.getParameterNames().length; i++) {
            if (methodSignature.getParameterNames()[i].equals("authorization")) {
                // Proveravamo da li je Bearer token
                if (joinPoint.getArgs()[i].toString().startsWith("Bearer ")) {
                    // Izdvajamo čist token (bez "Bearer ")
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
                }
            }
        }

        // 3. Ako nismo našli token, vraćamo 401 Unauthorized
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // 4. Parsiramo token
        Claims claims = tokenService.parseToken(token);

        // Ako je token nevalidan ili istekao
        if (claims == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // 5. Proveravamo ULOGE
        CheckSecurity checkSecurity = method.getAnnotation(CheckSecurity.class);
        String role = claims.get("role", String.class);

        // Ako uloga iz tokena postoji u listi dozvoljenih uloga iz anotacije
        if (Arrays.asList(checkSecurity.roles()).contains(role)) {
            return joinPoint.proceed(); // Pusti metodu da se izvrši
        }

        // 6. Ako nema dozvolu, vraćamo 403 Forbidden
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
