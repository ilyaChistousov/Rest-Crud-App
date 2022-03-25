package util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;

@UtilityClass
public class ConnectionUtil {

    public static SessionFactory getSessionFactory() {
        return ConfigurationUtil.getConfiguration().buildSessionFactory();
    }
}
