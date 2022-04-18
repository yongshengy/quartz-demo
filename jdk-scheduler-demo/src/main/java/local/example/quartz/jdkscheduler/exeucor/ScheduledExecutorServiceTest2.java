package local.example.quartz.jdkscheduler.exeucor;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * every Tuesday 9:00, how to implement?
 */
public class ScheduledExecutorServiceTest2 implements Runnable {
    private String jobName;

    public ScheduledExecutorServiceTest2(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public void run() {
        System.out.println("execute " + jobName);
    }

    /**
     * get the first start date
     * @param currentDate
     * @param dayOfWeek
     * @param hourOfDay
     * @param minuteOfHour
     * @param secondOfMinute
     * @return
     */
    public Calendar getFistStartDate(Calendar currentDate, int dayOfWeek,
                                     int hourOfDay, int minuteOfHour, int secondOfMinute) {
        int currentWeekOfYear = currentDate.get(Calendar.WEEK_OF_YEAR);
        int currentDayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK);
        int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentDate.get(Calendar.MINUTE);
        int currentSecond = currentDate.get(Calendar.SECOND);

        boolean weekLater = false;
        if (dayOfWeek < currentDayOfWeek) {
            weekLater = true;
        } else if (dayOfWeek == currentDayOfWeek) {
            if (hourOfDay < currentHour) {
                weekLater = true;
            } else if (hourOfDay == currentHour) {
                if (minuteOfHour < currentMinute) {
                    weekLater = true;
                } else if (minuteOfHour == currentSecond) {
                    if (secondOfMinute < currentSecond) {
                        weekLater = true;
                    }
                }
            }
        }
        if (weekLater) {
            currentDate.set(Calendar.WEEK_OF_YEAR, currentWeekOfYear + 1);
        }

        currentDate.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        currentDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        currentDate.set(Calendar.MINUTE, minuteOfHour);
        currentDate.set(Calendar.SECOND, secondOfMinute);
        return currentDate;
    }

    public static void main(String[] args) {
        ScheduledExecutorServiceTest2 job = new ScheduledExecutorServiceTest2("job1");
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

        Calendar currentDate = Calendar.getInstance();
        long currentDateLong = currentDate.getTime().getTime();
        System.out.println("Current Date = " + currentDate.getTime().toString());
        // get the first startDate, then run by scheduleAtFixedRate
        Calendar firstStartDate = job
                .getFistStartDate(currentDate, 3, 9, 30, 15);
        long firstStartDateLong = firstStartDate.getTime().getTime();
        System.out.println("First Start Date = "
                + firstStartDate.getTime().toString());

        service.scheduleAtFixedRate(job, firstStartDateLong, 7*24, TimeUnit.HOURS);
    }
}
