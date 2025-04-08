package me.xpyex.plugin.slimeacademy.bukkit.util;

public class ValueUtil {
    public static void notNull(String message, Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    public static void mustTrue(String message, boolean... condition) {
        for (boolean b : condition) {
            if (!b) {
                throw new IllegalStateException(message);
            }
        }
    }
}
