package com.example.security.config;

import com.example.security.auth.filters.AuthenticationFilter;
//import com.example.demo.auth.JwtAuthenticationEntryPoint;
//import com.example.demo.auth.CustomAccessDecisionVoter;
//import com.example.demo.auth.CustomPermissionEvaluator;
import com.example.security.auth.filters.JwtRequestFilter;
import com.example.security.auth.utils.JwtUtils;
import com.example.security.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
//@EnableAspectJAutoProxy
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final TokenProvider tokenProvider;
//    private final CorsFilter corsFilter;
//    private final JwtAuthenticationEntryPoint authenticationErrorHandler;


    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    /*@Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    };*/


//    private final CustomPermissionEvaluator permissionEvaluator;

//    @Autowired
//    AuthenticationFilter authenticationFilter;

//    @Autowired
//    AuthorizationFilter authorizationFilter;

    /*public SecurityConfig(CustomPermissionEvaluator permissionEvaluator) {
        this.permissionEvaluator = permissionEvaluator;
    }*/
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;



    /*public SecurityConfig(TokenProvider tokenProvider, CorsFilter corsFilter, JwtAuthenticationEntryPoint authenticationErrorHandler, JwtAccessDeniedHandler jwtAccessDeniedHandler, JwtAccessDeniedHandler jwtAccessDeniedHandler1) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.authenticationErrorHandler = authenticationErrorHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler1;
    }*/





    // Configure BCrypt password encoder =====================================================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*@Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                new WebExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter(),
                new CustomAccessDecisionVoter());
        return new UnanimousBased(decisionVoters);
    }*/

    /*@Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator);
        return handler;
    }*/


   /* @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }*/


    // Configure paths and requests that should be ignored by Spring Security ================================





    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authenticationProvider(authenticationProvider());

        // we don't need CSRF because our token is invulnerable
        http.cors().and().csrf().disable();

        // bypass all endpoints
//        http .authorizeRequests().antMatchers("/**").permitAll();

        // dont authenticate this particular request
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/user/register","/api/user/authenticate").permitAll();

        // all endpoints are secured.
        http.authorizeRequests().antMatchers("/api/**").authenticated();

//        http.authorizeRequests().accessDecisionManager(accessDecisionManager());

//        http.addFilter(new AuthenticationFilter(authenticationManager()));
//        http.addFilter(new AuthorizationFilter(authenticationManager()));

        // exception handling
//        http.exceptionHandling().authenticationEntryPoint(authenticationErrorHandler);

        // create no session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new AuthenticationFilter(authenticationManager(),jwtUtils), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new JwtRequestFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class);




//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

//                .exceptionHandling()
//                .authenticationEntryPoint(authenticationErrorHandler)
//                .accessDeniedHandler(jwtAccessDeniedHandler)

                // enable h2-console
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()

                // create no session
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/authenticate").permitAll()
                // .antMatchers("/api/register").permitAll()
                // .antMatchers("/api/activate").permitAll()
                // .antMatchers("/api/account/reset-password/init").permitAll()
                // .antMatchers("/api/account/reset-password/finish").permitAll()

//                .antMatchers(HttpMethod.PUT,"/api/user").hasAuthority("ROLE_USER")
//                .antMatchers("/api/hiddenmessage").hasAuthority("ROLE_ADMIN")

//                .anyRequest().authenticated()

//                .and()
//                .apply(securityConfigurerAdapter());








        // Enable CORS and disable CSRF
//        http = http.cors().and().csrf().disable();

        // Set session management to stateless
//        http = http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and();

        // Set unauthorized requests exception handler
//        http = http
//                .exceptionHandling()
//                .authenticationEntryPoint(
//                        (request, response, ex) -> {
//                            response.sendError(
//                                    HttpServletResponse.SC_UNAUTHORIZED,
//                                    ex.getMessage()
//                            );
//                        }
//                )
//                .and();

        // Set permissions on endpoints
//        http.authorizeRequests()
                // Our public endpoints
//                .antMatchers("/api/public/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/author/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/author/search").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/book/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/book/search").permitAll()
//                .antMatchers("/").permitAll()
                // Our private endpoints
//                .anyRequest().authenticated();

//        http
//                .httpBasic()
//                .and()

//                .authorizeRequests().antMatchers("/").permitAll()
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
//                .antMatchers("/api/**").permitAll()
//                .hasRole("USER")
//                .antMatchers("/**").permitAll()
//                .anyRequest().authenticated()
//                .antMatchers("/").permitAll()
//                .and().addFilter(authenticationFilter())
//                .and()
//                .formLogin()
//                    .loginPage("/login")
//                    .failureUrl("/login?error=true")
//                .and().addFilter(new AuthenticationFilter(authenticationManager()))
//                .addFilter(new AuthorizationFilter(authenticationManager()))
//                .and().cors().and().csrf().disable();

        /*http.cors().and().csrf().disable();
                http.antMatcher("/api/**").authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilterBefore(new AuthenticationFilter(authenticationManager()))
                .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/


    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**");
//                .antMatchers(HttpMethod.POST, "/api/user/register")
//                .antMatchers(HttpMethod.POST, "/api/user/authenticate");

                // allow anonymous resource requests
//                .antMatchers(
//                        "/",
//                        "/*.html",
//                        "/favicon.ico",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js"
//                );
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

//    private JWTConfigurer securityConfigurerAdapter() {
//        return new JWTConfigurer(tokenProvider);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
