package com.example.security.config;

import com.example.security.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.example.security.config.RestApiSecurityConfig.passwordEncoder;


@Configuration
@EnableWebSecurity
@Order(2)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        // ignore GET and POST requests for /login url from spring security.
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/login").permitAll()
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .and()
        // authorize all requests.
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()

        // custom login form.
                .formLogin()
                .loginPage("/login")
//                .loginProcessingUrl("")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .and()

        // setting logout url.
                .logout()
//                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()

        // session management.
                .sessionManagement().maximumSessions(1).expiredUrl("/login?expired=true");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/app/login").permitAll();
        http.authorizeRequests().antMatchers("/app/register").permitAll();
//        http.authorizeRequests().antMatchers("/app/user/register").permitAll();
//        http.authorizeRequests().antMatchers("/app/home").permitAll();
        http.mvcMatcher("/app/**").authorizeRequests().anyRequest().authenticated();
        // custom login form.
        http.formLogin().loginPage("/app/login")
//                .loginProcessingUrl("")
                .defaultSuccessUrl("/app/home")
                .failureUrl("/app/login?error=true");

        // setting logout url.
        http.logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/app/logout"))
//                .logoutUrl("/app/logout")
                .logoutSuccessUrl("/app/login?logout")
                .permitAll();

        // session management.
        http.sessionManagement().maximumSessions(1).expiredUrl("/app/login?expired=true");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**",
                        "/static/**",
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/scss/**",
                        "/vendor/**",
                        "/icon/**");
    }
}
