package ch.bbcag.javaee.util;

public enum EnumConfig {
    PBKDF2_COST("PBKDF2_COST", "16");

    private String key;
    private String defaultValue;

    EnumConfig(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

}
