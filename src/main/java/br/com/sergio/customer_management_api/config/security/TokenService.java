package br.com.sergio.customer_management_api.config.security;

import br.com.sergio.customer_management_api.database.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.expiration}")
    private long expiration;

    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("Commerce Management API")
                    .withSubject(user.getId().toString())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Commerce Management API")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }

    }

    private Instant expirationDate() {
        return Instant.now().plusSeconds(expiration);
    }

}