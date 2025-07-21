package notification;

import data.CheckListItem;
import data.DataManagement;
import languageSupport.LanguageSupport;
import languageSupport.UIConfig;
import ui.UIMiscWindow;


import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class checkReminder {

    private static final checkReminder INSTANCE = new checkReminder();

    private checkReminder() {}

    private boolean osNot = false;
    private ArrayList<Boolean> OverdueOnce;

    public static checkReminder getInstance() {
        return INSTANCE;
    }

    public void checkSystem(){
        for(CheckListItem elem: DataManagement.getInstance().getChecklistMap()){

        }
    }

    public void check(){
        for(CheckListItem elem: DataManagement.getInstance().getChecklistMap()){
            if(elem.getTm().isOverdue()){
                //TODO: refresh ui
            }
            if (SystemTray.isSupported() && elem.getTm().isOverdue() && !elem.getCompleted() && elem.isReminder()) {
                displaySystemNotificationCHATGPT(elem.getCheckListName() + UIConfig.EMPTY +
                        LanguageSupport.getInstance().translate(UIConfig.IS_OVERDUE_LABEL));
                osNot = true;

            }
            if(elem.getTm().isOverdue() && !elem.getCompleted() && elem.isReminder() && !osNot){
                UIMiscWindow.getInstance().customAlert(elem.getCheckListName() + UIConfig.EMPTY + LanguageSupport.getInstance().translate(UIConfig.IS_OVERDUE_LABEL));
                osNot = false;
            }
        }
    }

    public void reminderThread() {
//        makeArray();
        new Thread(() -> {
            while (true) {
                try {
                    check();
                    // Calculate time to the start of the next minute
                    long delay = 60_000 - (System.currentTimeMillis() % 60_000);
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    break; // stop the loop if interrupted
                }
            }
        }).start();
        new Thread(() -> {
            while (true){
                try {
                    checkSystem();

                    long delay = 600_000 - (System.currentTimeMillis() % 60_000);
                    Thread.sleep(delay);
                } catch (InterruptedException e){
                    break;
                }
            }
        }).start();
    }

    private void displaySystemNotificationCHATGPT(String message) {
        try {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource(UIConfig.CHECKBOX_ICON_PATH)); // use actual path or get from resources

            TrayIcon trayIcon = new TrayIcon(image, "Checklist Reminder");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Checklist Reminder");
            tray.add(trayIcon);

            trayIcon.displayMessage("Reminder", message, TrayIcon.MessageType.INFO);

            // Optional: remove tray icon after a short delay
            new Timer().schedule(new TimerTask() {
                public void run() {
                    tray.remove(trayIcon);
                }
            }, 5000); // remove after 5s

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void makeArray(){
//        OverdueOnce.clear();
//        for(CheckListItem elem: DataManagement.getInstance().getChecklistMap()){
//            OverdueOnce.add(elem.getOsReminder());
//        }
//    }

}
