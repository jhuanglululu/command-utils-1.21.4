package jhuanglululu.cmut.config;

import com.mojang.datafixers.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ServerConfigProvider implements SimpleConfig.DefaultConfig {
    private String configContents = "";
    private final List<Pair> CONFIG_LIST = new ArrayList<>();

    public List<Pair> getConfigList() {
        return CONFIG_LIST;
    }

    public void addKeyValuePair(Pair<String, ?> keyValuePair, String comment) {
        CONFIG_LIST.add(keyValuePair);
        if (comment.isEmpty()) {
            configContents += keyValuePair.getFirst() + "=" + keyValuePair.getSecond();
        } else {
            configContents += keyValuePair.getFirst() + "=" + keyValuePair.getSecond() + " # " + comment + "\n";
        }
    }

    @Override
    public String get(String namespace) {
        return configContents;
    }
}
