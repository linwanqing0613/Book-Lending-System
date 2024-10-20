package com.example.booklendsystem.config;

import com.example.booklendsystem.service.impl.BookServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtility tokenUtility;
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String phone_number = null;
        String jwtToken = null;
        // 检查请求头中是否包含 Bearer token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7); // 去掉 "Bearer " 前缀
            try {
                logger.info("fiter: "+ jwtToken);
                phone_number = tokenUtility.extractPhoneNumber(jwtToken); // 从 token 中提取用户名
            } catch (ExpiredJwtException e) {
                System.out.println("Token has expired");
            } catch (SignatureException e) {
                System.out.println("Invalid token signature");
            } catch (JwtException e) {
                System.out.println("JWT exception: " + e.getMessage());
            }
        }
        // 如果提取到用户名，则设置 SecurityContext
        if (phone_number != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Authentication  authenticationToken =
                    new UsernamePasswordAuthenticationToken(phone_number, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
