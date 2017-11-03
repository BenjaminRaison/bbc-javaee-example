package ch.bbcag.javaee.util;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHandler {
    private static ConfigHandler instance = new ConfigHandler();

    private Properties properties;

    private ConfigHandler() {
        properties = new Properties(getDefaults());
        try (InputStream input = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream
                ("/WEB-INF/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigHandler getInstance() {
        return instance;
    }

    private Properties getDefaults() {
        Properties defaults = new Properties();
        for (EnumConfig config :
                EnumConfig.values()) {
            defaults.setProperty(config.getKey(), config.getDefaultValue());
        }
        return defaults;
    }

    public String getProperty(EnumConfig key) {
        return properties.getProperty(key.getKey());
    }

}
