package me.xpyex.plugin.slimeacademy.bukkit.skill.meltdowner;

import java.util.stream.Stream;
import me.xpyex.plugin.slimeacademy.bukkit.skill.AcademySkill;
import me.xpyex.plugin.slimeacademy.bukkit.util.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MeltDowner extends AcademySkill {
    @Override
    public void onRegister() {

    }

    @Override
    public void onExecute(Player player) {

    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getCostMana() {
        return 50;
    }

    @Override
    public int getCostCooldown() {
        return 30;
    }

    @Override
    public ItemStack getIcon() {
        return new Icon();
    }

    public static class Icon extends ItemStack {
        public Icon() {
            super();
            setType(Material.SLIME_SPAWN_EGG);
            ItemMeta meta = getItemMeta();
            meta.setDisplayName(Util.getColorStr("&e粒机波形炮"));
            meta.setLore(Stream.of("", "&f蓄力过后释放", "&f可造成强力破坏").map(Util::getColorStr).toList());
            setItemMeta(meta);
        }
    }
}
