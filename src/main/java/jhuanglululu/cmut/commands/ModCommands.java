package jhuanglululu.cmut.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import jhuanglululu.cmut.commands.args.FilePathArgumentType;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import static jhuanglululu.cmut.CommandUtils.MOD_ID;
import static jhuanglululu.cmut.CommandUtils.LOGGER;
import static jhuanglululu.cmut.commands.args.FilePathArgumentType.FOLDER_SUGGESTION;
import static jhuanglululu.cmut.commands.args.FilePathArgumentType.FUNCTION_SUGGESTION;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ModCommands {

    public  ModCommands() {
        throw new AssertionError();
    }

    public static void register() {
        LOGGER.info("Registering Argument Type for " + MOD_ID);

        ArgumentTypeRegistry.registerArgumentType(new Identifier("jhuanglululu", "filepath"), FilePathArgumentType.class, ConstantArgumentSerializer.of(FilePathArgumentType::FilePath));

        LOGGER.info("Registering Commands");

        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> dispatcher.register(literal("listfunction")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(context -> handleCommandListFunction(context, 0))
                .then(argument(CommandArgumentEnum.LISTFUNCTION_COMMAND_ARG_1.getValue(), StringArgumentType.word())
                        .executes(context -> handleCommandListFunction(context, 1))
                        .then(argument(CommandArgumentEnum.LISTFUNCTION_COMMAND_ARG_2.getValue(), FilePathArgumentType.FilePath()).suggests(FOLDER_SUGGESTION)
                                .executes(context -> handleCommandListFunction(context, 2)))))));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("docs")
                .requires(source -> source.hasPermissionLevel(2))
                .then(argument(CommandArgumentEnum.DOCS_COMMAND_ARG_1.getValue(), FilePathArgumentType.FilePath()).suggests(FUNCTION_SUGGESTION)
                        .executes(ModCommands::handleCommandDocs))));
    }

    private static int handleCommandListFunction(CommandContext<ServerCommandSource> context, int args) {
        if (args == 0) {
            ListFunctionsCommands.list(context, "none", "");
        } else if (args == 1) {
            ListFunctionsCommands.list(context, StringArgumentType.getString(context, CommandArgumentEnum.LISTFUNCTION_COMMAND_ARG_1.getValue()), "");
        } else {
            ListFunctionsCommands.list(context, StringArgumentType.getString(context, CommandArgumentEnum.LISTFUNCTION_COMMAND_ARG_1.getValue()), FilePathArgumentType.getPath(context, CommandArgumentEnum.LISTFUNCTION_COMMAND_ARG_2.getValue()));
        }
        return 1;
    }

    private static int handleCommandDocs(CommandContext<ServerCommandSource> context) {
        DocsCommand.docs(context, FilePathArgumentType.getPath(context, CommandArgumentEnum.DOCS_COMMAND_ARG_1.getValue()));
        return 1;
    }

    public enum CommandArgumentEnum {
        LISTFUNCTION_COMMAND_ARG_1("hide prefix"),
        LISTFUNCTION_COMMAND_ARG_2("folder"),
        DOCS_COMMAND_ARG_1("function");

        private final String value;

        CommandArgumentEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
