//package ui;
//
//import languageSupport.LanguageSupport;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class UISettingsWindow {
//
//    private static final UISettingsWindow INSTANCE = new UISettingsWindow();
//    private JFrame frame;
//
//    public static UISettingsWindow getInstance() {
//        return INSTANCE;
//    }
//
//    public void show() {
//        if (frame != null && frame.isVisible()) {
//            frame.toFront();
//            return;
//        }
//
//        frame = new JFrame("Settings");
//        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//        frame.setSize(400, 150);
//
//        JPanel panel = new JPanel(new GridBagLayout());
//        panel.setBackground(Color.WHITE);
//        frame.add(panel);
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        JLabel languageLabel = new JLabel("Sprache (ALPHA)");
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        panel.add(languageLabel, gbc);
//
//        String[] items = LanguageSupport.getInstance().getLanguages().keySet().toArray(new String[0]);
//        int current = 0;
//        String currentLang = LanguageSupport.getInstance().getCurrentLanguage();
//
//        for (int i = 0; i < items.length; i++) {
//            if (items[i].equals(currentLang)) {
//                current = i;
//                break;
//            }
//        }
//
//        JComboBox<String> languageDropdown = new JComboBox<>(items);
//        languageDropdown.setSelectedIndex(current);
//        gbc.gridx = 1;
//        panel.add(languageDropdown, gbc);
//
//        JButton deleteAllBtn = new JButton("Alles Löschen");
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.gridwidth = 2;
//        panel.add(deleteAllBtn, gbc);
//
//        languageDropdown.addActionListener(e -> {
//            String selected = (String) languageDropdown.getSelectedItem();
//            if (selected != null && LanguageSupport.getInstance().checkAvailable(selected)) {
//                LanguageSupport.getInstance().setLanguage(selected);
//            }
//        });
//
//        deleteAllBtn.addActionListener(e -> {
//            UIMiscWindow.getInstance().confirmDeleteAlert(() ->
//                    UIMainWindow.getInstance().updateList()
//            );
//        });
//
//        frame.setVisible(true);
//    }
//}

package ui;

import data.DataManagement;
import languageSupport.LanguageSupport;
import languageSupport.UIConfig;

import javax.swing.*;
import java.awt.*;

public class UISettingsWindow {

    private static final UISettingsWindow INSTANCE = new UISettingsWindow();
    private JFrame frame;

    public static UISettingsWindow getInstance() {
        return INSTANCE;
    }

    public void show() {
        if (frame != null && frame.isVisible()) {
            frame.toFront();
            return;
        }

        frame = new JFrame(LanguageSupport.getInstance().translate(UIConfig.SETTINGS));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(400, 150);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        frame.add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel languageLabel = new JLabel(LanguageSupport.getInstance().translate(UIConfig.LANGUAGE + UIConfig.BRACKETOPEN + UIConfig.ALPHA + UIConfig.BRACKETCLOSE));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(languageLabel, gbc);

        String[] items = LanguageSupport.getInstance().getLanguages().keySet().toArray(new String[0]);
        int current = 0;
        String currentLang = LanguageSupport.getInstance().getCurrentLanguage();

        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(currentLang)) {
                current = i;
                break;
            }
        }

        JComboBox<String> languageDropdown = new JComboBox<>(items);
        languageDropdown.setSelectedIndex(current);
        gbc.gridx = 1;
        panel.add(languageDropdown, gbc);

        JButton deleteAllBtn = new JButton(LanguageSupport.getInstance().translate(UIConfig.DELETE_ALL));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(deleteAllBtn, gbc);

        languageDropdown.addActionListener(e -> {
            String selected = (String) languageDropdown.getSelectedItem();
            if (selected != null && LanguageSupport.getInstance().checkAvailable(selected)) {
                LanguageSupport.getInstance().setLanguage(selected);
            }
        });

        deleteAllBtn.addActionListener(e -> {
            UIMiscWindow.getInstance().confirmDeleteAlert(() -> {
                DataManagement.getInstance().deteleAll();
                UIMainWindow.getInstance().updateFreshList();
            });
        });


        frame.setVisible(true);
    }
}
