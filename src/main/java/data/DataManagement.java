package data;

import database.DBManager;
import ui.UIMainWindow;

import customExceptions.noSuchNameError;
import ui.UIExceptionWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManagement {

    private static final DataManagement instance = new DataManagement();
    private ArrayList<CheckListItem> checklist;
    private UIMainWindow ui;

    public void setUI(UIMainWindow ui) {
        this.ui = ui;
    }

    private DataManagement() {
        checklist = new ArrayList<>();
    }

    public static DataManagement getInstance() {
        return instance;
    }

    public ArrayList<CheckListItem> getChecklistMap(){
        return checklist;
    }

    public DefaultListModel<String> getChecklist() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (CheckListItem item : checklist) {
            model.addElement(item.getCheckListName());
        }
        return model;
    }

    public DefaultListModel<String> getSortedChecklist() {
        DefaultListModel<String> sortedModel = new DefaultListModel<>();

        List<String> sorted = checklist.stream()
                .map(CheckListItem::getCheckListName)
                .sorted()
                .toList();

        sorted.forEach(sortedModel::addElement);
        return sortedModel;
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
            for (CheckListItem elem : getChecklistMap()) {
                if (elem.getCheckListName().equals(name)) {
                    return elem;
                }
            }
            throw new noSuchNameError();
        } catch (Exception e){
            UIExceptionWindow.getInstance().showException(e);
            return null;
        }
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
