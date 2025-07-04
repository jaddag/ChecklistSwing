package timedata;

import languageSupport.UIConfig;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ClockManipulator {

    LocalTime now;
    LocalTime dueTime;
    LocalTime createdTime;

    TimeManipulator tm;

    public ClockManipulator(String dueTime, String createdTime) {
        this.now = LocalTime.now();
        try {
            this.dueTime = LocalTime.parse(dueTime, DateTimeFormatter.ofPattern("HH:mm"));
            this.createdTime = LocalTime.parse(createdTime, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            System.err.println(UIConfig.INVALID_TIME);
        }
    }

    public ClockManipulator(String dueTime) {
        this.now = LocalTime.now();
        try {
            this.dueTime = LocalTime.parse(dueTime, DateTimeFormatter.ofPattern("HH:mm"));
            this.createdTime = now;
        } catch (DateTimeParseException e) {
            System.err.println(UIConfig.INVALID_TIME);
        }
    }

    public String getCurrentTime() {return now.format(DateTimeFormatter.ofPattern("HH:mm"));}

    public String getCreatedTime() {return createdTime.format(DateTimeFormatter.ofPattern("HH:mm"));}

    public String getDueTime() {
        return dueTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public LocalTime getCreatedTimeRaw() {
        return createdTime;
    }

    public LocalTime getCurrentTimeRaw() {
        return now;
    }

    public LocalTime getDueTimeRaw() {
        return dueTime;
    }

    public void setTimeObj(TimeManipulator tm) {
        this.tm = tm;
    }

    public void setDueTimeRaw(LocalTime dueTime) {
        this.dueTime = dueTime;
    }

    public void setDueTime(String dueTime){
        try{
            this.dueTime = LocalTime.parse(dueTime, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e){
            System.err.println(UIConfig.INVALID_TIME);
        }
    }
}
