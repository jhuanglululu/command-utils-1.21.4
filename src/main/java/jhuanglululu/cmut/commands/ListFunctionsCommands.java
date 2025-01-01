package jhuanglululu.cmut.commands;

import com.mojang.brigadier.context.CommandContext;
import jhuanglululu.cmut.Utility;
import jhuanglululu.cmut.commands.comparator.FilePathComparator;
import jhuanglululu.cmut.commands.utils.FilePath;
import jhuanglululu.cmut.commands.utils.FilePathTree;
import jhuanglululu.cmut.commands.utils.LongText;
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
            source.sendMessage(navigateTree(root, new ArrayList<>(), 0));
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

    private static MutableText navigateTree(FilePathTree tree, List<String> hold, int layer) {

        MutableText result = Text.empty();

        // Handle file first
        if (tree.hasFile()) {
            // Add every file in the folder to the return <MutableText>
            for (Map.Entry<String, FilePath> file : tree.getAllFile().entrySet()) {
                result.append(fileToText(file.getValue(), layer));
            }
        }

        // Handle all the files and its children after
        if (tree.hasFolder()) {

            for (Map.Entry<String, FilePathTree> folder : tree.getAllFolder().entrySet()) {
                // Create a new duplicate of the hold
                List<String> newHold = new ArrayList<>(hold);

                if (layer == 0) {
                    // Handle namespace
                    result.append(namespaceToText(folder.getKey()));
                    result.append(navigateTree(folder.getValue(), new ArrayList<>(), 1));

                } else if (folder.getValue().hasOnlyOneFolderChildren()) {
                    // If there is only one sub folder
                    // Add to the hold and pass it
                    newHold.add(folder.getKey());
                    result.append(navigateTree(folder.getValue(), newHold, layer + 1));

                } else {
                    // If there are two or more sub folder
                    // Print itself and all the path folder on hold and do the recursion thing with empty hold
                    newHold.add(folder.getKey());

                    result.append(folderToText(newHold, layer));
                    result.append(navigateTree(folder.getValue(), new ArrayList<>(), layer + 1));

                }
            }
        }

        // Return everything so that one send message is use
        return result;
    }

    private static MutableText namespaceToText(String namespace) {
        return (MutableText) LongText.of(List.of(
                Text.literal("\n"),
                Text.literal("| ").formatted(Formatting.GRAY),
                Text.literal(namespace).formatted(Formatting.AQUA, Formatting.BOLD)
        ));
    }

    private static MutableText folderToText(List<String> folder, int indent) {
        String string = "/" + String.join("/", folder);
        return (MutableText) LongText.of(List.of(
                Text.literal("\n"),
                Text.literal("| ").formatted(Formatting.GRAY),
                Utility.indent((indent - folder.size() + 1)),
                Text.literal(string).formatted(Formatting.YELLOW)
        ));
    }

    private static MutableText folderToText(String folder, int indent) {
        return (MutableText) LongText.of(List.of(
                Text.literal("\n"),
                Text.literal("| ").formatted(Formatting.GRAY),
                Utility.indent(indent),
                Text.literal("/" + folder).formatted(Formatting.YELLOW)
        ));
    }

    private static MutableText fileToText(FilePath file, int indent) {
        String name = file.getFile();
        String path = file.fileToString();
        return (MutableText) LongText.of(List.of(
                Text.literal("\n"),
                Text.literal("| ").formatted(Formatting.GRAY),
                Utility.indent(indent),
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