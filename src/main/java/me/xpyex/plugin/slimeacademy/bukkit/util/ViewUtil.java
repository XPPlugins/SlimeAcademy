package me.xpyex.plugin.slimeacademy.bukkit.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.xpyex.plugin.slimeacademy.bukkit.SlimeAcademy;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ViewUtil {
    public static ViewEntity focusEntityAtView(Player target, EntityType showType, double distance, boolean ignoreBlock) {
        ViewEntity view = ViewEntity.of(target.getLocation(), showType);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!view.getEntity().isValid() || !target.isValid() || !target.isOnline()) {
                    cancel();
                    return;
                }
                switch (view.getType()) {
                    case FOCUS:
                        if (!ignoreBlock) {
                            Block targetBlockExact = target.getTargetBlockExact(Math.toIntExact(Math.round(distance)));
                            if (targetBlockExact != null && targetBlockExact.getType().isSolid()) {
                                if (target.getLocation().distance(targetBlockExact.getLocation()) < distance) {
                                    view.getEntity().teleport(targetBlockExact.getLocation());
                                    return;
                                }
                            }
                        }
                        view.getEntity().teleport(view.getEntity().getLocation().add(VectorUtil.getVectorFromYawAndPitch(target, distance)));
                        break;
                    case REMOVE:
                        view.getEntity().remove();
                        cancel();
                        return;
                    case RELEASE:
                        cancel();
                        return;
                    case FOLLOW:
                        view.getEntity().teleport(target.getLocation());
                        break;
                }
            }
        }.runTaskTimerAsynchronously(SlimeAcademy.getInstance(), 0, 1);
        return view;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor(staticName = "of")
    public static class ViewEntity {
        private final Entity entity;
        private ViewType type = ViewType.FOCUS;

        private ViewEntity(Location loc, EntityType type) {
            this(loc.getWorld().spawnEntity(loc, type));
        }

        public static ViewEntity of(Location location, EntityType showType) {
            return new ViewEntity(location, showType);
        }
    }

    public enum ViewType {
        FOCUS,
        FOLLOW,
        RELEASE,
        REMOVE
    }
}
