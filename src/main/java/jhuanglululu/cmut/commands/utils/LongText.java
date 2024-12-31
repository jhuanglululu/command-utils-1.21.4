package jhuanglululu.cmut.commands.utils;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;

public class LongText {

    public LongText() { throw new AssertionError(); }

    public static Text of(List<MutableText> contents) {
        MutableText result = Text.literal("");

        for (MutableText content : contents) {
            result.append(content);
        }

        return result;
    }
}
