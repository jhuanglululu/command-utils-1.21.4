package jhuanglululu.cmut.commands.docs;

import java.util.List;

public class TagEntry {
    private final String tag;
    private final List<PropertyEntry> property;
    private final List<String> content;

    public TagEntry(String tag, List<PropertyEntry> property, List<String> content) {
        this.tag = tag;
        this.property = property;
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public List<PropertyEntry> getProperty() {
        return property;
    }

    public List<String> getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "TagEntry{" +
                "tag='" + tag + '\'' +
                ", property='" + property + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
