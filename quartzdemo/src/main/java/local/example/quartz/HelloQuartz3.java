package local.example.quartz;

import org.quartz.*;

import java.util.Date;

@DisallowConcurrentExecution
public class HelloQuartz3 implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        String name = jobDetail.getJobDataMap().getString("name");
        System.out.println("say hello to " + name + " at " + new Date());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
