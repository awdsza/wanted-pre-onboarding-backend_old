package com.wanted.preonboarding.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class JWTTokenUtil {
    private static String SECRET_KEY = "secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret";
    {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }


    private static final Long tokenValidMilliSecond =1000L*60*60*24;

    public static String createToken(String key,String email){
        Claims claim = Jwts.claims().setId(key);
        claim.put("email",email);
        Date current = Calendar.getInstance().getTime();
        Date expired = Calendar.getInstance().getTime();
        expired.setTime(expired.getTime() + tokenValidMilliSecond);
        return Jwts.builder()
                .setSubject("Joe")
                .claim("email",email)
                .setIssuedAt(current)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY.getBytes())
                .compact();
    }
    public static String getTokenType(String token){
        return token.split(" ")[0];
    }
    public static String getTokenValue(String token){
        return token.split(" ")[1];
    }
    public static boolean isExpiredToken(String token){
        return decryptToken(getTokenValue(token)).getExpiration().before(Calendar.getInstance().getTime());
    }
    public static Object getValue(String token, String key){
        return decryptToken(getTokenValue(token)).get(key);
    }
    private static Claims decryptToken(String token){
        return
                Jwts.parser()
                        .setSigningKey(SECRET_KEY.getBytes())
                        .parseClaimsJws(token)
                        .getBody()
                ;
    }
}
