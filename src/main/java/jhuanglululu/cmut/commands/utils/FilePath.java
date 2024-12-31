package jhuanglululu.cmut.commands.utils;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilePath {
    private final String namespace;
    private final List<String> folder;
    private final String file;

    public FilePath(Identifier identifier) {
        this.namespace = identifier.getNamespace();
        String path = identifier.getPath();
        this.folder = getFolderStructure(path);
        this.folder.removeLast();
        this.file = getFolderStructure(path).getLast();
    }

    public String getNamespace() { return this.namespace; }

    public List<String> getFolder() { return this.folder; }

    public String getFile() { return this.file; }

    public List<String> getFolderPath() {
        List<String> result = new ArrayList<>();
        result.add(this.getNamespace());
        result.addAll(this.getFolder());
        return result;
    }

    public List<String> getFilePath() {
        List<String> result = this.getFolderPath();
        result.add(this.getFile());
        return result;
    }

    public String folderToString() {
        return this.getNamespace() + ":" + String.join("/", this.getFolder());
    }

    public String fileToString() {
        if (this.getFolder().isEmpty()) {
            return this.folderToString() + this.getFile();
        } else {
            return this.folderToString() + "/" + this.getFile();
        }
    }

    public static List<String> getFolderStructure(String filePath) {
        return new ArrayList<String>(Arrays.asList(filePath.split("/")));
    }

    public static String listToString(List<String> list) {
        String namespace = list.getFirst();
        if (list.size() <= 1) {
            return namespace;
        } else {
            List<String> folder = list.subList(1, list.size());
            return namespace + ":" + String.join("/", folder);
        }
    }
}
