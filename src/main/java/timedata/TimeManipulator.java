package timedata;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeManipulator {

    DateManipulator dateManipulator;
    ClockManipulator clockManipulator;

    LocalDate dueDate;
    LocalDate today;
    LocalDate createdDate;
    LocalTime dueTime;
    LocalTime now;
    LocalTime createdTime;

    public TimeManipulator(DateManipulator dm, ClockManipulator cm){

        this.dateManipulator = dm;
        this.clockManipulator = cm;

        refresh();
    }

    public void refresh(){

        dueDate = dateManipulator.getDueDateRaw();
        today = dateManipulator.getCurrentDateRaw();
        createdDate = dateManipulator.getCreatedDateRaw();
        dueTime = clockManipulator.getDueTimeRaw();
        now = clockManipulator.getCurrentTimeRaw();
        createdTime = clockManipulator.getCreatedTimeRaw();

    }

    public boolean isOverdue() {
        refresh();
        if (dueDate.isBefore(today)) return true;

        if (dueDate.isEqual(today)) {
            return dueTime.isBefore(now);
        }
        return false;
    }

    public void getVariables(){
        System.err.println(dueDate);
        System.err.println(today);
        System.err.println(createdDate);
        System.err.println(dueTime);
        System.err.println(now);
        System.err.println(createdTime);

    }



    public long minutesBeforeDue() {
        refresh();
        if (isOverdue()) return 0;
        return Duration.between(LocalDateTime.of(today, now), LocalDateTime.of(dueDate, dueTime)).toMinutes();
    }

    public long hoursBeforeDue() {
        refresh();
        if (isOverdue()) return 0;
        return Duration.between(LocalDateTime.of(today, now), LocalDateTime.of(dueDate, dueTime)).toHours();
    }

    public long daysBeforeDue() {
        refresh();
        if (isOverdue()) return 0;
        return Duration.between(LocalDateTime.of(today, now), LocalDateTime.of(dueDate, dueTime)).toDays();
    }

    public void setDateTime(LocalDateTime ldt){
        refresh();
        if (ldt != null) {
            dateManipulator.setDueDateRaw(ldt.toLocalDate());
            clockManipulator.setDueTimeRaw(ldt.toLocalTime());

        }
    }


}
