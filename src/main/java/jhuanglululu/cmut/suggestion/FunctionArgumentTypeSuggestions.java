package jhuanglululu.cmut.suggestion;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import jhuanglululu.cmut.events.ServerEvents;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.FunctionCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.util.Identifier;


import jhuanglululu.cmut.config.ClientConfig;

import java.util.List;

public class FunctionArgumentTypeSuggestions {
    public static boolean filteredFunctionListInitialized = false;
    public static final List<Identifier> filteredFunctionList = Lists.newArrayList();
    public static void init() {
        FunctionCommand.SUGGESTION_PROVIDER = (context, builder) -> {
            ClientConfig config = AutoConfig.getConfigHolder(ClientConfig.class).getConfig();
            CommandFunctionManager commandFunctionManager = context.getSource().getServer().getCommandFunctionManager();
            CommandSource.suggestIdentifiers(commandFunctionManager.getFunctionTags(), builder, "#");
            if (config.getHideSubfolderFunctions()) {
                if (!filteredFunctionListInitialized) {
                    initFilteredFunctionList(context);
                }
                return CommandSource.suggestIdentifiers(filteredFunctionList, builder);
            } else {
                return CommandSource.suggestIdentifiers(commandFunctionManager.getAllFunctions(), builder);
            }
        };

        // Mark the filtered function list to initialize when reloading datapacks
        ServerEvents.START_DATA_PACK_RELOAD.register((server) -> {
            filteredFunctionListInitialized = false;
        });
    }

    private static void initFilteredFunctionList(CommandContext<ServerCommandSource> context) {
        context.getSource().getServer().getCommandFunctionManager().getAllFunctions().forEach((s) -> {
            // Hide subfolders
            if (!subfolder(s.getPath())) {
                filteredFunctionList.add(s);
            }
        });
        filteredFunctionListInitialized = true;
    }

    private static boolean subfolder(String path) {

        int count = 0;

        for (int i=0; i<path.length(); i++)
        {
            // checking character in string
            if (path.charAt(i) == '/')
                count++;
        }

        return (count > 1);
    }
}
