package com.tapajyoti.auth_service.filters;

import com.tapajyoti.auth_service.service.CustomUserDetailsService;
import com.tapajyoti.auth_service.util.Jwtutil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final Jwtutil jwtutil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            // Validate the token and extract the username
            username = jwtutil.extractUsername(token);
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                // TODO fetch user by username
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (jwtutil.validateToken(username, userDetails, token)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            // You can use a JWT library to validate the token and extract user details
        }
        //TODO: Implement token validation and authentication logic here
        // TODO: If the token is valid, set the authentication in the security context


    }
}
