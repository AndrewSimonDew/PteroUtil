package org.andrexserver.playerUtil;

import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigManager {
    private YamlDocument config;

    public void load(File dataFolder) {
        try {
            File configFile = new File(dataFolder, "config.yml");

            if (!configFile.exists()) {
                // Copy default config from resource to plugin folder if not exists
                InputStream defaultConfig = getClass().getClassLoader().getResourceAsStream("config.yml");
                if (defaultConfig == null) {
                    throw new IOException("Default config.yml not found inside JAR!");
                }
                Files.copy(defaultConfig, configFile.toPath());
                defaultConfig.close();
            }
            config = YamlDocument.create(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlDocument getConfig() {
        return config;
    }
}
