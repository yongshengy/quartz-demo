package local.example.quartz.jdkscheduler.timer;

import java.util.Timer;

public class TimerTest {
    public static void main(String[] args) {
        Timer timer = new Timer("timer-test-0");
        timer.schedule(new HelloTask("job1"), 1000, 1000);
        timer.schedule(new HelloTask("job2"), 2000, 2000);
        // HelloTask - sleep 1s
        // job1: 1s delay and period 1s
        // job2: 2s delay and period 2s
//        execute job1
//        execute job2
//        execute job1
//        execute job2
//        execute job1
//        execute job2
//        execute job1
//        execute job2

        // When job execution is one long execution, they can't be executed in time.
        // See ScheduledExecutorServiceTest for ThreadPool

    }
}
