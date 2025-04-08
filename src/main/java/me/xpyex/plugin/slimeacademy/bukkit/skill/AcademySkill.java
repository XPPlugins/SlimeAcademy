package me.xpyex.plugin.slimeacademy.bukkit.skill;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import me.xpyex.plugin.slimeacademy.bukkit.ability.Ability;
import me.xpyex.plugin.slimeacademy.bukkit.implementation.SkillItem;
import me.xpyex.plugin.slimeacademy.bukkit.util.ValueUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class AcademySkill {
    private static final HashMap<String, AcademySkill> SKILLS = new HashMap<>();
    private final int level;
    private final Ability ability;

    protected AcademySkill(int level, Ability ability) {
        ValueUtil.mustTrue("等级范围x必须符合 1 ≤ x ≤ 5", level > 0, level < 6);
        ValueUtil.mustTrue("技能必须归属于某个能力", ability != null, ability != Ability.NotDevelopedYet);
        this.ability = ability;
        this.level = level;
        SKILLS.put(getClass().getSimpleName(), this);
    }

    public static AcademySkill getSkill(String skillName) {
        if (skillName == null || skillName.trim().isEmpty()) return null;
        if (SKILLS.containsKey(skillName)) return SKILLS.get(skillName);
        return SKILLS.entrySet().stream()
            .filter(entry -> entry.getKey().equalsIgnoreCase(skillName))
            .findAny()
            .map(Map.Entry::getValue)
            .orElse(null);
    }

    public abstract void onRegister();

    public abstract void onTick(Player player);

    public abstract boolean tickedDone(Player player);

    public abstract void onExecute(Player player);

    /**
     * 获取玩家目前选择的所有的目标
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
     * 玩家使用该技能需要耗费的能量值，总值到0则力竭
     * 可能需要根据技能是否蓄力扣除不同的魔力值
     *
     * @return 消耗的能量值
     */
    public abstract int getCostMana();

    /**
     * 玩家使用该技能会增加冷却条，冷却条满了则过载(无法使用能力)30秒
     * 可能需要根据技能是否蓄力增加不同的冷却进度
     *
     * @return 新增的冷却条进度
     */
    public abstract int getCostCooldown();

    public abstract ItemStack getIcon();
}
