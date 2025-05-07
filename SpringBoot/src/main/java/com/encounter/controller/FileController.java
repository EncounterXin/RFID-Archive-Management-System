package com.encounter.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.encounter.properties.ALiOSSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


/**
 * 文件控制器
 *
 * @author Encounter
 * @since 2025/05/07
 */
@RestController
@RequestMapping("/user")
public class FileController
    {
        @Qualifier("ALiOSSProperties")
        @Autowired
        ALiOSSProperties properties;
        
        /**
         * 上传文件
         *
         * @param file 文件
         * @return {@link String }
         */
        @RequestMapping("/uploadFile")
        public String uploadFile(@RequestParam("file") MultipartFile file)
            {
                OSS ossClient = null;
                
                try
                    {
                        ossClient = new OSSClientBuilder().build(properties.getEndpoint(),
                                properties.getAccessKeyId(), properties.getAccessKeySecret());
                        
                        String filename = file.getOriginalFilename();
                        String fullKey = properties.getUploadPath() + "/" + UUID.randomUUID() + filename;
                        
                        // 创建PutObjectRequest对象。
                        PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucketName(),
                                fullKey, file.getInputStream());
                        
                        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
                        // ObjectMetadata metadata = new ObjectMetadata();
                        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
                        // metadata.setObjectAcl(CannedAccessControlList.Private);
                        // putObjectRequest.setMetadata(metadata);
                        
                        // 上传文件。
                        ossClient.putObject(putObjectRequest);
                        
                        
                        return "https://" +
                                properties.getBucketName() + "." +
                                properties.getEndpoint() + "/" +
                                fullKey;
                    }
                catch (Exception e)
                    {
                        e.printStackTrace();
                        return "";
                    }
                finally
                    {
                        if (ossClient != null)
                            {
                                ossClient.shutdown();
                            }
                    }
            }
    }
