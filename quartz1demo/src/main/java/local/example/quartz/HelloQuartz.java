package local.example.quartz;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class HelloQuartz implements Job {
    // Job
    // State

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        String name = jobDetail.getJobDataMap().getString("name");
        System.out.println("say hello to " + name + " at " + new Date());
    }
}
