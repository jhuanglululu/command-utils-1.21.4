package jhuanglululu.cmut.config;

import com.mojang.datafixers.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static jhuanglululu.cmut.CommandUtils.MOD_ID;

public class ServerConfig {
    public static SimpleConfig CONFIG;
    private static ServerConfigProvider CONFIG_PROVIDER;
    public static List<String> HIDE_PREFIX, FORBID_PREFIX;

    public static void registerConfig() {
        CONFIG_PROVIDER = new ServerConfigProvider();
        createConfig();
        CONFIG = SimpleConfig.of(MOD_ID + "-server-config").provider(CONFIG_PROVIDER).request();
        assignConfig();
    }

    private static void createConfig() {
        CONFIG_PROVIDER.addKeyValuePair(new Pair<>("command.hide-prefix", "[]"), "Hide folders and files with those prefixes from showing up in command suggestion and result");
        CONFIG_PROVIDER.addKeyValuePair(new Pair<>("command.forbid-prefix", "[]"), "Stop everyone from using listfunction and docs command to folder and file with those prefixes");
    }

    private static void assignConfig() {
        HIDE_PREFIX = CONFIG.getOrDefault("command.listfunction.use-compacted-folder", new ArrayList<>());
        FORBID_PREFIX = CONFIG.getOrDefault("command.listfunction.use-compacted-folder", new ArrayList<>());
    }
}
