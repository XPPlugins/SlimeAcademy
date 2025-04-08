package me.xpyex.plugin.slimeacademy.bukkit.skill.meltdowner;

import me.xpyex.plugin.slimeacademy.bukkit.ability.Ability;
import me.xpyex.plugin.slimeacademy.bukkit.skill.AcademySkill;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ScatterBomb extends AcademySkill {

    protected ScatterBomb() {
        super(2, Ability.MeltDowner);
    }

    @Override
    public void onRegister() {

    }

    @Override
    public void onExecute(Player player) {

    }

    @Override
    public int getCostMana() {
        return 0;
    }

    @Override
    public int getCostCooldown() {
        return 0;
    }

    @Override
    public ItemStack getIcon() {
        return null;
    }
}
