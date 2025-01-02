package jhuanglululu.cmut.commands.docs.property;

import net.minecraft.text.MutableText;

public interface PropertyInterface {
    public MutableText toText();

    public int getPriority();
}
