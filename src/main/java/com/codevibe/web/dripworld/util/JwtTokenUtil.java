package com.codevibe.web.dripworld.util;

import com.codevibe.web.dripworld.entities.UsersEntity;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    //    private static final String CLAIM_KEY_USERTYPE = "usertype";
    private static final String ISSUER = "www.codevibe.lk";
    private static final String SECRET = Env.get("jwt.secret");
    private static final long ACCESS_TOKEN_LIFE = Long.parseLong(Env.get("jwt.accessTokenLife"));
    private static final long REFRESH_TOKEN_LIFE = Long.parseLong(Env.get("jwt.refreshTokenLife"));

    private String generateToken(Map<String, String> claims, Long expiration, String subject) {
        // Build an HMAC signer using an SHA-256 hash
        Signer signer = HMACSigner.newSHA256Signer(SECRET);

        // Build a new JWT with an issuer(iss), issued at(iat), subject(sub) and expiration(exp)
        JWT jwt = new JWT().setIssuer(ISSUER)
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setSubject(subject)
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(expiration));

        claims.keySet().forEach(k -> {
            if (claims.get(k) != null) {
                jwt.addClaim(k, claims.get(k));
            }
        });

        return JWT.getEncoder().encode(jwt, signer);

    }

    private Map<String, String> getClaimFromToken(String token) {
        // Build an HMC verifier using the same secret that was used to sign the JWT
        Verifier verifier = HMACVerifier.newVerifier(SECRET);

        // Verify and decode the encoded string JWT to a rich object
        JWT jwt = JWT.getDecoder().decode(token, verifier);

        Map<String, String> claims = new HashMap<>();
        if (jwt != null) {
            jwt.getAllClaims().forEach((k, v) -> {
                claims.put(k, v.toString());
            });
        }

        return claims;

    }

    public String generateAccessToken(UsersEntity user) {
        final Map<String, String> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, user.getEmail());
        claims.put(CLAIM_KEY_CREATED, new Date().toString());
        return generateToken(claims, ACCESS_TOKEN_LIFE, user.getEmail());
    }

    public String generateRefreshToken(UsersEntity user) {
        final Map<String, String> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, user.getEmail());
        claims.put(CLAIM_KEY_CREATED, new Date().toString());
        return generateToken(claims, REFRESH_TOKEN_LIFE, user.getEmail());
    }

    public String getUsernameFromToken(String token) {
        Map<String, String> claims = getClaimFromToken(token);
        return claims.get(CLAIM_KEY_USERNAME);

    }

    public Date getExpireDateFromToken(String token) {
        // Build an HMC verifier using the same secret that was used to sign the JWT
        Verifier verifier = HMACVerifier.newVerifier(SECRET);

        // Verify and decode the encoded string JWT to a rich object
        JWT jwt = JWT.getDecoder().decode(token, verifier);

        return new Date(jwt.expiration.toInstant().toEpochMilli());

    }

    public boolean isTokenExpired(String token) {
        Date expireDate = getExpireDateFromToken(token);
        return expireDate.before(new Date(System.currentTimeMillis()));
    }

    public boolean isValidToken(String token,UsersEntity user) {
        String username = getUsernameFromToken(token);
        return username.equals(user.getEmail()) && !isTokenExpired(token);
    }

}
