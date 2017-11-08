package ch.bbcag.javaee.util;

import javax.faces.context.FacesContext;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHandler {
    private static ConfigHandler instance = new ConfigHandler();

    private LogHelper logger = new LogHelper(getClass().getSimpleName());
    private Properties properties;

    private ConfigHandler() {
        properties = new Properties(getDefaults());
        try (InputStream input = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream
                ("/WEB-INF/config.properties")) {
            properties.load(input);
        } catch (Exception e) {
            // It is still possible to continue
            logger.loge("Unable to load config file! Using defaults.", e);
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

    public int getPBKDF2Cost() {
        String value = getProperty(EnumConfig.PBKDF2_COST);
        if (value.matches("^[0-9]+$")) {
            int cost = Integer.parseInt(value);
            if (cost >= 0 && cost <= 30) {
                return cost;
            }
        }
        logger.loge(String.format("Invalid config option: %s = %s.\nMust be int >= 0 <= 30.\nUsing default value %s",
                                  EnumConfig.PBKDF2_COST
                                          .getKey(), value, EnumConfig.PBKDF2_COST.getDefaultValue()));
        return Integer.parseInt(EnumConfig.PBKDF2_COST.getDefaultValue());
    }

    /**
     * Gets a property.
     * <p>
     * <b>The preferred method of getting properties is to use the specific methods, as they also validate the
     * property</b>
     * </p>
     *
     * @param key the key of the property to get
     * @return the value of the property
     */
    public String getProperty(EnumConfig key) {
        return properties.getProperty(key.getKey());
    }

}
