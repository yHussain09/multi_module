package com.example.security.config;

//import com.example.security.auth.filters.RestApiAuthenticationFilter;
//import com.example.security.auth.utils.JwtUtils;
//import com.example.security.domain.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.Arrays;
//
//import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


/**
 * -> Implementation-type 1. Rest APIs should only accessed if auth token is present and valid.
 * <p>
 * => Limitation of this implementation type is, if web application wants to make AJAX calls to Rest API even though browser has valid session it won't allow to access Web-APIs.
 * => Here Rest API is only for stateless access.
 * <p>
 * Description:
 * -> It has multiple http security configuration(two http security configuration)
 * -> where http configuration of @order(1) will authorize only "/api/**" rest of url's will not be considered by this configuration. This http configuration will be configured for stateless. And you should configure an implementation of OncePerRequestFilter(Say JwtAuthFilter) and filter order can be before UsernamePasswordAuthenticationFilter or BasicAuthenticationFilter. But your filter should read the header for auth token, validate it and should create Authentication object and set it to SecurityContext without fail.
 * -> And http configuration of @order(2) will authorize if request is not qualified for first order http configuration. And this configuration does not configures JwtAuthFilter but configures UsernamePasswordAuthenticationFilter(.formLogin() does this for you)
 * <p>
 * -> Implementation-type 2. Rest APIs can be accessed by auth token as well as session.
 * <p>
 * => Here Rest API's can be accessed by any third party applications(cross-origin) by auth token.
 * => Here Rest API's can be accessed in web application(same-origin) through AJAX calls.
 * <p>
 * Description:
 * -> It has only one http security configuration.
 * -> where http configuration will authorize all "/**"
 * -> Here this http configuration is configured for both UsernamePasswordAuthenticationFilter and JwtAuthFilter but JwtAuthFilter should be configured before UsernamePasswordAuthenticationFilter.
 * -> Trick used here is if there is no Authorization header filter chain just continues to UsernamePasswordAuthenticationFilter and attemptAuthentication method of UsernamePasswordAuthenticationFilter will get invoked if there is no valid auth object in SecurityContext. If JwtAuthFilter validates token and sets auth object to SecurityContext then even if filter chain reaches UsernamePasswordAuthenticationFilter attemptAuthentication method will not be invoked as there is already an authentication object set in SecurityContext.
 */


/*@Configuration
@EnableWebSecurity
//@ConditionalOnProperty(name = "app.security.enable", havingValue = "true")
public class SecurityConfig {



    *//*private static CorsFilter getCorsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
        corsConfiguration.setAllowedMethods(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With"
                , "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Jwt-Token", "Authorization", "Access-Control-Allow-Credentials", "Filename"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }*//*

 *//*@Order(1)
    @Configuration
//    @ConditionalOnProperty(name = "app.security.enable", havingValue = "true")
    public static class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {



        *//**//*@Bean
        CorsConfigurationSource corsConfigurationSource() {
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
            source.registerCorsConfiguration("/**", corsConfiguration);
            return source;
        }*//**//*

 *//**//*@Bean
        public CorsFilter crosFilter() {
            return getCorsFilter();
        }*//**//*


    }*//*

 *//*@Order(2)
    @Configuration
//    @ConditionalOnProperty(name = "app.security.enable", havingValue = "true")
    public static class WebAppLoginFormSecurityConfig extends WebSecurityConfigurerAdapter {

        *//**//*@Autowired
        UserService userService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
        }*//**//*

 *//**//*@Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
//            http.cors().and().csrf().disable();
            http.authorizeRequests().antMatchers("/**").permitAll();
            *//**//**//**//*
            // we don't need CSRF because our token is invulnerable
            http.cors().and().csrf().disable();

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
                    .sessionManagement().maximumSessions(1).expiredUrl("/login?expired=true");*//**//**//**//*
        }*//**//*

 *//**//*@Override
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
        }*//**//*

 *//**//*@Bean
        public CorsFilter crosFilter() {
            return getCorsFilter();
        }*//**//*
    }*//*
}*/

/**
 * -> Implementation-type 2. Rest APIs can be accessed by auth token as well as session.
 *
 *    => Here Rest API's can be accessed by any third party applications(cross-origin) by auth token.
 *    => Here Rest API's can be accessed in web application(same-origin) through AJAX calls.
 *
 *    Description:
 *    -> It has only one http security configuration.
 *    -> where http configuration will authorize all "/**"
 *    -> Here this http configuration is configured for both UsernamePasswordAuthenticationFilter and JwtAuthFilter but JwtAuthFilter should be configured before UsernamePasswordAuthenticationFilter.
 *    -> Trick used here is if there is no Authorization header filter chain just continues to UsernamePasswordAuthenticationFilter and attemptAuthentication method of UsernamePasswordAuthenticationFilter will get invoked if there is no valid auth object in SecurityContext. If JwtAuthFilter validates token and sets auth object to SecurityContext then even if filter chain reaches UsernamePasswordAuthenticationFilter attemptAuthentication method will not be invoked as there is already an authentication object set in SecurityContext.
 */

/*@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtauthFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureInMemoryAuthentication(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("admin@123#")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/**").hasAnyRole("APIUSER","ADMIN")
                .antMatchers("/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .addFilterBefore(jwtauthFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().maximumSessions(1).expiredUrl("/login?expired=true");
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}*/
