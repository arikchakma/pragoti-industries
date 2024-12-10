package pragoti.utils;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class ActivityLogger implements Serializable {
    protected String activity;
    protected LocalDate date;
    protected Integer userId;

    public ActivityLogger() {
    }

    public ActivityLogger(String activity, LocalDate date, Integer userId) {
        this.activity = activity;
        this.date = date;
        this.userId = userId;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ActivityLogger{" +
                "activity='" + activity + '\'' +
                ", date=" + date +
                ", userId=" + userId +
                '}';
    }

    public static void log(String activity, Integer userId) {
        ActivityLogger activityLogger = new ActivityLogger(activity, LocalDate.now(), userId);
        FileHandler.writeObjectToFile(activityLogger, "activity_logs.bin");
    }

    public static ArrayList<ActivityLogger> getUserActivityLogs(Integer userId) {
        ArrayList<ActivityLogger> activityLoggers = new ArrayList<ActivityLogger>();
        ArrayList<ActivityLogger> allActivityLoggers = FileHandler.<ActivityLogger>readObjectFromFile("activity_logs.bin");
        if (allActivityLoggers == null) {
            return activityLoggers;
        }

        for (ActivityLogger activityLogger : allActivityLoggers) {
            if (activityLogger.getUserId().equals(userId)) {
                activityLoggers.add(activityLogger);
            }
        }

        return activityLoggers;
    }
}