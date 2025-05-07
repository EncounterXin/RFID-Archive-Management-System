package com.encounter;

import com.encounter.properties.ALiOSSProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * RAMS应用程序
 *
 * @author Encounter
 * @since 2025/05/07
 */
@Slf4j
@MapperScan("com.encounter.mapper")
@SpringBootApplication
@ServletComponentScan
@EnableConfigurationProperties(ALiOSSProperties.class)
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
						log.info(
								"--/\n" +
										"---------------------------------------------------------------------------------------\n" +
										"\t\tApplication '{}' is running! Access URLs:\n" +
										"\t\tLocal: \t\t{}://localhost:{}\n" +
										"\t\tExternal: \t{}://{}:{}\n" +
										"\t\tProfile(s): \t{}\n" +
										"---------------------------------------------------------------------------------------",
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
