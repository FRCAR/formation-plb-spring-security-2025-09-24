package fr.maboite.correction.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.maboite.correction.service.ContactService;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;


	@GetMapping({ "/contact.html" })
	public String showContact() {
        this.contactService.doSomething();
		return "vue-contact";
	}

}