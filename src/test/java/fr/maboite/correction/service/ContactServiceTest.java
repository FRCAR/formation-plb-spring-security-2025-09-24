package fr.maboite.correction.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @Test
    public void testDoSomethingWithoutRole() {
        Assertions.assertThrows(AuthenticationCredentialsNotFoundException.class,
                () -> contactService.doSomething());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDoSomethingWithRoleAdmin() {
        contactService.doSomething();
    }

}
