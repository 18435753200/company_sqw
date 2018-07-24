package com.mall.dataload;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import java.util.Properties;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import com.mall.config.BeanConfiguration;

/**
 * 获取商品...
 *
 * @author 
 */
public class ProductLoadMain {
    private static  Properties properties;
    public static void main(String[] args) throws Exception {
        properties=new Properties();
        properties.load(ProductLoadMain.class.getResourceAsStream("/quartz.properties"));
        BeanConfiguration.init();
        
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        JobDetail job = newJob(BuildJob.class)
                .withIdentity("job1", "group1")
                .build();
        
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule((String)properties.get("cron")))
                .build();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }


}
