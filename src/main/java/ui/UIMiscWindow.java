//package ui;
//
//import database.DBManager;
//
//import javax.swing.*;
//import java.awt.*;
//import java.net.URL;
//import java.util.concurrent.CountDownLatch;
//
//public class UIMiscWindow {
//
//    private static final UIMiscWindow INSTANCE = new UIMiscWindow();
//
//    private UIMiscWindow() {}
//
//    public static UIMiscWindow getInstance() {
//        return INSTANCE;
//    }
//
//    public void confirmDeleteAlert(Runnable onConfirm) {
//        int result = JOptionPane.showConfirmDialog(
//                null,
//                "Sind sie sich sicher, all Einträge zu löschen?",
//                "Bestätigen",
//                JOptionPane.YES_NO_OPTION
//        );
//
//        if (result == JOptionPane.YES_OPTION) {
//            CountDownLatch latch = new CountDownLatch(1);
//            new Thread(() -> {
//                DBManager.getInstance().clearOnlyDatabase();
//                latch.countDown();
//            }).start();
//
//            try {
//                latch.await();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            SwingUtilities.invokeLater(onConfirm);
//        }
//    }
//
//    public void customAlert(String msg) {
//        JOptionPane.showMessageDialog(null, msg, "Hinweis", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    public String changeWindow(String changeableItem, Runnable onChange) {
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
//                CountDownLatch latch = new CountDownLatch(1);
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
//                SwingUtilities.invokeLater(onChange);
//                return name;
//            }
//        }
//        return null;
//    }
//
//    ImageIcon loadIcon(String path) {
//        URL u = getClass().getResource(path);
//        if (u == null) return null;
//        Image img = new ImageIcon(u).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
//        return new ImageIcon(img);
//    }
//
//    JButton iconButton(String path, String fallback) {
//        ImageIcon ic = loadIcon(path);
//        JButton b = new JButton(ic != null ? ic : null);
//        if (ic == null) b.setText(fallback);
//        styleButton(b);
//        return b;
//    }
//
//    void styleButton(JButton b) {
//        b.setPreferredSize(new Dimension(40, 40));
//        b.setMargin(new Insets(0, 0, 0, 0));
//        b.setContentAreaFilled(false);
//        b.setBorderPainted(false);
//        b.setFocusPainted(false);
//    }
//}
package ui;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import database.DBManager;
import languageSupport.LanguageSupport;
import languageSupport.UIConfig;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

public class UIMiscWindow {

    private static final UIMiscWindow INSTANCE = new UIMiscWindow();

    private UIMiscWindow() {}

    public static UIMiscWindow getInstance() {
        return INSTANCE;
    }

    public void confirmDeleteAlert(Runnable onConfirm) {
        int result = JOptionPane.showConfirmDialog(
                null,
                LanguageSupport.getInstance().translate(UIConfig.CONFIRM_DELETE_MSG),
                LanguageSupport.getInstance().translate(UIConfig.CONFIRM_TITLE),
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            CountDownLatch latch = new CountDownLatch(1);
            new Thread(() -> {
                DBManager.getInstance().clearOnlyDatabase();
                latch.countDown();
            }).start();

            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            SwingUtilities.invokeLater(onConfirm);
        }
    }

    public void customAlert(String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                LanguageSupport.getInstance().translate(UIConfig.ALERT_INFO_TITLE),
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public String changeWindow(String changeableItem) {
        JTextField nameField = new JTextField(20);
        JPanel panel = new JPanel();
        panel.add(new JLabel(LanguageSupport.getInstance().translate(UIConfig.CHANGE_LABEL) + UIConfig.EMPTY + UIConfig.QUOTATIONMARK + changeableItem + UIConfig.QUOTATIONMARK + UIConfig.EMPTY + UIConfig.COLON));
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                LanguageSupport.getInstance().translate(UIConfig.CHANGE_LABEL) + UIConfig.EMPTY + UIConfig.QUOTATIONMARK + changeableItem + UIConfig.QUOTATIONMARK + UIConfig.EMPTY + UIConfig.COLON,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            CountDownLatch latch1 = new CountDownLatch(1);
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                CountDownLatch latch = new CountDownLatch(1);
                new Thread(() -> {
                    DBManager.getInstance().clearOnlyDatabase();
                    latch.countDown();
                }).start();

                try {
                    latch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                return name;
            } else{
                customAlert(UIConfig.ALERT_EMPTY_NAME);
                return changeWindow(changeableItem);
            }
        } else {
            return changeableItem;
        }
    }

    ImageIcon loadIcon(String path) {
        URL u = getClass().getResource(path);
        if (u == null) return null;
        Image img = new ImageIcon(u).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    JButton iconButton(String path, String fallback) {
        ImageIcon ic = loadIcon(path);
        JButton b = new JButton(ic != null ? ic : null);
        if (ic == null) b.setText(fallback);
        styleButton(b);
        return b;
    }

    void styleButton(JButton b) {
        b.setPreferredSize(new Dimension(40, 40));
        b.setMargin(new Insets(0, 0, 0, 0));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
    }

    public LocalDateTime changeWindowWithDateTime(String changeableItem) {
        DateTimePicker dateTimePicker = new DateTimePicker(new DatePickerSettings(), new TimePickerSettings());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(LanguageSupport.getInstance().translate(UIConfig.CHANGE_LABEL)
                + UIConfig.EMPTY + UIConfig.QUOTATIONMARK + changeableItem + UIConfig.QUOTATIONMARK + UIConfig.EMPTY + UIConfig.COLON));
        panel.add(dateTimePicker);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                LanguageSupport.getInstance().translate(UIConfig.CHANGE_LABEL)
                        + UIConfig.EMPTY + UIConfig.QUOTATIONMARK + changeableItem + UIConfig.QUOTATIONMARK + UIConfig.EMPTY + UIConfig.COLON,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION && dateTimePicker.getDateTimeStrict() != null) {
            return dateTimePicker.getDateTimeStrict();
        }

        return null;
    }

    public boolean showConfirmation(String title, String message, String yesText, String noText) {
        Object[] options = { yesText, noText };
        int result = JOptionPane.showOptionDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        return result == JOptionPane.YES_OPTION;
    }

}

