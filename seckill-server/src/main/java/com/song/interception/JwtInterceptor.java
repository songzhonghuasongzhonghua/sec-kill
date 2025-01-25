package com.song.interception;

import com.song.constant.JwtConstant;
import com.song.context.BaseContext;
import com.song.properties.JwtProperties;
import com.song.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtProperties jwtProperties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

       String tokenFromRedis = (String) redisTemplate.opsForValue().get(token);
        if(tokenFromRedis == null) {
            return false;
        }
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), tokenFromRedis);
            if (claims == null) {
                return false;
            }
            String userId = (String) claims.get(JwtConstant.USER_ID);
            //设置userId为上下文数据，通过threadLocal获取
            BaseContext.setUserId(userId);
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }

}
