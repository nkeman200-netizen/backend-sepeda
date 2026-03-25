package com.kampus.sepedaweb.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Kunci Rahasia Server (Minimal 256-bit / 32 Karakter). Tidak boleh bocor ke publik!
    private final String SECRET = "PoliteknikNegeriCilacapSecretKeyYangSangatPanjangSekali12345";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
    
    // Masa berlaku token: 1 Jam (3.600.000 milidetik)
    private final int jwtExpirationMs = 3600000; 

    // Method untuk merakit dan mengenkripsi Token
    public String generateToken(String username, Integer userId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("idUser", userId) // Kita titipkan ID User di dalam token
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

    public String  extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public Integer  extractUserId(String token){
        return (Integer ) extractAllClaims(token).get("idUser");
    }
    
    public boolean validateToken(String token){
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}