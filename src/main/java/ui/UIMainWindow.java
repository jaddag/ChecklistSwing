//package ui;
//
//import com.github.lgooddatepicker.components.DatePickerSettings;
//import com.github.lgooddatepicker.components.DateTimePicker;
//import com.github.lgooddatepicker.components.TimePickerSettings;
//import com.github.lgooddatepicker.zinternaltools.WrapLayout;
//import data.CheckListItem;
//import data.DataManagement;
//import database.DBManager;
//import languageSupport.LanguageSupport;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.concurrent.CountDownLatch;
//
//public class UIMainWindow {
//
//    private static final UIMainWindow INSTANCE = new UIMainWindow();
//    public static UIMainWindow getInstance() {
//        return INSTANCE;
//    }
//
//    private final CountDownLatch latch  = new CountDownLatch(1);
//    private final CountDownLatch latch1 = new CountDownLatch(2);
//
//    private JFrame      frame;
//    private JScrollPane pane;
//
//    private JPanel      topPanel;
//    private JPanel      middlePanel;
//    private JPanel      bottomPanel;
//    private JPanel      checkListItems;
//
//    private JTextField  textField;
//    private DateTimePicker dateTimePicker;
//    private JButton     submitting;
//    private JButton     deleteAllButton;
//
//    private JTextField  searchField;
//    private JButton     settingsButton;
//
//    public void startWindow() {
//        try {
//            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//
//        SwingUtilities.invokeLater(() -> {
//
//            DataManagement.getInstance().setUI(this);
//
//            frame = new JFrame("uitest");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(500, 400);
//
//            topPanel    = new JPanel(new GridBagLayout());
//            topPanel.setBackground(Color.LIGHT_GRAY);
//            frame.add(topPanel, BorderLayout.NORTH);
//
//            middlePanel = new JPanel(new BorderLayout());
//            middlePanel.setBackground(Color.GRAY);
//            frame.add(middlePanel, BorderLayout.CENTER);
//
//            bottomPanel = new JPanel(new GridBagLayout());
//            bottomPanel.setBackground(Color.GRAY);
//            frame.add(bottomPanel, BorderLayout.SOUTH);
//
//            searchBar();
//            addToCheckList();
//            showList();
//
//            frame.setVisible(true);
//        });
//    }
//
//    public void showList() {
//        new Thread(() -> {
//            DBManager.getInstance().updateMemory();
//            latch.countDown();
//
//            SwingUtilities.invokeLater(() -> {
//                checkListItems = new JPanel(new WrapLayout(FlowLayout.LEFT, 10, 10));
//                checkListItems.setBackground(Color.WHITE);
//
//                ImageIcon icTrue  = UIMiscWindow.getInstance().loadIcon("/checkmarkw.png");
//                ImageIcon icFalse = UIMiscWindow.getInstance().loadIcon("/checkboxw.png");
//                ImageIcon icTrash = UIMiscWindow.getInstance().loadIcon("/trashw.png");
//                final boolean iconsOk = icTrue != null && icFalse != null && icTrash != null;
//
//                for (CheckListItem elem : DataManagement.getInstance().getChecklistMap()) {
//                    JButton toggle = new JButton();
//                    JButton trash  = new JButton();
//
//                    if (iconsOk) {
//                        toggle.setIcon(elem.getCompleted() ? icTrue : icFalse);
//                        trash.setIcon(icTrash);
//                    } else {
//                        toggle.setText(elem.getCompleted() ? "✔" : "✖");
//                        trash.setText("🗑");
//                    }
//                    UIMiscWindow.getInstance().styleButton(toggle);
//                    UIMiscWindow.getInstance().styleButton(trash);
//
//                    JPanel tile = new JPanel();
//                    tile.setPreferredSize(new Dimension(150, 110));
//                    tile.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//                    tile.setLayout(new BoxLayout(tile, BoxLayout.Y_AXIS));
//
//                    JPanel info = new JPanel();
//                    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
//                    info.setOpaque(false);
//                    info.setAlignmentX(Component.LEFT_ALIGNMENT);
//                    info.add(new JLabel("Name: " + elem.getCheckListName()));
//                    info.add(new JLabel("Date: " + elem.getDate()));
//                    info.add(new JLabel("Time: " + elem.getTime()));
//                    tile.add(info);
//                    tile.add(Box.createVerticalGlue());
//
//                    JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 13, 0));
//                    btnRow.setOpaque(false);
//                    btnRow.add(toggle);
//                    btnRow.add(trash);
//                    btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);
//                    tile.add(btnRow);
//
//                    checkListItems.add(tile);
//
//                    tile.addMouseListener(new MouseAdapter() {
//                        @Override public void mouseClicked(MouseEvent e) {
//                            if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
//                                UIChecklistWindow.getInstance().showWindow(elem.getCheckListName());
//                            }
//                        }
//                    });
//
//                    toggle.addActionListener(ae -> {
//                        elem.toggleComplete();
//                        if (iconsOk) toggle.setIcon(elem.getCompleted() ? icTrue : icFalse);
//                        else         toggle.setText(elem.getCompleted() ? "✔" : "✖");
//
//                        new Thread(() -> {
//                            UIChecklistWindow.getInstance().refreshWindow(elem);
//                            latch1.countDown();
//                        }).start();
//                    });
//
//                    trash.addActionListener(ae -> {
//                        DataManagement.getInstance().removeCheckListItem(elem);
//                        updateList();
//                    });
//                }
//
//                if (pane != null) middlePanel.remove(pane);
//                pane = new JScrollPane(checkListItems,
//                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
//                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//                middlePanel.add(pane, BorderLayout.CENTER);
//                middlePanel.revalidate();
//                middlePanel.repaint();
//            });
//        }).start();
//
//        try { latch.await(); } catch (InterruptedException ex) { throw new RuntimeException(ex); }
//    }
//
//    private void addToCheckList() {
//        DatePickerSettings dSet = new DatePickerSettings();
//        TimePickerSettings tSet = new TimePickerSettings();
//        dateTimePicker = new DateTimePicker(dSet, tSet);
//
//        textField   = new JTextField();
//        submitting  = new JButton("Submit");
//        deleteAllButton = UIMiscWindow.getInstance().iconButton("/trashw.png", "🗑");
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridy = 0;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        gbc.weightx = 0.6; gbc.gridx = 0; bottomPanel.add(textField,      gbc);
//        gbc.weightx = 0.2; gbc.gridx = 1; bottomPanel.add(dateTimePicker, gbc);
//        gbc.weightx = 0.2; gbc.gridx = 2; bottomPanel.add(submitting,     gbc);
//
//        submitting.addActionListener(e -> {
//            String   name = textField.getText().trim();
//            LocalDate date = dateTimePicker.getDatePicker().getDate();
//            LocalTime time = dateTimePicker.getTimePicker().getTime();
//
//            if (name.isEmpty())
//                UIMiscWindow.getInstance().customAlert("Auftrag Name kann nicht leer sein!");
//            else if (date == null || time == null)
//                UIMiscWindow.getInstance().customAlert("Bitte ein gültiges Datum und Uhrzeit auswählen!");
//            else {
//                DataManagement.getInstance().addToList(name, date.toString(), time.toString());
//                updateList();
//            }
//        });
//    }
//
//    private void searchBar() {
//        searchField = new JTextField();
//        settingsButton = UIMiscWindow.getInstance().iconButton("/settingsw.png", "⚙");
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridy    = 0;
//        gbc.fill     = GridBagConstraints.HORIZONTAL;
//        gbc.weightx  = 0.8; gbc.gridx = 0; topPanel.add(searchField,   gbc);
//        gbc.weightx  = 0.2; gbc.gridx = 1; topPanel.add(settingsButton, gbc);
//
//        settingsButton.addActionListener(e -> UISettingsWindow.getInstance().show());
//    }
//
//    public void updateList() {
//        new Thread(() -> {
//            DBManager.getInstance().updateDataBase();
//            SwingUtilities.invokeLater(this::showList);
//        }).start();
//    }
//}

package ui;

import com.github.lgooddatepicker.components.*;
import com.github.lgooddatepicker.zinternaltools.WrapLayout;
import data.CheckListItem;
import data.DataManagement;
import database.DBManager;
import languageSupport.LanguageSupport;
import languageSupport.UIConfig;
import notification.checkReminder;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;

public class UIMainWindow {

    private static final UIMainWindow INSTANCE = new UIMainWindow();
    public static UIMainWindow getInstance() { return INSTANCE; }

    private final CountDownLatch latch  = new CountDownLatch(1);
    private final CountDownLatch latch1 = new CountDownLatch(2);

    private JFrame frame;
    private JScrollPane pane;
    private JPanel topPanel, middlePanel, bottomPanel, checkListItems;
    private JTextField textField, searchField;
    private DateTimePicker dateTimePicker;
    private JButton submitting, deleteAllButton, settingsButton;

    public void startWindow() {
        DBManager.getInstance().updateMemory();
        DataManagement.getInstance().fillSortedChecklist();
        try { UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf()); }
        catch (Exception ignored) {}

        checkReminder.getInstance().reminderThread();

        SwingUtilities.invokeLater(() -> {
            DataManagement.getInstance().setUI(this);

            frame = new JFrame(LanguageSupport.getInstance()
                    .translate(UIConfig.TITLE));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);

            topPanel    = buildPanel(BorderLayout.NORTH,  new GridBagLayout());
            middlePanel = buildPanel(BorderLayout.CENTER,new BorderLayout());
            bottomPanel = buildPanel(BorderLayout.SOUTH, new GridBagLayout());

            searchBar();
            addToCheckList();
            showList();

            frame.setVisible(true);
        });
    }

    public void showList() {

            latch.countDown();

            SwingUtilities.invokeLater(() -> {
                checkListItems = new JPanel(new WrapLayout(FlowLayout.LEFT,10,10));
                checkListItems.setBackground(Color.WHITE);

                ImageIcon icTrue  = UIMiscWindow.getInstance()
                        .loadIcon(UIConfig.CHECKMARK_ICON_PATH);
                ImageIcon icFalse = UIMiscWindow.getInstance()
                        .loadIcon(UIConfig.CHECKBOX_ICON_PATH);
                ImageIcon icTrash = UIMiscWindow.getInstance()
                        .loadIcon(UIConfig.TRASH_ICON_PATH);
                final boolean iconsOk = icTrue!=null && icFalse!=null && icTrash!=null;

                for (CheckListItem elem : DataManagement.getInstance().getSortedChecklist()) {
                    JButton toggle = new JButton();
                    JButton trash  = new JButton();



                    if (iconsOk) {
                        toggle.setIcon(elem.getCompleted()? icTrue : icFalse);
                        trash.setIcon(icTrash);
                    } else {
                        toggle.setText(elem.getCompleted()? UIConfig.CHECKMARKICON : UIConfig.CHECKMARKNEGATEDICON);
                        trash.setText(UIConfig.TRASHICON);
                    }
                    UIMiscWindow.getInstance().styleButton(toggle);
                    UIMiscWindow.getInstance().styleButton(trash);

                    JPanel tile = new JPanel();
                    tile.setPreferredSize(new Dimension(150, 110));
                    tile.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    tile.setLayout(new BoxLayout(tile, BoxLayout.Y_AXIS));

                    JPanel info = new JPanel();
                    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
                    info.setOpaque(false);
                    info.setAlignmentX(Component.LEFT_ALIGNMENT);
                    info.add(new JLabel(LanguageSupport.getInstance().translate(UIConfig.NAME_LABEL) + UIConfig.COLON + UIConfig.EMPTY + elem.getCheckListName()));
                    info.add(new JLabel(LanguageSupport.getInstance().translate(UIConfig.DUE_DATE_LABEL) + UIConfig.COLON + UIConfig.EMPTY + elem.getDm().getDueDate()));
                    info.add(new JLabel(LanguageSupport.getInstance().translate(UIConfig.DUE_TIME_LABEL) + UIConfig.COLON + UIConfig.EMPTY + elem.getCm().getDueTime()));
                    tile.add(info);
                    tile.add(Box.createVerticalGlue());

                    JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT,13,0));
                    btnRow.setOpaque(false);
                    btnRow.add(toggle);
                    btnRow.add(trash);
                    btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);
                    tile.add(btnRow);

                    checkListItems.add(tile);

                    tile.addMouseListener(new MouseAdapter() {
                        @Override public void mouseClicked(MouseEvent e) {
                            if (e.getClickCount()==2 && SwingUtilities.isLeftMouseButton(e))
                                UIChecklistWindow.getInstance().showWindow(elem.getCheckListName());
                        }
                    });

                    toggle.addActionListener(ae -> {
                        elem.toggleComplete();
                        if (iconsOk) toggle.setIcon(elem.getCompleted()? icTrue : icFalse);
                        else         toggle.setText(elem.getCompleted()? UIConfig.CHECKMARKICON : UIConfig.CHECKMARKNEGATEDICON);

                        new Thread(() -> {
                            UIChecklistWindow.getInstance().refreshWindow(elem);
                            latch1.countDown();
                        }).start();
                    });

                    trash.addActionListener(ae -> {
                        DataManagement.getInstance().removeCheckListItem(elem);
                        UIChecklistWindow.getInstance().closeWindow(elem.getCheckListName());
                        updateList();
                    });
                }

                if (pane!=null) middlePanel.remove(pane);
                pane = new JScrollPane(checkListItems,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                middlePanel.add(pane, BorderLayout.CENTER);
                middlePanel.revalidate();
                middlePanel.repaint();
            });
    }

    private void addToCheckList() {
        dateTimePicker = new DateTimePicker(new DatePickerSettings(), new TimePickerSettings());

        textField = new JTextField();
        submitting = new JButton(LanguageSupport.getInstance()
                .translate(UIConfig.SUBMIT_LABEL));
        deleteAllButton = UIMiscWindow.getInstance()
                .iconButton(UIConfig.TRASH_ICON_PATH, UIConfig.TRASHICON);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.weightx=0.6; gbc.gridx=0; bottomPanel.add(textField,      gbc);
        gbc.weightx=0.2; gbc.gridx=1; bottomPanel.add(dateTimePicker, gbc);
        gbc.weightx=0.2; gbc.gridx=2; bottomPanel.add(submitting,     gbc);

        submitting.addActionListener(e -> {
            String name = textField.getText().trim();
            LocalDate date = dateTimePicker.getDatePicker().getDate();
            LocalTime time = dateTimePicker.getTimePicker().getTime();

            if (name.isEmpty()){
                UIMiscWindow.getInstance().customAlert(
                        LanguageSupport.getInstance().translate(UIConfig.ALERT_EMPTY_NAME));
            } else if (date == null || time == null){
                UIMiscWindow.getInstance().customAlert(
                        LanguageSupport.getInstance().translate(UIConfig.ALERT_INVALID_DATE));
            } else if (!DataManagement.getInstance().isNameAvailable(name)){
                UIMiscWindow.getInstance().customAlert(
                        LanguageSupport.getInstance().translate(UIConfig.ALERT_DUPLICATE_NAME));
            } else {
                DataManagement.getInstance()
                        .addToList(name, date.toString(), time.toString());
                updateList();
            }
        });
    }

    private void searchBar() {
        searchField = new JTextField();
        settingsButton = UIMiscWindow.getInstance()
                .iconButton(UIConfig.SETTINGS_ICON_PATH, UIConfig.SETTINGSICON);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy=0; gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.weightx=0.8; gbc.gridx=0; topPanel.add(searchField,gbc);
        gbc.weightx=0.2; gbc.gridx=1; topPanel.add(settingsButton,gbc);

        settingsButton.addActionListener(e -> UISettingsWindow.getInstance().show());

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                search();
            }
            public void removeUpdate(DocumentEvent e) {
                search();
            }
            public void changedUpdate(DocumentEvent e) {
                search();
            }

            private void search() {
                String text = searchField.getText().trim().toLowerCase();
                if(text.contains("t:")){
                    String time = text.substring(2);

                    DataManagement.getInstance().updateSortedChecklistMap(
                            DataManagement.getInstance().getCheckListItemByTime(time).getCheckListName()
                    );
                }
                DataManagement.getInstance().updateSortedChecklistMap(text);

                showList();
            }
        });

    }

    private JPanel buildPanel(String pos, LayoutManager lm) {
        JPanel p = new JPanel(lm); p.setBackground(Color.GRAY);
        frame.add(p,pos); return p;
    }

    public void updateList() {
        new Thread(() -> {
            DBManager.getInstance().updateDataBase();
            showList();
        }).start();
    }

    public void updateListLocal() {
        new Thread(this::showList).start();
    }


}


