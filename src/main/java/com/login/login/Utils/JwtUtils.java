package com.login.login.Utils;

import com.login.login.Common.AccessDeniedException;
import com.login.login.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static String secret="This is secret";
    private static long expiryDuration = 120*120;
    public String generateJwt(User user){
        long milliTime=System.currentTimeMillis();
        long expiryTime=milliTime + expiryDuration* 1000;

        Date issuedAt = new Date(milliTime);
        Date expiryAt=new Date(expiryTime);


        //claims
        Claims claims= Jwts.claims().setIssuer(user.getUserId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiryAt);

        //optional claims
        claims.put("userId", user.getUserId().toString());
        claims.put("companyNo", user.getCompanyNo());

        claims.put("resourceNo", user.getResourceNo());
        claims.put("roleNo", user.getRoleNo());



        //generate jwt using claims
        return Jwts.builder().setClaims(claims).
                setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
            }

            public Claims verify(String authorization) throws Exception {
        try{
            Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization).getBody();
            return claims;
        }catch(Exception e){
            throw new AccessDeniedException("Access Denied");
        }

            }
}
