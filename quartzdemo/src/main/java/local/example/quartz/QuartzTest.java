package local.example.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {

    public static void main(String[] args) {
        try {
            // https://www.quartz-scheduler.org/documentation/quartz-2.3.0/quick-start.html
            // 1. create Scheduler
            // 2. create JobDetail and Trigger
            // 3. add JobDetail and Trigger to Scheduler
            // 4. start the Scheduler(shutdown to close the scheduler)

            // create Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // create trigger
            // use the Quartz DSL, and have the Chain programming
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                    .build();
            // cron schedule
            Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * L-7 * ? *"))
                    .build();

            // JobDetail
            JobDetail job = JobBuilder.newJob(HelloQuartz.class)
                    .withIdentity("job1", "group1")
                    .usingJobData("name", "quartz")
                    .build();
            JobDetail job2 = JobBuilder.newJob(HelloQuartz.class)
                    .withIdentity("job2", "group1")
                    .usingJobData("name", "quartz2")
                    .build();

            // schedule the job with the trigger
            scheduler.scheduleJob(job, trigger);
            scheduler.scheduleJob(job2, trigger2);
            // start the scheduler
            scheduler.start();

            // check execution time for cron schedule: L-7
            System.out.println(trigger2.getNextFireTime()); // Sat Apr 23 00:00:00 CST 2022
            // keep run 10s
            Thread.sleep(60000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }


    }
}
