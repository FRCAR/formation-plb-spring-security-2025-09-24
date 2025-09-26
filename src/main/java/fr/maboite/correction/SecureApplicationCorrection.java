package fr.maboite.correction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import fr.maboite.correction.service.ServiceA;
import fr.maboite.correction.service.ServiceB;
import fr.maboite.correction.service.ServiceC;
import fr.maboite.correction.service.ServiceD;

@SpringBootApplication
public class SecureApplicationCorrection {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SecureApplicationCorrection.class, args);
		applicationContext.getBean(ServiceA.class).affichage();
		applicationContext.getBean(ServiceA.class).getServiceB().affichage();
		applicationContext.getBean(ServiceB.class).affichage();
		applicationContext.getBean(ServiceC.class).affichage();
		applicationContext.getBean(ServiceD.class).affichage();
	}

}
