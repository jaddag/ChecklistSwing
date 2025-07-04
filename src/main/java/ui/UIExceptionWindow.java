package ui;

import languageSupport.LanguageSupport;
import languageSupport.UIConfig;

import javax.swing.*;

public class UIExceptionWindow {

    private static final UIExceptionWindow instance = new UIExceptionWindow();

    private UIExceptionWindow() {
        // private constructor
    }

    public static UIExceptionWindow getInstance() {
        return instance;
    }

    public void showException(Exception e) {
        JOptionPane.showMessageDialog(
                null,
                e.getMessage(),
                LanguageSupport.getInstance().translate(UIConfig.ERROR),
                JOptionPane.ERROR_MESSAGE
        );
    }
}
