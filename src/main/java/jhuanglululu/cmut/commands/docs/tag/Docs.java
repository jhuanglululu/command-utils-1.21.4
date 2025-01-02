package jhuanglululu.cmut.commands.docs.tag;

import jhuanglululu.cmut.commands.docs.property.PropertyInterface;
import jhuanglululu.cmut.commands.docs.property.PropertyParser;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;

public class Docs implements TagInterface{
    private final List<PropertyInterface> properties;

    public Docs(String p, List<String> c) {
        properties = PropertyParser.parse(p);
    }

    @Override
    public MutableText toText() {
        MutableText result = Text.empty();

        if (properties != null) {
            for (PropertyInterface property : properties) {
                result.append(property.toText());
            }
        }

        return result;
    }

    @Override
    public int getPriority() { return 0; }
}
