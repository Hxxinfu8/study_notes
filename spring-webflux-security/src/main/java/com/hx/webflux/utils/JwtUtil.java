package com.hx.webflux.utils;

import com.alibaba.fastjson.JSONObject;
import com.hx.webflux.domain.auth.UserLogin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

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

    public String getToken(ServerWebExchange serverWebExchange) {
        return serverWebExchange.getRequest()
                .getHeaders()
                .getFirst(header);
    }

    public String generateAccessToken(UserLogin userLogin) {
        Map<String, Object> claims = new HashMap<>();
        userLogin.setKey(UUID.randomUUID().toString());
        refreshToken(userLogin);
        claims.put(CLAIM_KEY_USER_ID, userLogin.getKey());
        claims.put(CLAIM_KEY_AUTHORITIES, userLogin.getAuthorities());
        return generateAccessToken(userLogin.getUsername(), claims);
    }

    public UserLogin getLoginUser(ServerWebExchange serverWebExchange) {
        String token = getToken(serverWebExchange);
        if (StringUtils.isNotBlank(token)) {
            Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                String key = claims.get(CLAIM_KEY_USER_ID).toString();
                return JSONObject.parseObject(redisUtil.getCacheObject(LOGIN_TOKEN_KEY + key), UserLogin.class);
            }
        }
        return null;
    }

    /**
     * 刷新令牌有效期
     *
     * @param userLogin 登录信息
     */
    public void refreshToken(UserLogin userLogin) {
        long now = System.currentTimeMillis();
        userLogin.setLoginTime(now);
        userLogin.setExpireTime(now + expireTime * 60 * 1000);

        redisUtil.setCacheObject(LOGIN_TOKEN_KEY + userLogin.getKey(), JSONObject.toJSONString(userLogin), expireTime);
    }

    public void deleteLoginUser(UserLogin userLogin) {
        if (userLogin.getUser() != null) {
            redisUtil.deleteCacheObject(LOGIN_TOKEN_KEY + userLogin.getKey());
        }

    }

    public void verifyToken(UserLogin userLogin) {
        if (new Date().getTime() - userLogin.getExpireTime() > MILLIS_MINUTE_TEN) {
            refreshToken(userLogin);
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
