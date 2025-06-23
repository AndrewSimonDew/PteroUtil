package org.andrexserver.playerUtil;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.andrexserver.playerUtil.commands.PowerCommand;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.*;

@Plugin(id = "pteroutil", name = "Pterodactyl-Util", version = "1.0-SNAPSHOT")
public class Main {


    @Inject
    public final Logger logger;
    public static ProxyServer proxy;
    public static Main instance;

    private static YamlDocument config;

    public static String apiKey;
    public static String panelUrl;
    public static Map<String,Object> serverList;
    public static ConfigManager cfgManager = new ConfigManager();

    @Inject
    public Main(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory) {
        this.proxy = proxy;
        this.logger = logger;

        cfgManager.load(dataDirectory.toFile());
        config = cfgManager.getConfig();

        apiKey = config.getString("apikey");
        panelUrl = config.getString("url");
        Section serversSection = config.getSection("servers");
        serverList = serversSection.getStringRouteMappedValues(true);
    }



    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("Pterodactyl-Utility Initialized");
        proxy.getCommandManager().register("serverctl",new PowerCommand());
        instance = this;
    }



}
