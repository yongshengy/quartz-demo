package local.example.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTestTrigger {
    public static void main(String[] args) {
        try {
            // Trigger: SimpleTrigger, CronTrigger
            // CalendarIntervalTrigger - defined methods for interval units: day, week, month
            // DailyTimeIntervalTrigger - startAt endAt for daily and counts..

            // create Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // interval for defined units - no need calculation
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInWeeks(1))
                    .build();

            // 9:00 - 18:00 every hour execution: like cron trigger
            Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                    .startNow()
                    .withSchedule(DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                            .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(9, 0))
                            .endingDailyAt(TimeOfDay.hourAndMinuteOfDay(18, 0))
                            .withIntervalInHours(1)
                    ).build();


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


            System.out.println(trigger.getNextFireTime());
            System.out.println(trigger2.getNextFireTime());
            // keep run 10s
            Thread.sleep(60000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
