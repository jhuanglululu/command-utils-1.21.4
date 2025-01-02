package jhuanglululu.cmut.commands.docs.property;

import jhuanglululu.cmut.commands.utils.LongText;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class Deprecated implements PropertyInterface{

    private final String args;

    public Deprecated(String args) {
        this.args = args;
    }

    @Override
    public MutableText toText() {

        if (args != null) {
            // If argument is provided
            ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/docs " + args);
            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, LongText.of(List.of(
                    Text.translatable("command-utils.command.docs.property.deprecated.hover").formatted(Formatting.YELLOW),
                    Text.literal(args).formatted(Formatting.WHITE))));

            return LongText.of(List.of(
                    Text.literal(" [").formatted(Formatting.GRAY),
                    Text.translatable("command-utils.command.docs.property.deprecated").formatted(Formatting.RED)
                            .styled(style -> style.withHoverEvent(hoverEvent).withClickEvent(clickEvent)),
                    Text.literal("] ").formatted(Formatting.GRAY)));
        } else {
            // If no argument is provided
            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    Text.translatable("command-utils.command.docs.property.deprecated.hover-null").formatted(Formatting.YELLOW));

            return LongText.of(List.of(
                    Text.literal(" [").formatted(Formatting.GRAY),
                    Text.translatable("command-utils.command.docs.property.deprecated").formatted(Formatting.RED)
                            .styled(style -> style.withHoverEvent(hoverEvent)),
                    Text.literal("] ").formatted(Formatting.GRAY)));
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
