package languageSupport;

public class UIConfig {
    //Special Symbols
    public static final String BRACKETOPEN = "(";
    public static final String BRACKETCLOSE = ")";
    public static final String EMPTY = " ";
    public static final String EXCLAMATIONMARK = "!";
    public static final String QUESTIONMARK = "?";
    public static final String DOT = ".";
    public static final String COLON = ":";
    public static final String QUOTATIONMARK = "\"";

    //Main Window
    public static final String TITLE = "";
    public static final String SUBMIT_LABEL = "Submit";
    public static final String ALERT_EMPTY_NAME = "Auftrag Name kann nicht leer sein" + EXCLAMATIONMARK;
    public static final String ALERT_INVALID_DATE = "Bitte ein gültiges Datum und Uhrzeit auswählen" + EXCLAMATIONMARK;
    public static final String ALERT_DUPLICATE_NAME = "Du hast schon einen Auftrag mit diesem Namen" + EXCLAMATIONMARK;

    //Checklist Window
    public static final String FRAME_TITLE_PREFIX = "CheckList";
    public static final String NAME_LABEL = "Name";
    public static final String DUE_DATE_LABEL = "Due Date";
    public static final String DUE_TIME_LABEL = "Due Time";
    public static final String DUE_DATE = "Due Date";
    public static final String COMPLETED_LABEL = "Completed";
    public static final String CREATED_DATE_LABEL = "Created Date";
    public static final String CREATED_TIME_LABEL = "Created Time";
    public static final String CREATED_Date = "Created Date";
    public static final String PRIORITY_LABEL = "Priority";
    public static final String CATEGORY_LABEL = "Category";
    public static final String NOTES_LABEL = "Notes";
    public static final String REMINDER_LABEL = "Reminder";
    public static final String THE_TASK_IS_OVERDUE = "The task is overdue";
    public static final String IS_OVERDUE_LABEL = "is overdue";
    public static final String DUE_LABEL = "Due";
    public static final String MINUTES = "Minutes";
    public static final String HOURS = "Stunden";
    public static final String DAYS = "Tage";
    public static final String LOW_PRIORITY = "Niedrig";
    public static final String MED_PRIORITY = "Mittel";
    public static final String HIGH_PRIORITY = "Hoch";
    public static final String NO_CATEGORY = "keine Kategorie";
    public static final String ARBEIT = "Arbeit";
    public static final String SCHULE = "Schule";

    //Exception Window
    public static final String ERROR = "Fehler";

    //MISC Window
    public static final String CONFIRM_DELETE_MSG = "Sind sie sich sicher, all Einträge zu löschen";
    public static final String CONFIRM_TITLE = "Bestätigen";
    public static final String ALERT_INFO_TITLE = "Hinweis";
    public static final String CHANGE_LABEL = "Änderung an";
    public static final String CHANGE = "ändern";
    public static final String DATUM = "Datum";

    //Settings Window
    public static final String SETTINGS = "Settings";
    public static final String DELETE_ALL = "Alles löschen";
    public static final String LANGUAGE = "Sprache";
    public static final String ALPHA = "Alpha";

    //Date and Time
    public static final String INVALID_DATE = "Invalid date format. Expected yyyy-MM-dd";
    public static final String INVALID_TIME = "Invalid time format. Expected HH:mm";


    //Paths
    public static final String CHECKMARK_ICON_PATH = "/checkmarkw.png";
    public static final String CHECKBOX_ICON_PATH = "/checkboxw.png";
    public static final String TRASH_ICON_PATH = "/trashw.png";
    public static final String SETTINGS_ICON_PATH = "/settingsw.png";

    //Often used Symbols
    public static final String CHECKMARKICON = "✔";
    public static final String CHECKMARKNEGATEDICON = "✖";
    public static final String TRASHICON = "\uD83D\uDDD1";
    public static final String SETTINGSICON = "⚙";

    public static String getWithColon(String input){
        return input + UIConfig.EMPTY + COLON;
    }

    public static String getWithExclamationMark(String input){
        return input + EXCLAMATIONMARK;
    }

    public static String getWithDot(String input){
        return input + DOT;
    }

    public static String getWithQuestionmark(String input){
        return input + QUESTIONMARK;
    }

}
