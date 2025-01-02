package jhuanglululu.cmut.commands.docs.property;

import jhuanglululu.cmut.commands.utils.LongText;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class Abstract implements PropertyInterface{

    public Abstract(String args) {}

    @Override
    public MutableText toText() {
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                Text.translatable("command-utils.command.docs.property.abstract.hover").formatted(Formatting.YELLOW));

        return LongText.of(List.of(
                Text.literal(" [").formatted(Formatting.GRAY),
                Text.translatable("command-utils.command.docs.property.abstract").formatted(Formatting.GREEN)
                        .styled(style -> style.withHoverEvent(hoverEvent)),
                Text.literal("] ").formatted(Formatting.GRAY)
        ));
    }

    @Override
    public int getPriority() {
        return 1;
    }
}