package jhuanglululu.cmut.commands.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilePathTree {
    String ID;
    FilePathNodeType TYPE;

    FilePath FILEPATH;
    List<FilePathTree> children;

    private FilePathTree() {
        this.TYPE = FilePathNodeType.ROOT;

        this.ID = null;
        this.FILEPATH = null;
        this.children = new ArrayList<>();
    }

    // For creating folder
    private FilePathTree(String id) {
        this.TYPE = FilePathNodeType.FOLDER;
        this.ID = id;
        this.FILEPATH = null;
        this.children = new ArrayList<>();
    }

    // For creating file
    private FilePathTree(FilePath filePath) {
        this.TYPE = FilePathNodeType.FILE;

        this.ID = filePath.getFile();
        this.FILEPATH = filePath;
        this.children = null;
    }

    public boolean isRoot() {
        return this.TYPE == FilePathNodeType.ROOT;
    }

    public boolean isFolder() {
        return this.TYPE == FilePathNodeType.FOLDER;
    }

    public boolean isFile() {
        return this.TYPE == FilePathNodeType.FILE;
    }

    public boolean hasOnlyOneFolderChildren() {
        List<FilePathTree> children = this.getAllChildren();
        return children.size() == 1 && children.getFirst().isFolder();
    }

    public boolean hasFile() {
        return !(this.getAllFile().isEmpty());
    }

    public boolean hasFolder() {
        return !(this.getAllFolder().isEmpty());
    }

    public boolean containsFolder(String id) {
        return this.getAllFolder().containsKey(id);
    }

    private String getID() {
        return this.ID;
    }

    private FilePathTree getFolder(String id) {
        return this.getAllFolder().get(id);
    }

    private FilePath getFilePath() {
        return this.FILEPATH;
    }

    private List<FilePathTree> getAllChildren() {
        return this.children;
    }

    public Map<String, FilePathTree> getAllFolder() {
        List<FilePathTree> children = this.getAllChildren();
        Map<String, FilePathTree> folders = new HashMap<>();

        for (FilePathTree child : children) {
            if (child.isFolder()) folders.put(child.ID, child);
        }
        return folders;
    }

    public Map<String, FilePath> getAllFile() {
        List<FilePathTree> children = this.getAllChildren();
        Map<String, FilePath> files = new HashMap<>();

        for (FilePathTree child : children) {
            if (child.isFile()) files.put(child.getID(), child.getFilePath());
        }
        return files;
    }

    private void addChildren(FilePath file) {
        this.children.add(new FilePathTree(file));
    }

    private FilePathTree addChildren(String id) {
        FilePathTree folder = new FilePathTree(id);
        this.children.add(folder);
        return folder;
    }

    public void addFile(FilePath file) {
        List<String> folderPath = file.getFolderPath();
        FilePathTree currentFolder = this;

        for (String folder : folderPath) {
            if (currentFolder.containsFolder(folder)) {
                currentFolder = currentFolder.getFolder(folder);
            } else {
                currentFolder = currentFolder.addChildren(folder);
            }
        }

        currentFolder.addChildren(file);
    }

    public static FilePathTree of(Iterable<FilePath> files) {
        FilePathTree root = root();

        for (FilePath file : files) {
            root.addFile(file);
        }

        return root;
    }

    public static FilePathTree root() { return new FilePathTree(); }

    public enum FilePathNodeType {
        ROOT("root"),
        FOLDER("folder"),
        FILE("file");

        private final String value;

        FilePathNodeType(String value) { this.value = value; }

        public String getValue() {
            return value;
        }
    }
}
