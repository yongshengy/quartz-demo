package local.example.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.MutableTrigger;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class QuartzTestCron {
    public static void main(String[] args) {
        try {
// https://www.freeformatter.com/cron-expression-generator-quartz.html
            // Cron expression: IMPORTANT
            String cronExpr1 = "0 0/2 8-17 * * ?";
            String cronExpr2 = "0 30 9 ? * MON";
            String cronExpr3 = "0 30 9 L-7 * ?";
            Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpr1)).build();
            Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpr2)).build();
            Trigger trigger3 = TriggerBuilder.newTrigger().withIdentity("trigger3", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpr3)).build();

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = JobBuilder.newJob(HelloQuartz.class)
                    .withIdentity("job1", "group1")
                    .usingJobData("name", "quartz")
                    .build();

//            if (scheduler.checkExists(job.getKey())){
//                scheduler.deleteJob(job.getKey());
//            }
            // more than one triggers on the job
            scheduler.scheduleJob(job, new HashSet(Arrays.asList(trigger1, trigger2, trigger3)), false);

            System.out.println(trigger1.getNextFireTime());
            System.out.println(trigger2.getNextFireTime());
            System.out.println(trigger3.getNextFireTime());

            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
