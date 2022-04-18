package local.example.quartz.springschedule.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTest {

    private final Logger logger = LoggerFactory.getLogger(ScheduleTest.class);

    // run every 5s
    @Scheduled(cron = "0/5 * * ? * *")
    public void cron() {
        logger.info("cron");
    }

    // 1s delay, then fixed delay 1 delay
    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void fixedDelay() throws InterruptedException {
        logger.info("fixedDelay");
        Thread.sleep(10000); // long execution
    }
//2022-04-18 09:32:52.810  INFO 24280 --- [   scheduling-1] l.e.q.springschedule.job.ScheduleTest    : fixedDelay
//2022-04-18 09:33:02.815  INFO 24280 --- [   scheduling-1] l.e.q.springschedule.job.ScheduleTest    : cron
//2022-04-18 09:33:03.827  INFO 24280 --- [   scheduling-1] l.e.q.springschedule.job.ScheduleTest    : fixedDelay
//2022-04-18 09:33:13.831  INFO 24280 --- [   scheduling-1] l.e.q.springschedule.job.ScheduleTest    : cron
//2022-04-18 09:33:14.843  INFO 24280 --- [   scheduling-1] l.e.q.springschedule.job.ScheduleTest    : fixedDelay

    // Problem: as the second job spend long time, and cron can't be executed as expected: every 5s
    // Reason: default is single thread, in log: [   scheduling-1]
    // set thread pool size in application:  spring.task.scheduling.pool.size=10
//2022-04-18 09:36:54.597  INFO 18544 --- [   scheduling-1] l.e.q.springschedule.job.ScheduleTest    : fixedDelay
//2022-04-18 09:36:55.007  INFO 18544 --- [   scheduling-2] l.e.q.springschedule.job.ScheduleTest    : cron
//2022-04-18 09:37:00.014  INFO 18544 --- [   scheduling-2] l.e.q.springschedule.job.ScheduleTest    : cron
//2022-04-18 09:37:05.010  INFO 18544 --- [   scheduling-3] l.e.q.springschedule.job.ScheduleTest    : cron
//2022-04-18 09:37:05.611  INFO 18544 --- [   scheduling-2] l.e.q.springschedule.job.ScheduleTest    : fixedDelay
    // fixedDelay - every 5s

    // Problem: configurable schedule time? --> need code to implement the load from db
    //          cluster env?  --> need distributed locker ...

}
