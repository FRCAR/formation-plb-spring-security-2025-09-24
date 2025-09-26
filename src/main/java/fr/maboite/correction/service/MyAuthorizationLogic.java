package fr.maboite.correction.service;

import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.stereotype.Component;

/**
 * Contient des méthodes appelables par 
 * des annoations de sécurité avec Spring Expression Language
 */
@Component("myAuthorizationLogic")
public class MyAuthorizationLogic {

    public boolean decide(MethodSecurityExpressionOperations operations, Object filterObject) {
        System.out.println("Je suis la logique d'autorisation custom");
        System.out.println("param 1 : ");
        System.out.println(filterObject);
        System.out.println("returnObject : ");
        System.err.println(operations.getReturnObject());
        return true;
    }

}
