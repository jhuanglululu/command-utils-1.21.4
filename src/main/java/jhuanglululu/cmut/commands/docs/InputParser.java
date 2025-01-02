package jhuanglululu.cmut.commands.docs;

import jhuanglululu.cmut.commands.docs.tag.TagInterface;
import jhuanglululu.cmut.commands.docs.tag.TagParser;

import java.util.ArrayList;
import java.util.List;

public class InputParser {

    public static List<TagInterface> parse(List<String> input) {
        List<TagInterface> entries = new ArrayList<>();
        ArrayList<String> currentTag = new ArrayList<>();

        for (String line : input) {
            if (line.startsWith("@") && !currentTag.isEmpty()) {
                entries.add(TagParser.parse(currentTag));

                currentTag = new ArrayList<>();
            }
            currentTag.add(line);
        }
        entries.add(TagParser.parse(currentTag));

        return entries;
    }
}

