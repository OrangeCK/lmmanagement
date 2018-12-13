package com.ck.lmmanagement.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @author 01378803
 * @date 2018/12/12 13:50
 * Description  : JWT的工具类
 */
public class JwtUtil {
    /**
     * token的有效时间
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 校验token是否正确
     * @param token 校验码
     * @param loginName 登录名
     * @param secret 秘钥
     * @return 是否正确
     */
    public static boolean verify(String token, String loginName, String secret) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("loginName", loginName)
                    .build();
            //效验token
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @param token
     * @return
     */
    public static String getLoginName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("loginName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     *
     * @param loginName 登录名
     * @param secret 秘钥
     * @return token校验码
     */
    public static String sign(String loginName, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        // 使用secret秘钥进行HS256签名
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带loginName信息
        return JWT.create()
                .withClaim("loginName", loginName)
                // 到期时间
                .withExpiresAt(date)
                // 创建一个新的JWT，并使用给定的算法进行标记
                .sign(algorithm);
    }
}
