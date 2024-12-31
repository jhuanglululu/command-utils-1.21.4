package jhuanglululu.cmut.commands;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
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
        Identifier pathID = FINDER.toResourcePath(Identifier.of(path));
        Optional<Resource> function = source.getServer().getResourceManager().getResource(pathID);

        if (function.isEmpty()) {
            context.getSource().sendFeedback(() -> Text.translatable("commands.function.scheduled.no_functions", path), false);
            return;
        }

        try {
            List<String> docs = readDocs(function.get());
            for (String line: docs) {
                source.sendMessage(Text.literal(line));
            }
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            return;
        }
    }

    public static List<String> readDocs(Resource function) throws IOException {

        BufferedReader bufferedReader = function.getReader();
        List<String> docs = new ArrayList<>();
        List<String> contents = bufferedReader.lines().toList();

        if (!contents.getFirst().startsWith("# DOCS")) {
            bufferedReader.close();
            return docs;
        }

        for (int i = 1; i < contents.size(); i++) {
            String line = contents.get(i);

            if (line.startsWith("#")) {
                docs.add(line);
            } else { break; }
        }

        bufferedReader.close();
        return docs;
    }
}
