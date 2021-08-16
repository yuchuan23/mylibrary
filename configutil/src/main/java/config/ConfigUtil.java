package mylib.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;

public class ConfigUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

    @SuppressWarnings("unchecked")
    public static  <T> T getWithDefault(Config config, final String path, T defaultValue) {
        if (config.hasPath(path)) {
            return (T) config.getAnyRef(path);
        } else {
            return defaultValue;
        }
    }
}
