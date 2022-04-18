package local.example.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

public class Quartz1Test {
    public static void main(String[] args) {
        try {
            // https://www.quartz-scheduler.org/documentation/quartz-1.8.6/quick-start.html
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // Define job instance
            JobDetail job = new JobDetail("job1", "group1", HelloQuartz.class);

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("name", "quartz1");
            job.setJobDataMap(jobDataMap);

            // Define a Trigger: every 1s
//            Trigger trigger = new SimpleTrigger("trigger1", "group1", -1, 1000);
            // test cron trigger
            String cronExpression = "0/5 * * L * ? *";
//            String cronExpression = "0/5 * * L-7 * ? *"; // not support on Quartz1
            CronExpression cronExpression1 = new CronExpression(cronExpression);
            Trigger trigger = new CronTrigger("trigger1", "group1", cronExpression);

            // Schedule the job with the trigger
            scheduler.scheduleJob(job, trigger);
            // and start it
            scheduler.start();
            System.out.println(trigger.getNextFireTime());
            Thread.sleep(60000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException | ParseException e) {
            e.printStackTrace();
        }

//        say hello to quartz1 at Mon Apr 18 09:41:30 CST 2022
//        say hello to quartz1 at Mon Apr 18 09:41:31 CST 2022
//        say hello to quartz1 at Mon Apr 18 09:41:32 CST 2022
//        say hello to quartz1 at Mon Apr 18 09:41:33 CST 2022
    }
}
