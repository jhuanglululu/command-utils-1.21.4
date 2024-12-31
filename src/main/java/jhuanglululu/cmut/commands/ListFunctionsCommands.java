package jhuanglululu.cmut.commands;

import com.mojang.brigadier.context.CommandContext;
import jhuanglululu.cmut.commands.comparator.FilePathComparator;
import jhuanglululu.cmut.commands.utils.FilePath;
import jhuanglululu.cmut.commands.utils.FilePathTree;
import jhuanglululu.cmut.commands.utils.LongText;
import jhuanglululu.cmut.config.ServerConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.*;

public class ListFunctionsCommands {
    public ListFunctionsCommands() { throw new AssertionError(); }

    public static void list(CommandContext<ServerCommandSource> context, String hide, String defaultPath) {
        List<FilePath> filePaths = new ArrayList<>();

        context.getSource().getServer().getCommandFunctionManager().getAllFunctions().forEach((s) -> {
            if (hide.equals("none") || !s.getPath().substring(s.getPath().lastIndexOf("/") + 1).startsWith(hide)) {
                FilePath file = new FilePath(s);

                if (file.folderToString().startsWith(defaultPath)) {
                    filePaths.add(file);
                }
            }
        });

        FilePathComparator.sortFileList(filePaths);
        printFilePaths(context, filePaths);
    }

    private static void printFilePaths(CommandContext<ServerCommandSource> context, List<FilePath> fileList) {
        ServerCommandSource source = context.getSource();
        String player = source.getName();

        try {
            FilePathTree root = FilePathTree.of(fileList);
            if (ServerConfig.COMMAND_LISTFUNCTION_USE_COMPACTED_FOLDER.contains(player)) {
                navigateTree(source, root, new ArrayList<>(), 0);
            } else {
                navigateTree(source, root, 0);
            }
        } catch (Exception e) {
            source.sendError(Text.literal(e.getMessage()));
        }
    }

    private static void navigateTree(ServerCommandSource source, FilePathTree tree, int layer) {

        if (tree.hasFile()) {
            for (Map.Entry<String, FilePath> file : tree.getAllFile().entrySet()) {
                source.sendMessage(fileToText(file.getValue(), layer));
            }
        }

        if (tree.hasFolder()) {
            for (Map.Entry<String, FilePathTree> folder : tree.getAllFolder().entrySet()) {
                if (layer == 0) {
                    source.sendMessage(namespaceToText(folder.getKey()));
                    navigateTree(source, folder.getValue(), 1);
                } else {
                    source.sendMessage(folderToText(folder.getKey(), layer));
                    navigateTree(source, folder.getValue(), layer + 1);
                }
            }
        }
    }

    private static void navigateTree(ServerCommandSource source, FilePathTree tree, List<String> hold, int layer) {

        if (tree.hasFile()) {
            for (Map.Entry<String, FilePath> file : tree.getAllFile().entrySet()) {
                source.sendMessage(fileToText(file.getValue(), layer));
            }
        }

        if (tree.hasFolder()) {
            for (Map.Entry<String, FilePathTree> folder : tree.getAllFolder().entrySet()) {
                List<String> newHold = new ArrayList<>(hold);
                if (layer == 0) {
                    source.sendMessage(namespaceToText(folder.getKey()));
                    navigateTree(source, folder.getValue(), new ArrayList<>(), 1);
                } else if (folder.getValue().hasOnlyOneFolderChildren()) {
                    newHold.add(folder.getKey());
                    navigateTree(source, folder.getValue(), newHold, layer + 1);
                } else {
                    newHold.add(folder.getKey());
                    source.sendMessage(folderToText(newHold, layer));
                    navigateTree(source, folder.getValue(), new ArrayList<>(), layer + 1);
                }
            }
        }
    }

    private static MutableText namespaceToText(String namespace) {
        return (MutableText) LongText.of(List.of(
                Text.literal("| ").formatted(Formatting.GRAY),
                Text.literal(namespace).formatted(Formatting.AQUA, Formatting.BOLD)
        ));
    }

    private static MutableText folderToText(List<String> folder, int indent) {
        String string = "/" + String.join("/", folder);
        return (MutableText) LongText.of(List.of(
                Text.literal("| ").formatted(Formatting.GRAY),
                Text.literal("  ".repeat(indent - folder.size() + 1)),
                Text.literal(string).formatted(Formatting.YELLOW)
        ));
    }

    private static MutableText folderToText(String folder, int indent) {
        return (MutableText) LongText.of(List.of(
                Text.literal("| ").formatted(Formatting.GRAY),
                Text.literal("  ".repeat(indent)),
                Text.literal("/" + folder).formatted(Formatting.YELLOW)
        ));
    }

    private static MutableText fileToText(FilePath file, int indent) {
        String name = file.getFile();
        String path = file.fileToString();
        return (MutableText) LongText.of(List.of(
                Text.literal("| ").formatted(Formatting.GRAY),
                Text.literal("  ".repeat(indent)),
                Text.literal("- "),
                Text.literal(name).formatted(Formatting.WHITE).styled(style -> style
                        .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/docs " + path))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, LongText.of(List.of(
                                Text.translatable("command-utils.command.listfunction.output.hover").formatted(Formatting.YELLOW),
                                Text.literal(path)
                        )))))
        ));
    }
}