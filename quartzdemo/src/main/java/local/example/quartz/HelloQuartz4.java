package local.example.quartz;

import org.quartz.*;

import java.util.Date;

@DisallowConcurrentExecution
public class HelloQuartz4 implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        String name = jobDetail.getJobDataMap().getString("name");
        int age = jobDetail.getJobDataMap().getInt("age");
        System.out.println("name: " + name + " age: " + age + " at " + new Date());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
