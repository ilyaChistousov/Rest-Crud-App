package util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.flywaydb.core.Flyway;
import org.hibernate.cfg.Configuration;
import java.io.IOException;
import java.util.Properties;

@UtilityClass
public class ConfigurationUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
        var load = Flyway.configure()
                .dataSource(getProperties("db.url"),
                        getProperties("db.username"),
                        getProperties("db.password"))
                .locations("classpath:/db/migration")
                .load();
        load.migrate();
    }

    public Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        return configuration.configure();
    }

    private static String getProperties(String key) {
        return PROPERTIES.getProperty(key);
    }

    @SneakyThrows(IOException.class)
    private static void loadProperties() {
        try(var inputStream = ConfigurationUtil.class.getClassLoader()
                .getResourceAsStream("flyway.properties")) {
            PROPERTIES.load(inputStream);
        }
    }
}
