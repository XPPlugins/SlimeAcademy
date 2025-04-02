package me.xpyex.plugin.slimeacademy.bukkit.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.jetbrains.annotations.Nullable;

public class GsonUtil {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public static Gson getGson() {
        return GSON;
    }

    public static String toJson(Object obj) {
        return getGson().toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return getGson().fromJson(json, clazz);
    }

    @Nullable
    public static <T> T fromJson(File file, Class<T> clazz) {
        try {
            return fromJson(Files.readString(file.toPath(), StandardCharsets.UTF_8), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
