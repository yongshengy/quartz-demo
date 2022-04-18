package local.example.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.MonthlyCalendar;

public class QuartzTestCalendar {
    public static void main(String[] args) {
        try {

            // want to exclude some days? Quartz provide the org.quartz.Calendar

            // Calendar: see the implemented in Quartz
            MonthlyCalendar cal = new MonthlyCalendar();
            cal.setDayExcluded(18, true); // exclude the 15th day every month

            // create Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.addCalendar("testCal", cal, false, false);

            // create trigger
            // use the Quartz DSL, and have the Chain programming
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                    .modifiedByCalendar("testCal")
                    .build();


            // JobDetail
            JobDetail job = JobBuilder.newJob(HelloQuartz.class)
                    .withIdentity("job1", "group1")
                    .usingJobData("name", "quartz")
                    .build();


            // schedule the job with the trigger
            scheduler.scheduleJob(job, trigger);

            // start the scheduler
            scheduler.start();

            System.out.println(trigger.getNextFireTime()); // Tue Apr 19 00:00:00 CST 2022
            // keep run 10s
            Thread.sleep(60000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
