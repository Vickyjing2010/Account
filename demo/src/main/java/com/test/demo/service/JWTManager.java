package com.test.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.google.gson.Gson;
import com.test.demo.model.UserToken;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JWTManager {

    public static final String AUTHORIZATION = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String KEY = "t3t6et42$C&F)J@NcQfTjWnhr52gy";

    //1hour for expiration time
    public static final Long EXPIRATION_TIME = 60*60*1000l;

    public static String createToken(String subject){
        return TOKEN_PREFIX + JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(KEY));
    }

    public static String validateToken(String token) throws Exception {
        try {
            Map<String, Claim> map = JWT.decode(token).getClaims();
            String name = Base64.getDecoder().decode(map.get("name").asString()).toString();
            String cacheToken = JWTCache.getLocalTokenMap().get(name);

            String validatedToken = JWT.require(Algorithm.HMAC512(KEY))
                    .build()
                    .verify(cacheToken.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            refreshToken(cacheToken);
            return validatedToken;
        } catch (TokenExpiredException e){
            throw new Exception("token expired, please login again",e);
        } catch (JWTVerificationException e) {
            throw new Exception("token verify failed！",e);
        }
    }

    public static String verifyToken(String token) throws Exception {
        return JWT.require(Algorithm.HMAC512(KEY))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
    }

    /**
     * if token will expire in 5 min, refresh it in local cache
     * @param token
     * @throws Exception
     */
    public static void refreshToken(String token) throws Exception {
        Map<String, Claim> map = JWT.decode(token).getClaims();
        if (map.get("exp").asLong() * 1000 - System.currentTimeMillis() / 1000 < 5 * 60 * 1000) {
            String name = map.get("name").asString();
            String password = map.get("password").asString();
            String newToken = createToken(new Gson().toJson(new UserToken(name, password)));
            JWTCache.getLocalTokenMap().put(name, createToken(newToken));
        }
    }
}
