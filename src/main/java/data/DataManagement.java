package data;

import customExceptions.noSuchItemError;
import database.DBManager;
import ui.UIMainWindow;

import customExceptions.noSuchNameError;
import ui.UIExceptionWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DataManagement {

    private static final DataManagement instance = new DataManagement();
    private ArrayList<CheckListItem> checklist;
    private ArrayList<CheckListItem> sortedChecklist;
    private UIMainWindow ui;

    public void setUI(UIMainWindow ui) {
        this.ui = ui;
    }

    private DataManagement() {
        checklist = new ArrayList<>();
        sortedChecklist = new ArrayList<>();
    }

    public static DataManagement getInstance() {
        return instance;
    }

    public ArrayList<CheckListItem> getChecklist(){
        return checklist;
    }

    public void updateSortedChecklistMap(String filter){
        if(sortedChecklist != null)
        assert sortedChecklist != null;
        sortedChecklist.clear();

        for(CheckListItem elem: checklist){
            if(elem.getCheckListName().toLowerCase().contains(filter.toLowerCase())){
                sortedChecklist.add(elem);
            }
        }
    }

    @Deprecated
    public ArrayList<CheckListItem> getSortedChecklistOld() {

        return sortedChecklist.stream()
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<CheckListItem> getSortedChecklist() {
        return sortedChecklist.stream()
                .sorted(Comparator.comparing(CheckListItem::getCheckListName))
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public void fillSortedChecklist(){
        if (sortedChecklist != null) sortedChecklist.clear();

        assert sortedChecklist != null;
        sortedChecklist.addAll(getChecklist());
    }

    public void addToList(String itemName, String date, String time) {
        checklist.add(new CheckListItem(itemName, date, time));

        update();
    }

    public void updateList(CheckListItem... item) {
        checklist.addAll(Arrays.asList(item));
    }

    public CheckListItem getCheckListItemByName(String name){
        try {
            for (CheckListItem elem : getChecklist()) {
                if (elem.getCheckListName().equalsIgnoreCase(name)) {
                    return elem;
                }
            }
            throw new noSuchNameError();
        } catch (Exception e){
            UIExceptionWindow.getInstance().showException(e);
            return null;
        }
    }

    public CheckListItem getCheckListItemByTime(String time){
        for (CheckListItem elem : getChecklist()) {
            if (elem.getCm().getDueTime().equalsIgnoreCase(time)) {
                return elem;
            }
        }

        return null;
    }

    public void removeCheckListItem(CheckListItem item){
        checklist.remove(item);
    }

    public void update(){
        new Thread(() -> {
            DBManager.getInstance().updateDataBase();
            if (ui != null) {
                SwingUtilities.invokeLater(() -> ui.showList());
            }
        }).start();

    }

    public boolean isNameAvailable(String name){
        for(CheckListItem elem: checklist){
            if(elem.getCheckListName().equals(name)){
                return false;
            }
        }
        return true;
    }

}
