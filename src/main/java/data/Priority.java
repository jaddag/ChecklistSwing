package data;

import languageSupport.UIConfig;

public enum Priority {

    LOW(1, UIConfig.LOW_PRIORITY),
    MEDIUM(2, UIConfig.MED_PRIORITY),
    HIGH(3, UIConfig.HIGH_PRIORITY);

    private final int prio;
    private final String name;

    Priority(int prio, String name) {
        this.prio = prio;
        this.name = name;
    }

    public String getString() {
        return name;
    }

    public int getInt() {
        return prio;
    }
}
