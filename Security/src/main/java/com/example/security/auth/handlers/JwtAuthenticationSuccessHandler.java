package com.example.security.auth.handlers;


/*public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

//        JwtUserDto jwtUserDto = JwtUserDto.buildFromAuthentication(authentication);

//        boolean rememberMe = (loginDto.isRememberMe() == null) ? false : loginDto.isRememberMe();
//        String jwt = tokenProvider.createToken(authentication, rememberMe);


        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().append(generateToken(authentication)).flush();

    }





}*/
