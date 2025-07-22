//package ui;
//
//import data.CheckListItem;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.HashMap;
//import java.util.Map;
//
//public class UIChecklistWindow {
//
//    private static final UIChecklistWindow INSTANCE = new UIChecklistWindow();
//    public static UIChecklistWindow getInstance() {
//        return INSTANCE;
//    }
//
//    private final Map<String, ChecklistWindow> openFrames = new HashMap<>();
//
//    public void showWindow(String name) {
//        CheckListItem cli = data.DataManagement.getInstance().getCheckListItemByName(name);
//        ChecklistWindow cw = openFrames.get(name);
//
//        if (cw == null) {
//            JFrame f = new JFrame("CheckList: " + cli.getCheckListName());
//            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            f.setSize(300, 300);
//
//            JPanel top = new JPanel(new GridBagLayout());
//            top.setBackground(Color.LIGHT_GRAY);
//            top.add(new JLabel(cli.getCheckListName().toUpperCase()));
//            f.add(top, BorderLayout.NORTH);
//
//            JPanel mid = new JPanel(new BorderLayout());
//            mid.setBackground(Color.GRAY);
//            f.add(mid, BorderLayout.CENTER);
//
//            cw = new ChecklistWindow(f, mid);
//            openFrames.put(name, cw);
//        }
//
//        showStats(cli, cw);
//        cw.frame.setVisible(true);
//    }
//
//    public void refreshWindow(CheckListItem cli) {
//        ChecklistWindow cw = openFrames.get(cli.getCheckListName());
//        if (cw != null) showStats(cli, cw);
//    }
//
//    private void showStats(CheckListItem cli, ChecklistWindow cw) {
//        SwingUtilities.invokeLater(() -> {
//            cw.middlePanel.removeAll();
//            cw.middlePanel.setLayout(new BoxLayout(cw.middlePanel, BoxLayout.Y_AXIS));
//
//            cw.middlePanel.add(new JLabel("Name: " + cli.getCheckListName()));
//            cw.middlePanel.add(new JLabel("Due Date: " + cli.getDate()));
//            cw.middlePanel.add(new JLabel("Due Time: " + cli.getTime()));
//            cw.middlePanel.add(new JLabel("Completed: " + cli.getCompleted()));
//            cw.middlePanel.add(new JLabel("Created Date: " + cli.getDm().getCreatedDate()));
//            cw.middlePanel.add(new JLabel("Created Time: " + cli.getCm().getCreatedTime()));
//            cw.middlePanel.add(new JLabel("Priority: " + cli.getPriority()));
//            cw.middlePanel.add(new JLabel("Category: " + cli.getCategory()));
//            cw.middlePanel.add(new JLabel("Notes: " + cli.getNotes()));
//            cw.middlePanel.add(new JLabel("Reminder: " + cli.isReminder()));
//            cw.middlePanel.add(new JLabel("isOverdue? " + cli.getTm().isOverdue()));
//            cw.middlePanel.add(new JLabel("Due: " + cli.getTm().minutesOverdue()));
//
//            cw.middlePanel.revalidate();
//            cw.middlePanel.repaint();
//        });
//    }
//
//
//    private static class ChecklistWindow {
//        final JFrame frame;
//        final JPanel middlePanel;
//        ChecklistWindow(JFrame f, JPanel m) {
//            frame = f; middlePanel = m; }
//    }
//}

package ui;

import data.CheckListItem;
import data.Priority;
import languageSupport.LanguageSupport;
import languageSupport.UIConfig;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UIChecklistWindow {

    private JLabel checklistItemName;
    private JLabel dueDate;
    private JLabel completedLabel;
    private JLabel createdDate;
    private JLabel reminderLabel;
    private JComboBox<String> priorityDropdown;
    private JComboBox<String> categoryDropdown;
    private JTextArea notes;
    private JScrollPane notesPane;
    private JButton changeName;
    private JButton changeDueDate;
    private JButton toggleCompleted;
    private JButton toggleReminder;
    @Deprecated
    private JButton trash;

    CheckListItem cli;
    ChecklistWindow cw;

    ImageIcon imageCheck;
    ImageIcon imageCheckbox;
    ImageIcon icTrash;

    JPanel content;

    private static final UIChecklistWindow INSTANCE = new UIChecklistWindow();

    public static UIChecklistWindow getInstance() {
        return INSTANCE;
    }

    private final Map<String, ChecklistWindow> openFrames = new HashMap<>();

    public void showWindow(String name) {
        cli = data.DataManagement.getInstance().getCheckListItemByName(name);
        cw = openFrames.get(name);

        if (cw == null) {
            JFrame f = new JFrame(LanguageSupport.getInstance().translate(UIConfig.FRAME_TITLE_PREFIX) + UIConfig.COLON + UIConfig.EMPTY + cli.getCheckListName());
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setSize(400, 450);
            f.setResizable(false);

            JPanel top = new JPanel(new GridBagLayout());
            top.setBackground(Color.LIGHT_GRAY);
            top.add(new JLabel(cli.getCheckListName().toUpperCase()));
            f.add(top, BorderLayout.NORTH);

            JPanel mid = new JPanel(new BorderLayout());
            mid.setBackground(Color.GRAY);
            f.add(mid, BorderLayout.CENTER);

            cw = new ChecklistWindow(f, mid);
            openFrames.put(name, cw);
        }

//        System.err.println(cli.getCheckListName() + " :");
//        cli.getTm().getVariables();
//        System.err.println(cli.getTm().minutesBeforeDue());
//        System.err.println(cli.getTm().hoursBeforeDue());
//        System.err.println(cli.getTm().daysBeforeDue());

        showStats();
        cw.frame.setVisible(true);
    }

    public void refreshWindow(CheckListItem cli) {
        ChecklistWindow cw = openFrames.get(cli.getCheckListName());
        if (cw != null) showStats();
    }

    private void showStats() {
        imageCheck  = UIMiscWindow.getInstance()
                .loadIcon(UIConfig.CHECKMARK_ICON_PATH);
        imageCheckbox = UIMiscWindow.getInstance()
                .loadIcon(UIConfig.CHECKBOX_ICON_PATH);
        icTrash = UIMiscWindow.getInstance()
                .loadIcon(UIConfig.TRASH_ICON_PATH);
        final boolean iconsOk = imageCheck!=null && imageCheckbox!=null && icTrash!=null;

        SwingUtilities.invokeLater(() -> {
            cw.middlePanel.removeAll();
            cw.middlePanel.setLayout(new BorderLayout());

            initComponents(cli);

            loadIcons(iconsOk);

            styleButtons();

            addElements();

            ActionListeners(iconsOk);
        });
    }

    private void addElements() {
        //Grid Layout
        content = new JPanel(new GridBagLayout());
        content.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;



        // Helper to add a row with optional button
        TriConsumer<String, JComponent, JComponent> addRow = (label, comp, button) -> {
            gbc.gridx = 0;
            content.add(new JLabel(label), gbc);
            gbc.gridx = 1;
            content.add(comp, gbc);
            gbc.gridx = 2;
            content.add(button != null ? button : Box.createHorizontalStrut(20), gbc);
            gbc.gridy++;
        };

        SharedTriConsumer<String, JComponent> addSharedRow = (label, comp) -> {
            gbc.gridx = 0;
            gbc.gridwidth = 1;
            content.add(new JLabel(label), gbc);

            gbc.gridx = 1;
            gbc.gridwidth = 2;
            content.add(comp, gbc);

            gbc.gridy++;
            gbc.gridwidth = 1;
        };



        //Added Rows
        addRow.accept(getLabel(UIConfig.NAME_LABEL), checklistItemName, changeName);
        addRow.accept(getLabel(UIConfig.DUE_DATE), dueDate, changeDueDate);
        addSharedRow.accept(getLabel(UIConfig.COMPLETED_LABEL), toggleCompleted);
        addSharedRow.accept(getLabel(UIConfig.CREATED_DATE_LABEL), createdDate);
        addSharedRow.accept(getLabel(UIConfig.PRIORITY_LABEL), priorityDropdown);
        addSharedRow.accept(getLabel(UIConfig.CATEGORY_LABEL), categoryDropdown);
        addSharedRow.accept(getLabel(UIConfig.NOTES_LABEL), notesPane);
        addSharedRow.accept(getLabel(UIConfig.REMINDER_LABEL), toggleReminder);
        if (cli.getTm().isOverdue()) {
            addSharedRow.accept(getLabel(UIConfig.DUE_LABEL), centeredJLabel(UIConfig.IS_OVERDUE_LABEL));
        } else if (cli.getCompleted()){
            addSharedRow.accept(getLabel(UIConfig.DUE_LABEL), centeredJLabel(UIConfig.COMPLETED_LABEL));
        } else if (cli.getTm().hoursBeforeDue() <= 24) {
            addSharedRow.accept(getLabel(UIConfig.DUE_LABEL),
                    centeredJLabel(cli.getTm().hoursBeforeDue() + UIConfig.EMPTY + UIConfig.HOURS + UIConfig.EMPTY +
                            cli.getTm().minutesBeforeDue() % 60 + UIConfig.EMPTY + UIConfig.MINUTES));
        } else if (cli.getTm().hoursBeforeDue() <= 1) {
            addSharedRow.accept(getLabel(UIConfig.DUE_LABEL),
                    centeredJLabel(cli.getTm().minutesBeforeDue() % 60 + UIConfig.EMPTY + UIConfig.MINUTES));
        } else {
            addSharedRow.accept(getLabel(UIConfig.DUE_LABEL),
                    centeredJLabel(cli.getTm().daysBeforeDue() + UIConfig.EMPTY + UIConfig.DAYS));
        }
    }

    private void ActionListeners(boolean iconsOk) {
        //Action Listener
        changeName.addActionListener(e -> {
            cli.setName(UIMiscWindow.getInstance().changeWindow(cli.getCheckListName()));
            cw.frame.setTitle(cli.getCheckListName());
            UIMainWindow.getInstance().updateList();
            checklistItemName.setText(LanguageSupport.getInstance().translate(String.valueOf(cli.getCheckListName())));
        });

        changeDueDate.addActionListener(e -> {
            cli.getTm().setDateTime(UIMiscWindow.getInstance().changeWindowWithDateTime(String.valueOf(LanguageSupport.getInstance().translate(cli.getDm().getDueDate()) + UIConfig.EMPTY + LanguageSupport.getInstance().translate(cli.getCm().getDueTime()))));
            UIMainWindow.getInstance().updateList();
            showStats();
        });

        priorityDropdown.addActionListener(e -> {
            String selected = (String) priorityDropdown.getSelectedItem();
            cli.setPriority(Priority.valueOf(selected));
        });

        categoryDropdown.addActionListener(e -> {
            String selected = (String) categoryDropdown.getSelectedItem();
        });

        JScrollPane scrollPane = new JScrollPane(content,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        cw.middlePanel.add(scrollPane, BorderLayout.CENTER);

        cw.middlePanel.revalidate();
        cw.middlePanel.repaint();

        toggleCompleted.addActionListener(ae -> {
            cli.toggleComplete();
            if (iconsOk){ toggleCompleted.setIcon(cli.getCompleted() ? imageCheck : imageCheckbox);
            }else{         toggleCompleted.setText(cli.getCompleted()? UIConfig.CHECKMARKICON : UIConfig.CHECKMARKNEGATEDICON);}
            completedLabel.setText(String.valueOf(cli.getCompleted()));
            UIMainWindow.getInstance().updateListLocal();
        });

        toggleReminder.addActionListener(ae -> {
            cli.toggleReminder();
            reminderLabel.setText(String.valueOf(cli.isReminder()));
            if (iconsOk) toggleReminder.setIcon(cli.isReminder()? imageCheck : imageCheckbox);
            else         toggleReminder.setText(cli.isReminder()? UIConfig.CHECKMARKICON : UIConfig.CHECKMARKNEGATEDICON);
        });

        notes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                notes.setEditable(true);
                notes.requestFocus();
            }
        });


        notes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                if (notes.isEditable()) {
                    cli.setNotes(notes.getText());
                    notes.setEditable(false);
                }
            }
        });
    }

    private void loadIcons(boolean iconsOk) {
        //Load Icons
        if (iconsOk) {
            toggleCompleted.setIcon(cli.getCompleted()? imageCheck : imageCheckbox);
            toggleReminder.setIcon(cli.isReminder()? imageCheck : imageCheckbox);
            trash.setIcon(icTrash);
        } else {
            toggleCompleted.setText(cli.getCompleted()? UIConfig.CHECKMARKICON : UIConfig.CHECKMARKNEGATEDICON);
            toggleReminder.setText(cli.isReminder()? UIConfig.CHECKMARKICON : UIConfig.CHECKMARKNEGATEDICON);
            trash.setText(UIConfig.TRASHICON);
        }
    }

    private String getLabel(String key) {
        return LanguageSupport.getInstance().translate(key) + UIConfig.COLON;
    }

    // Functional interface to accept 3 params //CHATGPT GENERATED
    @FunctionalInterface
    interface TriConsumer<A, B, C>{
        void accept(A a, B b, C c);
    }

    @FunctionalInterface
    interface SharedTriConsumer<A, B>{
        void accept(A a, B b);
    }


    private static class ChecklistWindow {
        final JFrame frame;
        final JPanel middlePanel;
        ChecklistWindow(JFrame f, JPanel m) {
            frame = f; middlePanel = m; }
    }

    public void closeWindow(String name) {
        ChecklistWindow cw = openFrames.remove(name);
        if (cw != null && cw.frame != null) {
            cw.frame.setVisible(false);
            cw.frame.dispose();
        }
    }

    public void initComponents(CheckListItem cli) {
        checklistItemName = new JLabel(LanguageSupport.getInstance().translate(String.valueOf(cli.getCheckListName())));

        dueDate = new JLabel(LanguageSupport.getInstance().translate(cli.getDm().getDueDate()) + UIConfig.EMPTY +
                LanguageSupport.getInstance().translate(cli.getCm().getDueTime()));

        completedLabel = new JLabel(LanguageSupport.getInstance().translate(String.valueOf(cli.getCompleted())));

        createdDate = centeredJLabel(LanguageSupport.getInstance().translate(cli.getDm().getCreatedDate()) + UIConfig.EMPTY +
                LanguageSupport.getInstance().translate(cli.getCm().getCreatedTime()));

        reminderLabel = new JLabel(LanguageSupport.getInstance().translate(String.valueOf(cli.isReminder())));

        priorityDropdown = new JComboBox<>(Arrays.stream(Priority.values())
                .map(Enum::name)
                .toArray(String[]::new));
        priorityDropdown.setSelectedIndex(cli.getPriority().getInt() - 1);

        categoryDropdown = new JComboBox<>(new String[] {UIConfig.NO_CATEGORY, UIConfig.ARBEIT, UIConfig.SCHULE});
//        categoryDropdown = new JComboBox<>(Arrays.stream()
//                .map(Enum::name)
//                .toArray(String[]::new));
        categoryDropdown.setSelectedIndex(0);

        notes = new JTextArea(cli.getNotes());
        notes.setEditable(false);
        notes.setSize(new Dimension(200, 200));
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        notesPane = new JScrollPane(notes);
        notesPane.setSize(new Dimension(200, 200));

        changeName = new JButton(LanguageSupport.getInstance().translate(UIConfig.NAME_LABEL) + UIConfig.EMPTY +
                LanguageSupport.getInstance().translate(UIConfig.CHANGE));
        changeDueDate = new JButton(LanguageSupport.getInstance().translate(UIConfig.DATUM) + UIConfig.EMPTY +
                LanguageSupport.getInstance().translate(UIConfig.CHANGE));
        toggleCompleted = new JButton();
        toggleReminder = new JButton();
        trash = new JButton();
    }

    private void styleButtons() {
        //Button Only Images
        UIMiscWindow.getInstance().styleButton(toggleCompleted);
        UIMiscWindow.getInstance().styleButton(toggleReminder);
        UIMiscWindow.getInstance().styleButton(trash);
    }

    private JLabel centeredJLabel(String text){
        JLabel jlabel = new JLabel(text);
        jlabel.setHorizontalAlignment(SwingConstants.CENTER);
        return jlabel;
    }
}
