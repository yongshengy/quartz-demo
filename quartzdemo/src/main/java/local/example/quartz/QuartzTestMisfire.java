package local.example.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTestMisfire {
    public static void main(String[] args) {
        try {
            // same as QuartzTestPriority: set the default threadpool size to 1 default: 10
            // more than one job execution at same time, as the priority

            // create Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // create trigger
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever().withMisfireHandlingInstructionIgnoreMisfires())
                    .build();
            // cron schedule
            Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever().withMisfireHandlingInstructionIgnoreMisfires())
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
//        say hello to quartz at Mon Apr 18 10:13:27 CST 2022  // default: MISFIRE_INSTRUCTION_SMART_POLICY:  SimpleTrigger - MISFIRE_INSTRUCTION_FIRE_NOW
//        say hello to quartz2 at Mon Apr 18 10:13:37 CST 2022
//        say hello to quartz at Mon Apr 18 10:13:48 CST 2022

        // change: withMisfireHandlingInstructionIgnoreMisfires -> Priority not work
//        say hello to quartz at Mon Apr 18 10:49:05 CST 2022
//        say hello to quartz2 at Mon Apr 18 10:49:15 CST 2022
//        say hello to quartz at Mon Apr 18 10:49:25 CST 2022
//        say hello to quartz2 at Mon Apr 18 10:49:35 CST 2022

// org.quartz.jobStore.misfireThreshold=60000
        /*
        The the number of milliseconds the scheduler will ‘tolerate’ a trigger to pass
         its next-fire-time by, before being considered “misfired”.
        The default value (if you don’t make an entry of this property in your configuration) is 60000 (60 seconds).
         */
    }
}
