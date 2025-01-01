package jhuanglululu.cmut.commands.utils;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;

public class LongText {

    public LongText() { throw new AssertionError(); }

    public static MutableText of(List<MutableText> contents) {
        if (contents.size() == 1)  return contents.getFirst();

        MutableText result = Text.empty();

        for (Text content : contents) {
            result.append(content);
        }

        return result;
    }

    public static MutableText of(List<MutableText> contents, String delimiter) {
        if (contents.size() == 1)  return contents.getFirst();

        MutableText result = Text.empty();
        result.append(contents.getFirst());
        MutableText delimiterText = Text.literal(delimiter);

        for (int i = 1; i < contents.size(); i++) {
            result.append(delimiterText);
            result.append(contents.get(i));
        }

        return result;
    }
}
