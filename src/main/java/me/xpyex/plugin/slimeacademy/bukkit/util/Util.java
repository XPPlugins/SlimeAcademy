package me.xpyex.plugin.slimeacademy.bukkit.util;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class Util {
    @NotNull
    public static String getColorStr(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
