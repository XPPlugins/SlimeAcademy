package me.xpyex.plugin.slimeacademy.bukkit.implementation;

import lombok.Getter;
import me.xpyex.plugin.slimeacademy.bukkit.skill.AcademySkill;
import org.bukkit.inventory.ItemStack;

@Getter
public class SkillItem extends ItemStack {
    private final AcademySkill skill;

    private SkillItem(AcademySkill skill) {
        this.skill = skill;
        var meta = this.getItemMeta();
    }
}
