package timedata;

import languageSupport.UIConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class DateManipulator{

    private static final Logger log = LoggerFactory.getLogger(DateManipulator.class);
    private LocalDate today;
    private LocalDate dueDate;
    private LocalDate createdDate;

    TimeManipulator tm;

    public DateManipulator(String dueDate, String createdDate) {
        this.today = LocalDate.now();
        try {
            this.dueDate = LocalDate.parse(dueDate, DateTimeFormatter.ISO_LOCAL_DATE);
            this.createdDate = LocalDate.parse(createdDate, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            System.err.println(UIConfig.INVALID_DATE);
            this.createdDate = today;
        }
    }


    public DateManipulator(String dueDate) {
        this.today = LocalDate.now();
        try {
            this.dueDate = LocalDate.parse(dueDate, DateTimeFormatter.ISO_LOCAL_DATE);
            this.createdDate = today;
        } catch (DateTimeParseException e) {
            System.err.println(UIConfig.INVALID_DATE);
        }
    }

    public String getCurrentDate() {
        return today.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String getCreatedDate(){
        return createdDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String getDueDate(){
        return dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public LocalDate getCurrentDateRaw() {
        return today;
    }

    public LocalDate getDueDateRaw(){
        return dueDate;
    }

    public LocalDate getCreatedDateRaw(){
        return createdDate;
    }

    public void setTimeObj(TimeManipulator tm) {

        this.tm = tm;

    }

    public void setDueDateRaw(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueDate(String dueDate){
        try {
            this.dueDate = LocalDate.parse(dueDate, DateTimeFormatter.ISO_LOCAL_DATE);
        }  catch (DateTimeParseException e){
            System.err.println(UIConfig.INVALID_DATE);
        }
    }
}
