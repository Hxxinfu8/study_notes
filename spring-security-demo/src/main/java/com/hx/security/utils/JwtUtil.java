package com.hx.security.utils;

import com.alibaba.fastjson.JSONObject;
import com.hx.security.domain.auth.UserAuthority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * token验证处理
 *
 * @author hx
 */
@Component
public class JwtUtil {
    // 令牌自定义标识
    @Value("${jwt.token.header}")
    private String header;

    // 令牌秘钥
    @Value("${jwt.token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${jwt.token.expireTime}")
    private long expireTime;

    private final RedisUtil redisUtil;
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    private static final String CLAIM_KEY_USER_ID = "user_id";
    private static final String CLAIM_KEY_AUTHORITIES = "authorities";

    private static final Long MILLIS_MINUTE_TEN = 10 * 60 * 1000L;

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public JwtUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 获取请求token
     *
     * @param request request
     * @return token
     */
    public String getToken(HttpServletRequest request) {
        return request.getHeader(header);
    }

    public String generateAccessToken(UserAuthority userAuthority) {
        Map<String, Object> claims = new HashMap<>();
        refreshToken(userAuthority);
        claims.put(CLAIM_KEY_USER_ID, userAuthority.getUser().getId());
        claims.put(CLAIM_KEY_AUTHORITIES, userAuthority.getAuthorities());
        return generateAccessToken(userAuthority.getUsername(), claims);
    }

    public UserAuthority getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (StringUtils.isNotBlank(token)) {
            Claims claims = getClaimsFromToken(token);
            Integer key = (Integer) claims.get(CLAIM_KEY_USER_ID);
            return JSONObject.parseObject(redisUtil.getCacheObject(LOGIN_TOKEN_KEY + key), UserAuthority.class);
        }
        return null;
    }

    public void deleteLoginUser(UserAuthority userAuthority) {
        redisUtil.deleteCacheObject(LOGIN_TOKEN_KEY + userAuthority.getUser().getId());
    }

    public void verifyToken(UserAuthority userAuthority) {
        if (new Date().getTime() - userAuthority.getExpireTime() > MILLIS_MINUTE_TEN) {
            refreshToken(userAuthority);
        }
    }

    private String generateAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims);
    }

    private String generateToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    /**
     * 刷新令牌有效期
     *
     * @param userAuthority 登录信息
     */
    public void refreshToken(UserAuthority userAuthority) {
        long now = System.currentTimeMillis();
        userAuthority.setLoginTime(now);
        userAuthority.setExpireTime(now + expireTime * 60 * 1000);

        redisUtil.setCacheObject(LOGIN_TOKEN_KEY + userAuthority.getUser().getId(), JSONObject.toJSONString(userAuthority), expireTime);
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}
