package fr.maboite.correction.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfigurationCorrection {

    @Bean
    public DefaultSecurityFilterChain configuration(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(c -> c.requestMatchers("/ma-page.html").permitAll()
                        .requestMatchers("/restricted.html").authenticated()
                        .requestMatchers("/rest/voitures").hasAnyAuthority("VOITURE_GERANT")
                        .requestMatchers("/rest/login").permitAll()
                        .requestMatchers("/rest/new").permitAll()
                        .anyRequest().denyAll())
                .formLogin(c -> c.loginPage("/login.html").permitAll())
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/vous-avez-ete-deconnecte.html")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).permitAll())
                .build();
    }

}
