package local.example.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTestStatefulJob {
    public static void main(String[] args) {
        try {
            // when the job has some DB change, which won't want to be concurrency, can use StatefulJob
            // Quartz2: StatefulJob - Deprecated, can use the annotation @DisallowConcurrentExecution



            // create Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // create trigger
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                    .build();

            // JobDetail
            JobDetail job = JobBuilder.newJob(HelloQuartz3.class)
                    .withIdentity("job1", "group1")
                    .usingJobData("name", "quartz")
                    .build();

            // schedule the job with the trigger
            scheduler.scheduleJob(job, trigger);
            // start the scheduler
            scheduler.start();

            // keep run 10s
            Thread.sleep(60000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }

        // threadpool: 10, every job execution: 10s, run every 1s
//        say hello to quartz at Mon Apr 18 11:24:07 CST 2022
//        say hello to quartz at Mon Apr 18 11:24:08 CST 2022
//        say hello to quartz at Mon Apr 18 11:24:09 CST 2022
//        say hello to quartz at Mon Apr 18 11:24:10 CST 2022
//        say hello to quartz at Mon Apr 18 11:24:11 CST 2022


//        use the @DisallowConcurrentExecution - HelloQuartz3
        // every 1s, but need to wait the last execution finished, here misfired them
//        say hello to quartz at Mon Apr 18 11:25:00 CST 2022
//        say hello to quartz at Mon Apr 18 11:25:11 CST 2022

    }
}
