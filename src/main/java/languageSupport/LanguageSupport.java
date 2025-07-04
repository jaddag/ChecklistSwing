package languageSupport;

import translator.TranslatorBeta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LanguageSupport {

    private static LanguageSupport instance;

    Map<String, String> languages = new HashMap<>();
    private String currentLanguage;
    private String setShort;

    private LanguageSupport() {
        languages.put("English", "en");
        languages.put("Deutsch", "de");
        languages.put("Español", "es");
        languages.put("Français", "fr");
        languages.put("Italiano", "it");
        languages.put("Português", "pt");
        languages.put("Русский", "ru");
        languages.put("中文", "zh");
        languages.put("日本語", "ja");
        languages.put("한국어", "ko");
        languages.put("Türkçe", "tr");
        languages.put("Polski", "pl");
        languages.put("Nederlands", "nl");
        languages.put("Čeština", "cs");
        languages.put("Svenska", "sv");
        languages.put("العربية", "ar");
        setLanguage("Deutsch");
    }

    public static synchronized LanguageSupport getInstance() {
        if (instance == null) {
            instance = new LanguageSupport();
        }
        return instance;
    }

    public void setLanguage(String setLanguage) {
        if(checkAvailable(setLanguage)){
            this.currentLanguage = setLanguage;
        } else {

        }
    }

    public Map<String, String> getLanguages() {
        return languages;
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public boolean checkAvailable(String language) {
        return languages.containsKey(language);
    }


    public String translate(String input) {
        if(getShort().equals("de")){
            return input;
        }
        try {
            return TranslatorBeta.getInstance().translate(input, "de", getShort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String test(){
        try {
            return TranslatorBeta.getInstance().translate("Hello", "en", "es"); // English → Spanish
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String getShort() {
        return languages.getOrDefault(currentLanguage, ""); // returns "" if not found
    }


}
