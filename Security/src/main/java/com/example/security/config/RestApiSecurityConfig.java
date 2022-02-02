package com.example.security.config;

import com.example.security.auth.filters.RestApiRequestFilter;
import com.example.security.auth.filters.RestApiAuthenticationFilter;
import com.example.security.auth.handlers.RestApiAuthenticationEntryPoint;
import com.example.security.auth.utils.JwtUtils;
import com.example.security.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

//    @Autowired
//    Jackson2ObjectMapperBuilder mapperBuilder;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*@Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
//            http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
//            http.authorizeRequests().anyRequest().permitAll();
//        http.authorizeRequests().antMatchers("/api/login/**").permitAll();
        http.exceptionHandling().authenticationEntryPoint(new RestApiAuthenticationEntryPoint());
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new RestApiAuthenticationFilter(authenticationManager(), jwtUtils));
        http.addFilterBefore(new RestApiRequestFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class);

            /*// we don't need CSRF because our token is invulnerable
            http.cors().and().csrf().disable();
            // dont authenticate this particular request
            http.authorizeRequests()
                    .antMatchers(HttpMethod.POST,"/api/user/register","/api/user/authenticate").permitAll();
            http.authorizeRequests()
                    .antMatchers(HttpMethod.GET,
                            "/resources/**",
                            "/static/**",
                            "/css/**",
                            "js/**",
                            "/app/",
                            "/app/login",
                            "/app/register")
                    .permitAll();
            // all endpoints are secured.
            http.authorizeRequests().antMatchers("/api/**", "/app/**").authenticated();

            // create no session
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.addFilterBefore(new AuthenticationFilter(authenticationManager(),jwtUtils), UsernamePasswordAuthenticationFilter.class);
            http.addFilterAfter(new JwtRequestFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class);

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/

            /*http
                    .csrf().disable()
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .antMatchers("/api/authenticate").permitAll()
                    .antMatchers("/api/**").hasAnyRole("APIUSER")
                    .and()
                    .addFilterBefore(jwtauthFilter, UsernamePasswordAuthenticationFilter.class);*/
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
