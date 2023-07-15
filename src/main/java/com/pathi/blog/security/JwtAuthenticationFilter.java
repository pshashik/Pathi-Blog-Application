package com.pathi.blog.security;

import com.pathi.blog.exception.BlogAPIException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                   UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Get Token from request
        String token = getTokenFromRequest(request);
        //Validate Token
        if (StringUtils.hasText(token) && jwtTokenProvider.isValidateToken(token)) {
            String username = jwtTokenProvider.getUsername(token);
            //Get user details from UserDetails
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            //Create UsernamePasswordAuthenticationToken object by passing obtained user details
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            //UsernamePasswordAuthenticationToken object set details with WebAuthenticationDetailsSource has buildDetails method
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //Set Security Context holder with UsernamePasswordAuthenticationToken object
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        //finally call filter doFilter method with request and response

        filterChain.doFilter(request, response);

    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
