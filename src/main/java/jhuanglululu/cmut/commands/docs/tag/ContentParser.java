package jhuanglululu.cmut.commands.docs.tag;

import jhuanglululu.cmut.commands.utils.LongText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class ContentParser {
    public static List<Content> parse(List<String> input) {
        List<Content> result = new ArrayList<>();

        if (input == null) {
            return null;
        }

        for (String line : input) {
            String[] lineList = line.split(" -> ", 2);
            if (lineList.length > 1) {
                result.add(new Content(lineList[0], lineList[1]));
            } else {
                result.add(new Content(lineList[0]));
            }
        }

        return result;
    }

    public static class Content {
        private String variable, value;

        public Content(String val) {
            variable = null;
            value = val;
        }

        public Content(String var, String val) {
            variable = var;
            value = val;
        }

        public MutableText toText() {
            if (variable == null) {
                return Text.literal(value);
            } else {
                return LongText.of(List.of(
                        Text.literal(variable).formatted(Formatting.YELLOW),
                        Text.literal(": ").formatted(Formatting.YELLOW),
                        Text.literal(value).formatted(Formatting.WHITE)));
            }
        }

        public MutableText toText(String divider) {
            if (variable == null) {
                return Text.literal(value);
            } else {
                return LongText.of(List.of(
                        Text.literal(variable).formatted(Formatting.YELLOW),
                        Text.literal(divider).formatted(Formatting.YELLOW),
                        Text.literal(value).formatted(Formatting.WHITE)));
            }
        }

        public MutableText toText(String color, String divider) {
            if (variable == null) {
                return Text.literal(value);
            } else {
                return LongText.of(List.of(
                        Text.literal(variable).formatted(Formatting.valueOf(color.toUpperCase())),
                        Text.literal(divider).formatted(Formatting.valueOf(color.toUpperCase())),
                        Text.literal(value).formatted(Formatting.WHITE)));
            }
        }

        public MutableText toText(String color1, String color2, String divider) {
            if (variable == null) {
                return Text.literal(value).formatted(Formatting.valueOf(color2.toUpperCase()));
            } else {
                return LongText.of(List.of(
                        Text.literal(variable).formatted(Formatting.valueOf(color1.toUpperCase())),
                        Text.literal(divider).formatted(Formatting.valueOf(color1.toUpperCase())),
                        Text.literal(value).formatted(Formatting.valueOf(color2.toUpperCase()))));
            }
        }
    }
}
