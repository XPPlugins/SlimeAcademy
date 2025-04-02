package me.xpyex.plugin.slimeacademy.bukkit.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import me.xpyex.plugin.slimeacademy.bukkit.SlimeAcademy;
import me.xpyex.plugin.slimeacademy.bukkit.ability.Ability;
import me.xpyex.plugin.slimeacademy.bukkit.skill.AcademySkill;
import me.xpyex.plugin.slimeacademy.bukkit.util.GsonUtil;

@Getter
@Setter
public class PlayerData {
    private Ability ability = Ability.NotDevelopedYet;
    private Set<String> skills = new HashSet<>();
    private int level = 0;
    private Map<String, Integer> skillMastery = new HashMap<>();  //技能熟练度
    private List<String> skillSelectQueue = new ArrayList<>();  //玩家自定义技能在哪个槽位

    public Set<AcademySkill> getLearnedSkills() {
        return skills.stream()
                   .map(AcademySkill::getSkill)
                   .filter(Objects::nonNull)
                   .collect(Collectors.toSet());
    }

    public Map<AcademySkill, Integer> getSkillMastery() {
        Map<AcademySkill, Integer> map = new HashMap<>();
        skillMastery.forEach((skillName, mastery) -> {
            AcademySkill skill = AcademySkill.getSkill(skillName);
            if (skill != null) {
                map.put(skill, mastery);
            }
        });
        return map;
    }

    public List<AcademySkill> getSkillSelectQueue() {
        return skillSelectQueue.stream()
            .map(AcademySkill::getSkill)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public boolean save(UUID uuid) {
        try {
            File playerDataFile = new File(SlimeAcademy.getInstance().getDataFolder(), "data" + File.separator + uuid + ".json");
            if (!playerDataFile.exists()) playerDataFile.createNewFile();
            Files.writeString(playerDataFile.toPath(), GsonUtil.toJson(this), StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
