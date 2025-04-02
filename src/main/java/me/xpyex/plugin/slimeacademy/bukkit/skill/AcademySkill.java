package me.xpyex.plugin.slimeacademy.bukkit.skill;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class AcademySkill {
    private static final HashMap<String, AcademySkill> SKILLS = new HashMap<>();

    public static AcademySkill getSkill(String skillName) {
        return SKILLS.get(skillName.toLowerCase());
    }

    public abstract void onRegister();

    public abstract void onExecute(Player player);

    /**
     * 获取玩家选择的所有的目标
     * 默认为空List
     *
     * @return 目标
     */
    @NotNull
    public List<Object> getTargets(Player player) {
        return Collections.emptyList();
    }

    /**
     * 当玩家选择能力作用对象时，提示玩家的消息
     * 此List的size将会作为玩家完成全部选择的判定方式
     * 当size为0时，将不会提示任何消息，也不触发玩家选择，默认点击则选
     * 默认为空List
     *
     * @return 提示消息
     */
    @NotNull
    public List<String> getSelectNotice(Player player) {
        return Collections.emptyList();
    }

    /**
     * 该技能应当在能力者达到哪个Level才能使用
     *
     * @return 最低限度
     */
    public abstract int getLevel();

    /**
     * 玩家使用该技能需要耗费的能量值，总值到0则力竭
     *
     * @return 消耗的能量值
     */
    public abstract int getCostMana();

    /**
     * 玩家使用该技能会增加冷却条，冷却条满了会无法使用能力30秒
     *
     * @return 新增的冷却条进度
     */
    public abstract int getCostCooldown();

    public abstract ItemStack getIcon();
}
