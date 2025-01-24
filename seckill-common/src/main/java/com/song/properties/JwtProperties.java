package com.song.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "seckill.jwt")
@Component
public class JwtProperties {

    private String secretKey;
    private int ttl;
    private String tokenName;

}
