package jhuanglululu.cmut;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandUtils implements ModInitializer {
	public static final String MOD_ID = "command-utils";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Made by: jhuanglululu!");
	}
}