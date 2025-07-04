package data;

import database.DBManager;
import timedata.ClockManipulator;
import timedata.DateManipulator;
import timedata.TimeManipulator;

public class CheckListItem {

    private String checkListName;
    private boolean      isCompleted;

    /* time helpers */
    private final DateManipulator  dm;
    private final ClockManipulator cm;
    private final TimeManipulator  tm;

    private Priority prio;
    private String    category;
    private String    notes;
    private boolean   reminder;

    public CheckListItem(String name, String dueDate, String dueTime) {

        this.checkListName = name;
        this.isCompleted = false;
        this.notes = null;
        this.category = null;
        this.prio = Priority.LOW;
        this.dm = new DateManipulator(dueDate);
        this.cm = new ClockManipulator(dueTime);
        this.tm = new TimeManipulator(dm, cm);
        this.reminder = false;

    }


    public CheckListItem(String name,
                         String dueDate,
                         String dueTime,
                         boolean isCompleted,
                         String createdDate,
                         String createdTime,
                         int priority,
                         String category,
                         String notes,
                         boolean reminder) {

        this.checkListName = name;
        this.isCompleted   = isCompleted;
        this.prio          = Priority.values()[Math.max(0, Math.min(priority - 1, Priority.values().length - 1))];
        this.category      = category;
        this.notes         = notes;
        this.reminder      = reminder;

        this.dm = new DateManipulator(dueDate, createdDate);
        this.cm = new ClockManipulator(dueTime, createdTime);
        this.tm = new TimeManipulator(dm, cm);
        this.dm.setTimeObj(tm);
        this.cm.setTimeObj(tm);
    }

//    public void setDate(String date) {
//        this.date = date;
//        dm.setDueDate(date);
//        update();
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//        cm.setDueTime(time);
//        update();
//    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
        update();
    }

    public void setPriority(Priority prio) {
        this.prio = prio;
        update();
    }

    public void setCategory(String category) {
        this.category = category;
        update();
    }

    public void setNotes(String notes) {
        this.notes = notes;
        update();
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
        update();
    }

    public void setName(String checkListName){
        this.checkListName = checkListName;
    }

    public String getCheckListName() {return checkListName;}
    public boolean getCompleted() {return isCompleted;}
    public DateManipulator  getDm() {return dm;}
    public ClockManipulator getCm() {return cm;}
    public TimeManipulator  getTm() {return tm;}
    public Priority getPriority() {return prio;}
    public String getCategory() {return category;}
    public String getNotes() {return notes;}
    public boolean isReminder() {return reminder;}

    public void toggleComplete() {
        setCompleted(!isCompleted);
    }

    private void update() {
        new Thread(() -> DBManager.getInstance().updateDataBase()).start();
    }

    public void toggleReminder() {
        setReminder(!isReminder());
    }
}
