package com.example.security.jwt.app.filter;

import com.example.security.jwt.app.helper.JwtTokenUtil;
import com.example.security.jwt.app.repository.UserRepository;
import com.example.security.jwt.app.service.CustomUserServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserServiceDetails customUserServiceDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        String requestUserToken = null;
        String userName = null;
        UserDetails userDetails = null;

        if (null != requestHeader && requestHeader.startsWith("Bearer")) {
            requestUserToken = requestHeader.substring(7);
            userName = jwtTokenUtil.extractUsername(requestUserToken);
        }

        if(null != userName && SecurityContextHolder.getContext().getAuthentication() == null){
            userDetails = customUserServiceDetails.loadUserByUsername(userName);
            if(jwtTokenUtil.validateToken(requestUserToken, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}