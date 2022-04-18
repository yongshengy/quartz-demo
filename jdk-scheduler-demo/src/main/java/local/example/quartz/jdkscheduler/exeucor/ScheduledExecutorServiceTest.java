package local.example.quartz.jdkscheduler.exeucor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceTest implements Runnable {
    private String jobName;

    public ScheduledExecutorServiceTest(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("execute " + jobName);
    }

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        service.scheduleAtFixedRate(new ScheduledExecutorServiceTest("job1"), 1, 1, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(new ScheduledExecutorServiceTest("job2"), 2, 2, TimeUnit.SECONDS);
        // same trigger time as he TimerTest.
        // execute job1
        // execute job2
        // execute job1
        // execute job1
        // execute job2
        // execute job1
        // execute job1
        // execute job2

        // starttime, repeat with the period/delay
        // Complex use: every Tuesday 9:00, how to implement?
    }
}
