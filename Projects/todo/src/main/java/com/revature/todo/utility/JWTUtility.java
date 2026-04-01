package com.revature.todo.utility;

import com.revature.todo.entity.User;
import com.revature.todo.exception.AuthFail;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class JWTUtility {

    private static final String SECRET = "my-super-secret-key-which-should-be-long-enough";
	private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
	private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000; // 24 hours

	public static String generateToken(User user) {
                return Jwts.builder()
                        .subject(user.getUsername())
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                        .signWith(KEY)
                        .compact();
	}

	public static String validateToken(String token) throws AuthFail {
	        if (!isTokenExpired(token)){
                        return getSubject(token);
                }
                throw new AuthFail("Token Expired: please log in again");
	}

	private static String getSubject(String token) {
        return Jwts.parser()
                // set the secret key we need to use to decrypt the token
                .verifyWith((SecretKey)KEY)
                // actually create the parser
                .build()
                // decrypt the jwt
                .parseSignedClaims(token)
                // get the data from the jwt
                .getPayload()
                // get the subject from the jwt data
                .getSubject();
	}   

	private static boolean isTokenExpired(String token) {
                Date expiration = Jwts.parser()
                        .verifyWith((SecretKey)KEY)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getExpiration();
                return expiration.before(new Date());
	}

        
        // Main method for generating a dummy JWT for testing purposes
        public static void main(String[] args) {
                // Create a dummy user
                User dummyUser = new User();
                dummyUser.setUsername("testuser");
                String token = generateToken(dummyUser);
                System.out.println("Generated dummy JWT token:\n" + token);
        }
}
