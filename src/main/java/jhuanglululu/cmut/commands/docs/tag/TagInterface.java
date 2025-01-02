package jhuanglululu.cmut.commands.docs.tag;

import net.minecraft.text.MutableText;

public interface TagInterface {

    MutableText toText();

    int getPriority();
}
