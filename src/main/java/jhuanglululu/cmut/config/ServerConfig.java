package jhuanglululu.cmut.config;

import com.mojang.datafixers.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static jhuanglululu.cmut.CommandUtils.MOD_ID;

public class ServerConfig {
    public static SimpleConfig CONFIG;
    private static ServerConfigProvider CONFIG_PROVIDER;
    public static List<String> COMMAND_LISTFUNCTION_USE_COMPACTED_FOLDER;

    public static void registerConfig() {
        CONFIG_PROVIDER = new ServerConfigProvider();
        createConfig();
        CONFIG = SimpleConfig.of(MOD_ID + "-config").provider(CONFIG_PROVIDER).request();
        assignConfig();
    }

    private static void createConfig() {
        CONFIG_PROVIDER.addKeyValuePair(new Pair<>("command.listfunction.use-compacted-folder", "[]"), "Compact folder with only one subfolder.");
    }

    private static void assignConfig() {
        COMMAND_LISTFUNCTION_USE_COMPACTED_FOLDER = CONFIG.getOrDefault("command.listfunction.use-compacted-folder", new ArrayList<>());
    }
}
