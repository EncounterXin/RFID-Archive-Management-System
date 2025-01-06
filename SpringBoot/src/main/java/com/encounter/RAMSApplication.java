package com.encounter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class RAMSApplication
    {
        
        public static void main(String[] args)
            {
                ConfigurableApplicationContext app = SpringApplication.run(RAMSApplication.class, args);
                Environment env = app.getEnvironment();
                String protocol = "http";
                if (env.getProperty("server.ssl.key-store") != null)
                    {
                        protocol = "https";
                    }
                try
                    {
                        log.info("""
                                        --/
                                        ---------------------------------------------------------------------------------------
                                        \t\
                                        Application '{}' is running! Access URLs:
                                        \t\
                                        Local: \t\t{}://localhost:{}
                                        \t\
                                        External: \t{}://{}:{}
                                        \t\
                                        Profile(s): \t{}\
                                        
                                        ---------------------------------------------------------------------------------------""",
                                env.getProperty("spring.application.name"),
                                protocol,
                                env.getProperty("server.port"),
                                protocol,
                                InetAddress.getLocalHost().getHostAddress(),
                                env.getProperty("server.port"),
                                env.getActiveProfiles().length == 0 ? "default" : Arrays.toString(env.getActiveProfiles()));
                    }
                catch (UnknownHostException e)
                    {
                        log.error("获取本地IP地址失败", e);
                    }
            }
        
    }
