package jhuanglululu.cmut.commands.args;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import jhuanglululu.cmut.commands.utils.FilePath;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

import jhuanglululu.cmut.commands.ModCommands.CommandArgumentEnum;
import static jhuanglululu.cmut.CommandUtils.LOGGER;
import static jhuanglululu.cmut.Utility.countChar;

public class FilePathArgumentType implements ArgumentType<String> {
    public static final DynamicCommandExceptionType INVALID_PATH = new DynamicCommandExceptionType(o -> Text.translatable("command-utils.argument.filepath.exception").append(Text.literal(": " + o)));

    public static SuggestionProvider<ServerCommandSource> FOLDER_SUGGESTION = (context, builder) -> {
        Iterable<Identifier> candidates = context.getSource().getServer().getCommandFunctionManager().getAllFunctions();
        return CommandSource.suggestMatching(suggestFolder(context, candidates), builder);
    };

    public static SuggestionProvider<ServerCommandSource> FUNCTION_SUGGESTION = (context, builder) -> {
        Iterable<Identifier> candidates = context.getSource().getServer().getCommandFunctionManager().getAllFunctions();
        return CommandSource.suggestMatching(suggestFile(context, candidates), builder);
    };

    private FilePathArgumentType() {}

    public static FilePathArgumentType FilePath() { return new FilePathArgumentType(); }

    public static String getPath(final CommandContext<?> context, final String name) {
        return context.getArgument(name, String.class);
    }

    @Override
    public String parse(final StringReader reader) throws CommandSyntaxException {
        String result = readString(reader);
        if (Identifier.validate(result).isSuccess()) {
            return result;
        } else { throw INVALID_PATH.create(result); }
    }

    private static Iterable<String> suggestFolder(CommandContext<ServerCommandSource> context, Iterable<Identifier> candidates) {
        try {
            String currentArg = getPath(context, CommandArgumentEnum.LISTFUNCTION_COMMAND_ARG_2.getValue());
            int layer = 1 + countChar(currentArg, '/');
            if (":/".indexOf(currentArg.charAt(currentArg.length() - 1)) != -1) {
                layer++;
            }
            return getFolders(candidates, layer);
        } catch (Exception e) {
            return getFolders(candidates, 1);
        }
    }

    private static Iterable<String> suggestFile(CommandContext<ServerCommandSource> context, Iterable<Identifier> candidates) {
        try {
            String currentArg = getPath(context, CommandArgumentEnum.DOCS_COMMAND_ARG_1.getValue());
            int layer = 1 + countChar(currentArg, '/');
            if (":/".indexOf(currentArg.charAt(currentArg.length() - 1)) != -1) {
                layer++;
            }
            return getFiles(candidates, layer);
        } catch (Exception e) {
            return getFiles(candidates, 1);
        }
    }


    private static String readString(StringReader reader) {
        int i = reader.getCursor();

        while (reader.canRead() && Identifier.isCharValid(reader.peek())) {
            reader.skip();
        }

        return reader.getString().substring(i, reader.getCursor());
    }

    private static Iterable<String> getFolders(Iterable<Identifier> identifiers, int layer) {
        Set<String> result = new HashSet<>();
        for (Identifier identifier : identifiers) {
            FilePath file = new FilePath(identifier);
            List<String> folder = file.getFolderPath();
            if (folder.size() >= layer) {
                LOGGER.info(FilePath.listToString(folder.subList(0, layer)));
                result.add(FilePath.listToString(folder.subList(0, layer)));
            }
        }
        return new ArrayList<>(result);
    }

    private static Iterable<String> getFiles(Iterable<Identifier> identifiers, int layer) {
        Set<String> result = new HashSet<>();
        for (Identifier identifier : identifiers) {
            FilePath file = new FilePath(identifier);
            List<String> filePath = file.getFilePath();
            if (filePath.size() >= layer) {
                result.add(FilePath.listToString(filePath.subList(0, layer)));
            }
        }
        return new ArrayList<>(result);
    }
}