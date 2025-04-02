package me.xpyex.plugin.slimeacademy.bukkit.implementation;

import java.io.File;
import java.util.UUID;
import java.util.WeakHashMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import me.xpyex.plugin.slimeacademy.bukkit.SlimeAcademy;
import me.xpyex.plugin.slimeacademy.bukkit.data.PlayerData;
import me.xpyex.plugin.slimeacademy.bukkit.util.GsonUtil;
import me.xpyex.plugin.slimeacademy.bukkit.util.Util;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class AcademyPlayer {
    private static final WeakHashMap<UUID, AcademyPlayer> CACHE = new WeakHashMap<>();
    private PlayerData data = null;
    private final UUID playerUUID;
    private boolean academyEnabled = false;
    private int lastSlot = 0;

    @NotNull
    @SneakyThrows
    public static AcademyPlayer getPlayer(UUID playerUUID) {
        if (CACHE.containsKey(playerUUID)) {
            return CACHE.get(playerUUID);
        }

        File playerDataFile = new File(SlimeAcademy.getInstance().getDataFolder(), "data" + File.separator + playerUUID + ".json");
        if (!playerDataFile.exists()) new PlayerData().save(playerUUID);
        PlayerData dataInFile = GsonUtil.fromJson(playerDataFile, PlayerData.class);
        var ret = of(playerUUID).setData(dataInFile);
        CACHE.put(playerUUID, ret);
        return ret;
    }

    public static AcademyPlayer getPlayer(Player player) {
        return getPlayer(player.getUniqueId());
    }

    public void sendMessage(String... messages) {
        for (String message : messages) {
            if (message == null) continue;
            getPlayer().sendMessage(Util.getColorStr(message));
        }
    }

    public void sendAction(String message) {
        getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Util.getColorStr(message)));
    }

    public Player getPlayer() {  //玩家重生，或者退出重进的时候，实例会变化，原来实例会清空
        return Bukkit.getPlayer(getPlayerUUID());
    }
}
