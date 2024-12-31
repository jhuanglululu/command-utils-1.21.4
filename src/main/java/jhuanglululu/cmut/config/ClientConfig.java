package jhuanglululu.cmut.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "command-utils")
public class ClientConfig implements ConfigData {
    boolean hideSubfolderFunctions = true;

    public boolean getHideSubfolderFunctions() {
        return this.hideSubfolderFunctions;
    }
}