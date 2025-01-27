package com.song.interception;

import com.song.constant.JwtConstant;
import com.song.context.BaseContext;
import com.song.properties.JwtProperties;
import com.song.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtProperties jwtProperties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        if(token == null){
            log.error("token不存在");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

       String tokenFromRedis = (String) redisTemplate.opsForValue().get(token);
        if(tokenFromRedis == null) {
            log.error("未登陆");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), tokenFromRedis);
            if (claims == null) {
                log.error("登陆超时，请重新登陆");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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
