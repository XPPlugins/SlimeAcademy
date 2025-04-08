package me.xpyex.plugin.slimeacademy.bukkit.data;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbilityInfo {
    private String name;
    private List<String> description;
    private Map<String, SkillInfo> skills;

    public static class SkillInfo {

    }
}
