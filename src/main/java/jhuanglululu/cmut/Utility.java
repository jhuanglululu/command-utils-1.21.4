package jhuanglululu.cmut;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class Utility {

    public static MinecraftServer server;

    public static void sendMessage(Text t) {
        if(server != null && server.isRunning()) {
            for (ServerPlayerEntity player : Utility.server.getPlayerManager().getPlayerList()) {
                if (Utility.server.getPlayerManager().isOperator(player.getGameProfile())) {
                    player.sendMessage(t);
                }
            }
        }
    }

    public static String removeEx(String msg) {
        msg = msg.substring(msg.indexOf(": ") + 2);
        msg = msg.replace("Use JsonReader.setLenient(true) to accept m", "M");
        return msg;
    }

    public static int countChar(String string, char ch) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ch) {
                count++;
            }
        }
        return count;
    }
}