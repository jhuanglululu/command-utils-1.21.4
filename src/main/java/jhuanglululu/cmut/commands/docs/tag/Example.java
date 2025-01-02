package jhuanglululu.cmut.commands.docs.tag;

import jhuanglululu.cmut.Utility;
import jhuanglululu.cmut.commands.docs.property.PropertyInterface;
import jhuanglululu.cmut.commands.docs.property.PropertyParser;
import jhuanglululu.cmut.commands.utils.LongText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class Example implements TagInterface{
    private final List<PropertyInterface> properties;
    private final List<ContentParser.Content> contents;

    public Example(String p, List<String> c) {
        properties = PropertyParser.parse(p);
        contents = ContentParser.parse(c);
    }

    @Override
    public MutableText toText() {
        MutableText result = Text.empty().append(Text.translatable("command-utils.command.docs.tag.example").formatted(Formatting.GOLD, Formatting.BOLD));

        if (properties != null) {
            for (PropertyInterface property : properties) {
                result.append(property.toText());
            }
            result.append("\n");
        }

        if (contents != null) {
            for (ContentParser.Content content : contents) {
                result.append("\n");
                result.append(LongText.of(List.of(Utility.indent(1), content.toText())));
            }
        }

        return result;
    }

    @Override
    public int getPriority() { return 2; }
}
