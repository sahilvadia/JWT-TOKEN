package com.company.taskmanager.configuration;


import com.company.taskmanager.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  //configuration
@EnableWebSecurity // go for this configuration
public class SecurityConfig {


    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/user/register","api/v1/user/login")
                        .permitAll()
                        .anyRequest().authenticated())

                // for the webLogin
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
//                .oauth2Login(Customizer.withDefaults()) for oAuth


                // for the client login like postman or other
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    // InMemoryUserDetailsManager implements the UserDetailsManager it extends the MyUserDetailsService.
    // InMemoryUserDetailsManager use the UserDetails(interface)
    // UserDetail is the interface so we need to use the user class that implement the UserDetail
//    @Bean
//    public MyUserDetailsService userDetailsService(){
//
//        return default and hardcoded way
//        UserDetails admin = User
//                .withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User
//                .withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USERS")
//                .build();
//
//
//        return new InMemoryUserDetailsManager(admin,user);
//    }


    // own custom AuthenticationProvider
    //  it is interface so implement it or use other class that extends the interface.
    // DaoAuthenticationProvider it is the class that implement the AuthenticationProvider

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // no password encoder default

        //create own userDetail service but we need to implement on the class because it is interface
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
