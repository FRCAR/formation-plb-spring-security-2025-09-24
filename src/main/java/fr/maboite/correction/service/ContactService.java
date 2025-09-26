package fr.maboite.correction.service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class ContactService {


    /**
     * Méthode sécurisée : réservée aux seuls 
     * utilisateurs ayant le role ADMIN
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    // équivalent à @Secured("ROLE_ADMIN")
    public int doSomething() {
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(System.out::println);
        return 42;
    }

    /**
     * Méthode sécurisée déclenchant la méthode MyAuthorizationLogic.decide()
     * @param maListe
     * @return
     */
    @PostAuthorize("@myAuthorizationLogic.decide(#root, #l)")
    public int doSomething(@P("l") List<String> maListe) {
        return 42;
    }
}
