package ra.edu.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expired}")
    private long expired;
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                secretKey.getBytes()
        );
    }


    /**
     * 1. CREATE TOKEN
     */

    // có 2 loại token phổ biến trong thực tê
    // accessToken : JWT : 15p -> hết hạn sau 15p (Inmemmory DB - RAM)

    // tự động gọi refreshToken để xin cấp lại accessToken mới
    // RefreshToken : UUID / uniqueString => xin cấp lại accessToken : 90 ngày
    // RefreshToken lưu trong DB -> ngày hết hạn -> trạng thái (Inactive)
    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(
                        new Date(System.currentTimeMillis() + expired)
                )
                .signWith(
                        getSigningKey()
                )
                .compact();
    }



    /**
     * 2. VALIDATE TOKEN
     */
    public boolean validateToken(String token) {

        try {

            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);


            return true;

        } catch (Exception e) {

            return false;
        }
    }




    /**
     * 3. GET USERNAME FROM TOKEN
     */
    public String extractUsername(String token) {


        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();


        return claims.getSubject();
    }
}
