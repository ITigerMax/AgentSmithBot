package tk.itiger.utils;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * Holds all app data.
 */
class AppPropertiesHolder {


    public static String getToken() {
        return getProperties().getProperty("token");
    }

    public static String getGuildId() {
        return getProperties().getProperty("guild.id");
    }

    /**
     * Get all properties from property's file app.properties, that are located in classpath\resource folder.
     *
     * @return properties that contains within the property's file
     */
    private static final Properties getProperties() {
        Properties properties = null;
        System.setProperty("file.encoding", "UTF-8");
        try(InputStream inputstream = AppPropertiesHolder.class.getClassLoader().getResourceAsStream("app.properties")){
            properties = new Properties();
            properties.load(inputstream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
