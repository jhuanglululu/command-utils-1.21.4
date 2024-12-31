package jhuanglululu.cmut;

import jhuanglululu.cmut.config.ClientConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import static jhuanglululu.cmut.CommandUtils.LOGGER;

public class CommandUtilsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LOGGER.info("Made by: jhuanglululu!");

        LOGGER.info("Registering Config");
        AutoConfig.register(ClientConfig.class, GsonConfigSerializer::new);
    }
}
