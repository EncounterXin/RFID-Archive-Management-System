package com.encounter.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Encounter
 * @since 2025/04/20 17:03
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer
    {
        @Override
        public void addCorsMappings(CorsRegistry registry)
            {
                //项目中的所有接口都支持跨域
                registry.addMapping("/**")
                        //所有地址都可以访问，也可以配置具体地址
                        .allowedOriginPatterns("*")
                        .allowCredentials(true)
                        //"GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"
                        .allowedMethods("*")
                        // 跨域允许时间
                        .maxAge(3600);
            }
    }
