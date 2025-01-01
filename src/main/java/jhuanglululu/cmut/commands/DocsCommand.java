package jhuanglululu.cmut.commands;

import com.mojang.brigadier.context.CommandContext;
import jhuanglululu.cmut.Utility;
import jhuanglululu.cmut.commands.docs.TagEntry;
import jhuanglululu.cmut.commands.docs.TagParser;
import jhuanglululu.cmut.commands.utils.FilePath;
import jhuanglululu.cmut.commands.utils.LongText;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jhuanglululu.cmut.CommandUtils.LOGGER;
import static net.minecraft.server.function.FunctionLoader.FUNCTION_REGISTRY_KEY;

public class DocsCommand {
    public DocsCommand() { throw new AssertionError(); }
    private static final ResourceFinder FINDER = new ResourceFinder(RegistryKeys.getPath(FUNCTION_REGISTRY_KEY), ".mcfunction");

    public static void docs(CommandContext<ServerCommandSource> context, String path) {
        ServerCommandSource source = context.getSource();
        Identifier resourceID = Identifier.of(path);
        Identifier pathID = FINDER.toResourcePath(resourceID);
        Optional<Resource> function = source.getServer().getResourceManager().getResource(pathID);

        if (function.isEmpty()) {
            context.getSource().sendFeedback(() -> Text.translatable("commands.function.scheduled.no_functions", path), false);
            return;
        }

        try {
            List<String> docs = readDocs(function.get());
            List<TagEntry> tags = TagParser.parseInput(docs);

            printDocs(source, new FilePath(resourceID), tags);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            source.sendError(Text.literal(e.getMessage()));
        }
    }

    public static List<String> readDocs(Resource function) throws Exception {

        BufferedReader bufferedReader = function.getReader();
        List<String> docs = new ArrayList<>();
        List<String> contents = bufferedReader.lines().toList();

        if (!contents.getFirst().startsWith("# @Docs")) {
            bufferedReader.close();
            return docs;
        }

        for (int i = 1; i < contents.size(); i++) {
            String line = contents.get(i);

            if (line.startsWith("#")) {
                docs.add(line.substring(2));
            } else { break; }
        }

        bufferedReader.close();
        return docs;
    }

    public static void printDocs(ServerCommandSource source, FilePath file, List<TagEntry> tags) {
        String functionName = file.getFile();
        String functionPath = file.folderToString();

        MutableText header = LongText.of(List.of(
                Text.literal("=".repeat(15)).formatted(Formatting.GRAY, Formatting.BOLD),
                Text.translatable("command-utils.command.docs.output.header").formatted(Formatting.AQUA),
                Text.literal("=".repeat(15)).formatted(Formatting.GRAY, Formatting.BOLD)
        ));

        // Create the events first so that it is more organize
        HoverEvent folderHoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, LongText.of(List.of(
                Text.translatable("command-utils.command.docs.output.hover.folder").formatted(Formatting.YELLOW),
                Text.literal(functionPath).formatted(Formatting.WHITE))));
        ClickEvent folderClickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/listfunction none " + functionPath);

        // Create the file info using the file path and name
        MutableText fileInfo = LongText.of(List.of(
                Text.translatable("command-utils.command.docs.output.name").formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                Text.literal(functionName).formatted(Formatting.GRAY),Text.literal("\n"),
                Text.translatable("command-utils.command.docs.output.folder").formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                Text.literal(functionPath).formatted(Formatting.GRAY).styled(style -> style.withHoverEvent(folderHoverEvent).withClickEvent(folderClickEvent))));

        source.sendMessage(LongText.of(List.of(header, fileInfo), "\n\n"));
    }
}
