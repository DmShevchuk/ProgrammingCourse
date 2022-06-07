package gui;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18N {
    private static I18N instance;
    private ResourceBundle resourceBundle;
    private String language;

    private I18N() {
    }

    public static I18N getInstance() {
        if (instance == null) {
            instance = new I18N();
            return instance;
        }
        return instance;
    }

    public void changeLocale(String language){
        this.language = language;
        Locale locale;
        if ("Русский".equals(language)) {
            locale = new Locale("ru", "RU");
        } else if ("Nederlands".equals(language)) {
            locale = new Locale("nl", "NL");
        } else if ("Lietuvių".equals(language)) {
            locale = new Locale("lt", "LT");
        }else{
            locale = new Locale("en", "NZ");
        }
        resourceBundle = ResourceBundle.getBundle("i18n.text", locale);
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public String getText(String key){
        if (resourceBundle.containsKey(key)){
            return resourceBundle.getString(key);
        }
        return "...";
    }

    public String getLanguage() {
        return language;
    }
}
