package local.example.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTestPriority {
    public static void main(String[] args) {
        try {
            // Test trigger priority: set the default threadpool size to 1 default: 10
            // more than one job execution at same time, as the priority

            /*
             * Note: Priorities are only compared when triggers have the same fire time.
             * A trigger scheduled to fire at 10:59 will always fire before one scheduled to fire at 11:00.
             */

            // create Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // create trigger
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                    .build();
            // cron schedule
            Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                    .withPriority(999)// default is 5
                    .build();

            // JobDetail
            JobDetail job = JobBuilder.newJob(HelloQuartz2.class)
                    .withIdentity("job1", "group1")
                    .usingJobData("name", "quartz")
                    .build();
            JobDetail job2 = JobBuilder.newJob(HelloQuartz2.class)
                    .withIdentity("job2", "group1")
                    .usingJobData("name", "quartz2")
                    .build();

            // schedule the job with the trigger
            scheduler.scheduleJob(job, trigger);
            scheduler.scheduleJob(job2, trigger2);
            // start the scheduler
            scheduler.start();

            // keep run 10s
            Thread.sleep(60000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }
//        say hello to quartz at Mon Apr 18 10:12:55 CST 2022
//        say hello to quartz at Mon Apr 18 10:13:06 CST 2022
//        say hello to quartz2 at Mon Apr 18 10:13:16 CST 2022 // the job1 not executed
//        say hello to quartz at Mon Apr 18 10:13:27 CST 2022
//        say hello to quartz2 at Mon Apr 18 10:13:37 CST 2022
//        say hello to quartz at Mon Apr 18 10:13:48 CST 2022

    }
}
