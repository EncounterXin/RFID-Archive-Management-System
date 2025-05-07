package com.encounter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云OSS配置类<br/>
 * 通过 @ConfigurationProperties 自动绑定 Nacos 中的 aliyun.oss 开头的属性
 *
 * @author Encounter
 * @since 2025/05/07 15:28
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class ALiOSSProperties
    {
        // OSS key
        private String accessKeyId;
        // OSS secret
        private String accessKeySecret;
        // 存储桶名
        private String bucketName;
        // 存储区域
        private String endpoint;
        // 上传路径
        private String uploadPath;
    }
