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
//import java.net.URL;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.CountDownLatch;
//
//public class UIold {
//    private final Map<String, ChecklistWindow> openChecklistFrames = new HashMap<>();
//
//
//
//    CountDownLatch latch = new CountDownLatch(1);
//    CountDownLatch latch1 = new CountDownLatch(2);
//
//
//    JFrame frame;
//    JFrame settingsFrame;
//    JPanel settingsPanel;
//
//    JScrollPane pane;
//
//    JTextField textField;
//    DatePickerSettings dateSettings;
//    TimePickerSettings timeSettings;
//    DateTimePicker dateTimePicker;
//    JButton submitting;
//    JButton deleteAllButton;
//
//    JTextField searchField;
//    JButton settingsButton;
//
//    JPanel topPanel;
//    JPanel middlePanel;
//    JPanel bottomPanel;
//    JPanel checkListItems;
//
//    JPanel topPanelItem;
//    JPanel middlePanelItem;
//
//    public void startWindow(){
//        SwingUtilities.invokeLater(() -> {
//            DataManagement.getInstance().setUI(this);
//
//
//            frame = new JFrame("uitest");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(500, 400);
//
//
//            topPanel = new JPanel();
//            topPanel.setLayout(new GridBagLayout());
//            topPanel.setBackground(Color.LIGHT_GRAY);
//            frame.add(topPanel, BorderLayout.NORTH);
//
//            middlePanel = new JPanel();
//            middlePanel.setLayout(new BorderLayout());
//            middlePanel.setBackground(Color.GRAY);
//            frame.add(middlePanel, BorderLayout.CENTER);
//
//            bottomPanel = new JPanel();
//            bottomPanel.setLayout(new GridBagLayout());
//            bottomPanel.setBackground(Color.GRAY);
//            frame.add(bottomPanel, BorderLayout.SOUTH);
//
//            frame.setVisible(true);
//
//            searchBar();
//
//            addToCheckList();
//
//            showList();
//        });
//    }
//
//    public void showList() {
//        new Thread(() -> {
//            DBManager.getInstance().updateMemory();
//            latch.countDown();
//
//            SwingUtilities.invokeLater(() -> {
//                checkListItems = new JPanel();
//                checkListItems.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));
//                checkListItems.setBackground(Color.WHITE);
//
//                checkListItems.removeAll();
//
//                ImageIcon trueIcon = null;
//                ImageIcon falseIcon = null;
//                ImageIcon trashIcon = null;
//
//                try {
//                    trueIcon = new ImageIcon(new ImageIcon(getClass().getResource("/checkmarkw.png"))
//                            .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
//                } catch (Exception e) {
//                    System.err.println("checkw.png not found");
//                }
//
//                try {
//                    falseIcon = new ImageIcon(new ImageIcon(getClass().getResource("/checkboxw.png"))
//                            .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
//                } catch (Exception e) {
//                    System.err.println("crossw.png not found");
//                }
//
//                try {
//                    trashIcon = new ImageIcon(new ImageIcon(getClass().getResource("/trashw.png"))
//                            .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
//                } catch (Exception e) {
//                    System.err.println("trashw.png not found");
//                }
//
//                boolean iconsLoaded = trueIcon != null && falseIcon != null && trashIcon != null;
//
//
//                for (CheckListItem elem : DataManagement.getInstance().getChecklistMap()) {
//                    final ImageIcon finalTrueIcon = trueIcon;
//                    final ImageIcon finalFalseIcon = falseIcon;
//
//                    JButton toggleButton = new JButton();
//                    JButton deleteButton = new JButton();
//
//                    if (iconsLoaded) {
//                        toggleButton.setIcon(elem.getCompleted() ? trueIcon : falseIcon);
//                        deleteButton.setIcon(trashIcon);
//                    } else {
//                        toggleButton.setText(elem.getCompleted() ? "✔" : "✖");
//                        deleteButton.setText("🗑");
//                    }
//
//                    toggleButton.setPreferredSize(new Dimension(40, 40));
//                    toggleButton.setMargin(new Insets(0, 0, 0, 0));
//                    toggleButton.setContentAreaFilled(false);
//                    toggleButton.setBorderPainted(false);
//                    toggleButton.setFocusPainted(true);
//
//                    deleteButton.setPreferredSize(new Dimension(40, 40));
//                    deleteButton.setContentAreaFilled(false);
//                    deleteButton.setBorderPainted(false);
//                    deleteButton.setFocusPainted(false);
//
//
//                    JPanel tile = new JPanel();
//                    tile.setPreferredSize(new Dimension(150, 110));
//                    tile.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//                    tile.setLayout(new BoxLayout(tile, BoxLayout.Y_AXIS));
//
//                    String itemName = elem.getCheckListName();
//
//
//                    JPanel info = new JPanel();
//                    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
//                    info.setOpaque(false);
//                    info.setAlignmentX(Component.LEFT_ALIGNMENT);
//                    info.add(new JLabel("Name: " + itemName));
//                    info.add(new JLabel("Date: " + elem.getDate()));
//                    info.add(new JLabel("Time: " + elem.getTime()));
//                    tile.add(info);
//
//
//                    tile.add(Box.createVerticalGlue());
//
//
//                    JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 13, 0));
//                    btnRow.setOpaque(false);
//                    Dimension btnSize = new Dimension(50, 50);
//                    toggleButton.setPreferredSize(btnSize);
//                    deleteButton.setPreferredSize(btnSize);
//                    btnRow.add(toggleButton);
//                    btnRow.add(deleteButton);
//                    btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);
//                    tile.add(btnRow);
//
//                    checkListItems.add(tile);
//
//
//                    tile.addMouseListener(new MouseAdapter() {
//                        @Override
//                        public void mouseClicked(MouseEvent e) {
//                            if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
//                                checkListWindow(elem.getCheckListName());
//                            }
//                        }
//                    });
//
//                    toggleButton.addActionListener(e -> {
//                        elem.toggleComplete();
//                        if (iconsLoaded) {
//                            toggleButton.setIcon(elem.getCompleted() ? finalTrueIcon : finalFalseIcon);
//                        } else {
//                            toggleButton.setText(elem.getCompleted() ? "✔" : "✖");
//                        }
//
//                        new Thread(() -> {
//                            refreshChecklistWindow(elem);
//                            latch1.countDown();
//                        }).start();
//                    });
//
//                    deleteButton.addActionListener(e -> {
//                        DataManagement.getInstance().removeCheckListItem(elem);
//                        updateList();
//                    });
//                }
//
//                if (pane != null) middlePanel.remove(pane);
//
//                pane = new JScrollPane(checkListItems);
//                pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//                pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
//                middlePanel.add(pane, BorderLayout.CENTER);
//
//                middlePanel.revalidate();
//                middlePanel.repaint();
//            });
//        }).start();
//
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//
//
//    public void addToCheckList(){
//        dateSettings = new DatePickerSettings();
//        timeSettings = new TimePickerSettings();
//        dateTimePicker = new DateTimePicker(dateSettings, timeSettings);
//
//        textField = new JTextField();
//        submitting = new JButton("Submit");
//        URL iconURL = getClass().getResource("/trashw.png");
//        if (iconURL != null) {
//            ImageIcon icon = new ImageIcon(iconURL);
//            Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
//            deleteAllButton = new JButton(new ImageIcon(scaled));
//        } else {
//            System.err.println("Icon not found!");
//        }
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridy = 0;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        gbc.weightx = 0.6;
//        gbc.gridx = 0;
//        bottomPanel.add(textField, gbc);
//
//        gbc.weightx = 0.2;
//        gbc.gridx = 1;
//        bottomPanel.add(dateTimePicker, gbc);
//
//        gbc.weightx = 0.2;
//        gbc.gridx = 2;
//        bottomPanel.add(submitting, gbc);
//
//        submitting.addActionListener(e -> {
//            String result = textField.getText();
//            LocalTime time = dateTimePicker.getTimePicker().getTime();
//            LocalDate date = dateTimePicker.getDatePicker().getDate();
//
//            if (result.isBlank()) {
//                customAlert("Auftrag Name kann nicht leer sein!");
//            } else if (date == null || time == null) {
//                customAlert("Bitte ein gültiges Datum und Uhrzeit auswählen!");
//            } else {
//                DataManagement.getInstance().addToList(result, date.toString(), time.toString());
//
//                updateList();
//            }
//        });
//    }
//
//    public void searchBar() {
//        searchField = new JTextField();
//
//        settingsButton = new JButton();
//
//        URL iconURL = getClass().getResource("/settingsw.png");
//        if (iconURL != null) {
//            ImageIcon icon = new ImageIcon(iconURL);
//            Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
//            settingsButton.setIcon(new ImageIcon(scaled));
//        } else {
//            settingsButton.setText("⚙"); // fallback
//            System.err.println("Icon not found!");
//        }
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridy = 0;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        gbc.weightx = 0.8;
//        gbc.gridx = 0;
//        topPanel.add(searchField, gbc);
//
//        gbc.weightx = 0.2;
//        gbc.gridx = 1;
//        topPanel.add(settingsButton, gbc); // ✅ always safe
//
//        settingsButton.addActionListener(e -> settingsWindow());
//    }
//
//    public void confirmDeleteAlert(){
//        int result = JOptionPane.showConfirmDialog(
//                null,
//                "Sind sie sich sicher, all Einträge zu löschen?",
//                "Bestätigen",
//                JOptionPane.YES_NO_OPTION
//        );
//
//        if (result == JOptionPane.YES_OPTION) {
//            new Thread(() -> {
//                DBManager.getInstance().clearOnlyDatabase();
//                latch.countDown();
//            }).start();
//            try {
//                latch.await();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            new Thread(() -> {
//                SwingUtilities.invokeLater(this::showList);
//            }).start();
//        } else {
//
//        }
//    }
//
//    public void customAlert(String msg) {
//        JOptionPane.showMessageDialog(null, msg, "Hinweis", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//
//
//
//    public void settingsWindow() {
//        SwingUtilities.invokeLater(() -> {
//            settingsFrame = new JFrame("Settings");
//            settingsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//            settingsFrame.setSize(400, 150);
//
//            settingsPanel = new JPanel();
//            settingsPanel.setLayout(new GridBagLayout());
//            settingsPanel.setBackground(Color.WHITE);
//            settingsFrame.add(settingsPanel);
//
//            GridBagConstraints gbc = new GridBagConstraints();
//            gbc.insets = new Insets(10, 10, 10, 10); // spacing
//            gbc.fill = GridBagConstraints.HORIZONTAL;
//
////            System.out.println("Jetztige Srpache: " + LanguageSupport.getInstance().getCurrentLanguage() + " Kürzel: " + LanguageSupport.getInstance().getShort());
//
//            // Label
////            JLabel languageLabel = new JLabel(LanguageSupport.getInstance().test());
//            JLabel languageLabel = new JLabel("Sprache");
//            gbc.gridx = 0;
//            gbc.gridy = 0;
//            settingsPanel.add(languageLabel, gbc);
//
//            String[] items = LanguageSupport.getInstance().getLanguages().keySet().toArray(new String[0]);
//
//            int current = 0;
//            String currentLangName = LanguageSupport.getInstance().getCurrentLanguage(); // e.g., "English"
//
//            for (int i = 0; i < items.length; i++) {
//                if (items[i].equals(currentLangName)) {
//                    current = i;
//                    break;
//                }
//            }
//
//
//            JComboBox<String> languageDropdown = new JComboBox<>(items);
//            languageDropdown.setSelectedIndex(current);
//            gbc.gridx = 1;
//            gbc.gridy = 0;
//            gbc.weightx = 1.0;
//            settingsPanel.add(languageDropdown, gbc);
//
//            // Button (spanning two columns)
//            JButton deleteButton = new JButton("Alles Löschen");
//            gbc.gridx = 0;
//            gbc.gridy = 1;
//            gbc.gridwidth = 2;
//            gbc.weightx = 1.0;
//            settingsPanel.add(deleteButton, gbc);
//
//            settingsFrame.setVisible(true);
//
//            languageDropdown.addActionListener(e -> {
//                String selected = (String) languageDropdown.getSelectedItem();
//                if (selected != null && LanguageSupport.getInstance().checkAvailable(selected)) {
//                    LanguageSupport.getInstance().setLanguage(selected);
//                }
//            });
//
//            deleteButton.addActionListener(e -> {
//                confirmDeleteAlert();
//            });
//
//        });
//    }
//
//    public void checkListWindow(String name) {
//        CheckListItem cli = DataManagement.getInstance().getCheckListItemByName(name);
//
//        ChecklistWindow cw = openChecklistFrames.get(name);
//
//        if (cw == null) {
//            JFrame checkListFrame = new JFrame("CheckList: " + cli.getCheckListName());
//            checkListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            checkListFrame.setSize(300, 300);
//
//            topPanelItem = new JPanel(new GridBagLayout());
//            topPanelItem.setBackground(Color.LIGHT_GRAY);
//            topPanelItem.add(new JLabel(cli.getCheckListName().toUpperCase()));
//            checkListFrame.add(topPanelItem, BorderLayout.NORTH);
//
//            middlePanelItem = new JPanel(new BorderLayout());
//            middlePanelItem.setBackground(Color.GRAY);
//            checkListFrame.add(middlePanelItem, BorderLayout.CENTER);
//
//            cw = new ChecklistWindow(checkListFrame, middlePanelItem);
//            openChecklistFrames.put(name, cw);
//        }
//
//        updateCheckListStats(cli, cw);
//        cw.frame.setVisible(true);
//    }
//
//
//    private void updateCheckListStats(CheckListItem cli, ChecklistWindow cw) {
//        SwingUtilities.invokeLater(() -> {
//            cw.middlePanel.removeAll();
//            cw.middlePanel.add(new JLabel(cli.getCompleted() ? "Complete" : "Not Complete", SwingConstants.CENTER));
//            cw.middlePanel.revalidate();
//            cw.middlePanel.repaint();
//        });
//    }
//
//
//    public void refreshChecklistWindow(CheckListItem cli) {
//        ChecklistWindow cw = openChecklistFrames.get(cli.getCheckListName());
//        if (cw != null){
//            updateCheckListStats(cli, cw);
//        }
//    }
//
//
//
//    public String changeWindow(String changeableItem) {
//        JTextField nameField = new JTextField(20);
//        JPanel panel = new JPanel();
//        panel.add(new JLabel("Änderung an " + changeableItem + ":"));
//        panel.add(nameField);
//
//        int result = JOptionPane.showConfirmDialog(
//                null,
//                panel,
//                "Änderung an " + changeableItem + ":",
//                JOptionPane.YES_NO_OPTION,
//                JOptionPane.PLAIN_MESSAGE
//        );
//
//        if (result == JOptionPane.YES_OPTION) {
//            String name = nameField.getText().trim();
//            if (!name.isEmpty()) {
//                new Thread(() -> {
//                    DBManager.getInstance().clearOnlyDatabase();
//                    latch.countDown();
//                }).start();
//
//                try {
//                    latch.await();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//
//                SwingUtilities.invokeLater(this::showList);
//                return name;
//            }
//        }
//        return null;
//    }
//
//    private static class ChecklistWindow {
//        JFrame frame;
//        JPanel middlePanel;
//
//        ChecklistWindow(JFrame frame, JPanel middlePanel) {
//            this.frame = frame;
//            this.middlePanel = middlePanel;
//        }
//    }
//
//    public void updateList(){
//        new Thread(() -> {
//            DBManager.getInstance().updateDataBase();
//            SwingUtilities.invokeLater(this::showList);
//        }).start();
//    }
//
//
//}