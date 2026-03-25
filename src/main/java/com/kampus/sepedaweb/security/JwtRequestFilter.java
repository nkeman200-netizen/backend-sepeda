package com.kampus.sepedaweb.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 1. Cari header bernama "Authorization"
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 2. Cek apakah ada token bertipe "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Potong kata "Bearer " untuk ambil token murninya
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                // Token tidak valid atau kedaluwarsa
            }
        }

        // 3. Jika token valid dan user belum terotentikasi di sistem
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(jwt)) {
                // Ekstrak ID dari token untuk disimpan di memori sementara (SecurityContext)
                Integer userId = jwtUtil.extractUserId(jwt);
                
                // Daftarkan user ini sebagai "Sah" untuk request kali ini
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username, userId, new ArrayList<>());
                
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 4. Lanjutkan perjalanan request ke Controller
        chain.doFilter(request, response);
    }
}