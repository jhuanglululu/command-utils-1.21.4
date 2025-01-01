package jhuanglululu.cmut.commands.docs;

import java.util.ArrayList;
import java.util.List;

public class TagParser {

    public static void main(String[] args) {
        List<String> input = List.of(
                "@Docs ABSTRACT DEMO DEPRECATED<demonstration:daddy_folder/subfolder/new_and_awesome>",
                "@Date",
                "CREATED -> 2024/12/31",
                "@Variable",
                "foo -> a random variable",
                "bar -> another random variable",
                "@Require",
                "(foo) > (bar)"
        );

        List<TagEntry> entries = parseInput(input);

        // Print the result
        entries.forEach(System.out::println);
    }

    public static List<TagEntry> parseInput(List<String> input) {
        List<TagEntry> entries = new ArrayList<>();
        String currentTag = null;
        List<PropertyEntry> currentProperty = null;
        List<String> currentContent = new ArrayList<>();

        for (String line : input) {

            if (line.startsWith("@")) {
                if (currentTag != null) {
                    entries.add(new TagEntry(currentTag, currentProperty, currentContent));
                    currentContent = new ArrayList<>();
                }
                String[] splitLine =  line.substring(1).split(" ", 2);
                currentTag = splitLine[0];
                if (splitLine.length > 1) {
                    currentProperty = parseProperty(splitLine[1]);
                } else {
                    currentProperty = null;
                }
            } else {
                currentContent.add(line);
            }
        }
        entries.add(new TagEntry(currentTag, currentProperty, currentContent));
        return entries;
    }

    public static List<PropertyEntry> parseProperty(String input) {
        List<PropertyEntry> entries = new ArrayList<>();
        String string = input;

        int i = string.length() - 1;
        boolean seenArg = false;
        while (i > 0) {
            char current = input.charAt(i);
            if (current == '>') {
                seenArg = true;
            } else if (current == '<') {
                seenArg = false;
            } else if (!seenArg && current == ' ') {
                entries.add(new PropertyEntry(string.substring(i)));
                string = string.substring(0, i);
            }
            i--;
        }
        entries.add(new PropertyEntry(string));

        return entries;
    }
}

