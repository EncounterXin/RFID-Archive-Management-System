package com.encounter.conf;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Quartz 配置
 *
 * @author Encounter
 * @since 2025/05/07
 */
@Configuration
public class QuartzConfig
    {
        /**
         * 我工作 detai2
         *
         * @return {@link JobDetail }
         */
        @Bean
        public JobDetail myJobDetai2()
            {
                JobDetail jobDetail = JobBuilder.newJob(MiuiverJob.class)
                        .withIdentity("myJob1", "myJobGroup1")
                        //JobDataMap可以给任务execute传递参数
                        .usingJobData("job_param", "job_param1")
                        .storeDurably()
                        .build();
                return jobDetail;
            }
        
        /**
         * 触发器
         *
         * @return {@link Trigger }
         */
        @Bean
        public Trigger myTrigger1()
            {
                Trigger trigger = TriggerBuilder.newTrigger()
                        .forJob(myJobDetai2())
                        .withIdentity("myTrigger1", "myTriggerGroup1")
                        .usingJobData("job_trigger_param", "job_trigger_param1")
                        .startNow()
                        //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                        //                .withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ? *"))
                        .withSchedule(CronScheduleBuilder.cronSchedule("46 57 22 * * ? *"))
                        .build();
                return trigger;
            }
    }