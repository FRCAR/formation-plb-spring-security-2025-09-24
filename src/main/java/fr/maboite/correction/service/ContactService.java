package fr.maboite.correction.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

	@Secured("ROLE_ADMIN")
	public int doSomething() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		System.out.println("Je fais quelque chose");
		return 42;
	}
	
}
