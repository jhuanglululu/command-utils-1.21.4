package jhuanglululu.cmut.mixin.loader;

import com.google.common.collect.ImmutableMap;
import jhuanglululu.cmut.Utility;
import jhuanglululu.cmut.commands.utils.LongText;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.FunctionLoader;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(FunctionLoader.class)
public class FunctionMixin {
    @Inject(
            method = "method_29457",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            )
    )
    private static void error(Identifier id, ImmutableMap.Builder<Identifier, CommandFunction<ServerCommandSource>> immBuilder, CommandFunction<ServerCommandSource> commandFunction, Throwable ex, CallbackInfoReturnable<Object> ci) {
        Text t = LongText.of(List.of(
                Text.literal("[Datapack/WARN]").formatted(Formatting.YELLOW),
                Text.literal(" (command-utils) ").formatted(Formatting.AQUA),
                Text.translatable("command-utils.error.parse-function", id.toString()).formatted(Formatting.WHITE).styled(style ->
                    style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            Text.literal(Utility.removeEx(ex.getMessage())).formatted(Formatting.RED))
                    )
                )
        ));
        Utility.sendMessage(t);
    }
}