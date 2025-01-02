package jhuanglululu.cmut.commands.docs.tag;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class Kpop implements TagInterface{

    public Kpop(String p, List<String> c) {}

    @Override
    public MutableText toText() {
        MutableText result = Text.empty().append(Text.literal("薑黃的推").formatted(Formatting.GOLD, Formatting.BOLD));

        result.append(Text.literal("   AHYEON!!! YUQI!!! CHAEWON!!! EUNCHAE!!!").formatted(Formatting.BOLD, Formatting.AQUA));

        return result;
    }

    @Override
    public int getPriority() { return 2; }
}
