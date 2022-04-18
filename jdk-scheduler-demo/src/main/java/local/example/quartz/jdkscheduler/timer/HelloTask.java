package local.example.quartz.jdkscheduler.timer;

import java.util.TimerTask;

public class HelloTask extends TimerTask {
    private String jobName;

    public HelloTask(String jobName) {
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
}
