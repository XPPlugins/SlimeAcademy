package me.xpyex.plugin.slimeacademy.bukkit;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class SlimeAcademy extends JavaPlugin {
    @Getter
    private static SlimeAcademy instance;

    @Override
    public void onDisable() {
        getLogger().info("卸载中");
        getServer().getOnlinePlayers().forEach(Player::closeInventory);
    }

    @Override
    public void onEnable() {
        if (!getServer().getPluginManager().isPluginEnabled("Slimefun")) {
            getLogger().warning("未安装SlimeFun插件？");
            getLogger().warning("本插件为SlimeFun附属");
            getLogger().warning("请安装Slimefun后再次启动本插件");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;

        getLogger().info("加载中");
        getLogger().info("加载完成");
    }
}
