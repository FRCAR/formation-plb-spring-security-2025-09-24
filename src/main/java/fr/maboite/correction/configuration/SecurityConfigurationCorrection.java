package fr.maboite.correction.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfigurationCorrection {

    private static final int BCRYPT_STRENGTH = 10;

    /**
     * SecurityFilterChain pour les requêtes HTTP
     * émises par un navigateur, et requêtant des pages HTML.
     * 
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(3)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Réutilisation de HttpSecurity fourni par SpringSecurity
        http

                .authorizeHttpRequests(requests -> requests
                        // / et /home peuvent être requêtées par tout le monde
                        .requestMatchers("/", "/home", "/error", "/contact.html", "/logout-done").permitAll()
                        .requestMatchers("/salut.html").authenticated()
                        // Toute autre requête ne peut être émise que par une personne
                        // authentifiée
                        .anyRequest().authenticated())
                .exceptionHandling(c -> c.accessDeniedPage("/denied.html"))

                // la page de login est accessible via /login
                // et est accessible par tout le monde
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                // La page de logout est aussi accessible
                // par tout le monde
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/logout-done")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).permitAll());
        return http.build();
    }

    /**
     * Utile si aucun UserDetailsService n'est présent
     * dans le contexte de Spring
     * 
     * @return
     */
    // @Bean
    public UserDetailsService userDetailsServiceInMemory() {
        // La méthode ci-dessous est dépréciée : il n'est pas conseillé
        // de mettre en dur un login et un mot de passe, mais de
        // récupérer un utilisateur d'une base de données, ou d'un
        // référentiel d'utilisateurs (annuaire LDAP)
        @SuppressWarnings("deprecation")
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .authorities("USER", "ROBERTO")
                .build();
        // Renvoie une implémentation de UserDetailsService
        // qui stocke les utilisateurs en mémoire (ici, un seul utilisateur)
        return new InMemoryUserDetailsManager(user);
    }

    /**
     * PasswordEncoder : sert à hacher les mots de passe
     * 
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(BCRYPT_STRENGTH);
        encoders.put(idForEncode, bCryptPasswordEncoder);
        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }
}
