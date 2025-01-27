package com.jjla2425.da.unit5.sellerappspring;

import com.jjla2425.da.unit5.sellerappspring.model.daos.ISellersDAO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Autowired
    private ISellersDAO sellersDAO;
    @Bean
    public UserDetailsService userDetailsService() {
        return cif -> {
            SellersEntity seller = sellersDAO.findByCif(cif)
                    .orElseThrow(() -> new UsernameNotFoundException("Seller cif not found"));

            return User.withUsername(seller.getCif())
                    .password(seller.getPassword())
                    .roles("SELLER")
                    //.passwordEncoder(passwordEncoder::encode)
                    // comment this line if password is stored encoded
                    .build();
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return DigestUtils.md5Hex(rawPassword.toString()).toUpperCase();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }
        };
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //.requiresChannel( channel -> channel.anyRequest().requiresSecure() )
                .authorizeHttpRequests(auth -> auth
                        // free access to the REST API
                        .requestMatchers("/api-rest/**").permitAll()
                        // free access to the styles.css file
                        .requestMatchers("/*.css").permitAll()
                        // free access to login page
                        .requestMatchers("/login").permitAll()
                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/viewseller", true)
                        .permitAll()
                )
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                        .logoutSuccessHandler((request, response, authentication) -> {
                            String contextPath = request.getContextPath();
                            response.sendRedirect(contextPath + "/login?logout");
                        })
                )
                .httpBasic(withDefaults());

        return http.build();
    }
}
