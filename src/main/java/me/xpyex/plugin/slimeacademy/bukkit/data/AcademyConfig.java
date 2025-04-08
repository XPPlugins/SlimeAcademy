package me.xpyex.plugin.slimeacademy.bukkit.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.xpyex.plugin.slimeacademy.bukkit.SlimeAcademy;
import me.xpyex.plugin.slimeacademy.bukkit.skill.AcademySkill;
import me.xpyex.plugin.slimeacademy.bukkit.util.GsonUtil;

@Getter
@Setter
@Accessors(chain = true)
public class AcademyConfig {
    private static final File CONFIG_FILE = new File(SlimeAcademy.getInstance().getDataFolder(), "config.json");
    private static AcademyConfig current;
    private Set<String> bannedWorlds = new HashSet<>();
    private Set<String> bannedSkills = new HashSet<>();

    public Set<AcademySkill> getBannedSkills() {
        return bannedSkills.stream()
                   .map(AcademySkill::getSkill)
                   .filter(Objects::nonNull)
                   .collect(Collectors.toSet());
    }

    public static AcademyConfig load() {
        if (!CONFIG_FILE.exists()) {
            new AcademyConfig().save();
        }
        current = GsonUtil.fromJson(CONFIG_FILE, AcademyConfig.class);
        return current;
    }

    public boolean save() {
        try {
            if (!CONFIG_FILE.exists()) CONFIG_FILE.createNewFile();
            Files.writeString(CONFIG_FILE.toPath(), GsonUtil.toJson(this), StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public enum Language {
        zh_cn
    }
}
