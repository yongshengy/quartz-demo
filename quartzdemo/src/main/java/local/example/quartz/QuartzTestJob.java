package local.example.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzTestJob {
    public static void main(String[] args) throws SchedulerException, IOException {
        JobDetail job = newJob()
                .ofType(HelloQuartz4.class)
                .withIdentity("job1", "group1")
                .withDescription("this is a test job")
                .usingJobData("age", 30)
                .build();

        job.getJobDataMap().put("name", "yyang");

        Trigger trigger = newTrigger()
                .startNow()
                .withIdentity("trigger1")
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();

        Scheduler sche = StdSchedulerFactory.getDefaultScheduler();
        sche.scheduleJob(job, trigger);

        sche.start();

        System.in.read();

        sche.shutdown();
    }
}
