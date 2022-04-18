package local.example.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

public class QuartzTestStartTime {

    public static void main(String[] args) {
        try {
            // starAt endAt on trigger

            // create Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // add 10s for trigger startime
            ZonedDateTime zonedDateTime = ZonedDateTime.now();
            System.out.println(zonedDateTime);
            ZonedDateTime startZonedDateTime = zonedDateTime.plus(10, ChronoUnit.SECONDS);
            System.out.println(zonedDateTime);

            ZonedDateTime endZonedDateTime = zonedDateTime.plusDays(1);
            System.out.println(endZonedDateTime);

            // create trigger
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                    .startAt(Date.from(startZonedDateTime.toInstant()))
                    .endAt(Date.from(endZonedDateTime.toInstant()))
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
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

            System.out.println(trigger.getNextFireTime());
            System.out.println(trigger.getEndTime());
            // keep run 10s
            Thread.sleep(60000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }


    }
}
