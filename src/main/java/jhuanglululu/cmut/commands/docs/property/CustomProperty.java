package jhuanglululu.cmut.commands.docs.property;

import jhuanglululu.cmut.commands.utils.LongText;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CustomProperty implements PropertyInterface{

    private final String name, args;

    public CustomProperty(String name, String args) {
        this.name = name;
        this.args = args;
    }

    @Override
    public MutableText toText() {

        if (args != null) {
            // If argument is provided
            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal(args).formatted(Formatting.WHITE));

            return LongText.of(List.of(
                    Text.literal(" [").formatted(Formatting.GRAY),
                    Text.literal(name).formatted(Formatting.YELLOW)
                            .styled(style -> style.withHoverEvent(hoverEvent)),
                    Text.literal("] ").formatted(Formatting.GRAY)));
        } else {
            return LongText.of(List.of(
                    Text.literal(" [").formatted(Formatting.GRAY),
                    Text.literal(name).formatted(Formatting.YELLOW),
                    Text.literal("] ").formatted(Formatting.GRAY)));
        }
    }

    @Override
    public int getPriority() {
        return 10;
    }
}
