package fi.plasmonics.inventory.authentication;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter {

    @Autowired
    private JWTUtils jwtUtils;

//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(
//        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
//        throws ServletException, IOException {
//        try {
//            String jwt = parseJwt(httpServletRequest);
//            if (jwtUtils.validateJwtToken(jwt)) {
//                String username = jwtUtils.getUserName(jwt);
//
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        userDetails.getUsername(),
//                        userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            }
//        } catch (Exception e) {
//            logger.error("Cannot set user authentication: {}", e);
//        }
//
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
//    }



    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
