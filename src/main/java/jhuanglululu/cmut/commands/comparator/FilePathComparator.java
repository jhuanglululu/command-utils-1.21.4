package jhuanglululu.cmut.commands.comparator;

import jhuanglululu.cmut.commands.utils.FilePath;

import java.util.Comparator;
import java.util.List;

public class FilePathComparator {

    public FilePathComparator() {}

    public static void sortFileList(List<FilePath> fileList) {
        fileList.sort(new Comparator<FilePath>() {
            @Override
            public int compare(FilePath path1, FilePath path2) {
                return path1.getFile().compareTo(path2.getFile());
            }
        });
    }
}
