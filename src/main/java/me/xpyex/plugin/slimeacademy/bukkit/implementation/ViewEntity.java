package me.xpyex.plugin.slimeacademy.bukkit.implementation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.xpyex.plugin.slimeacademy.bukkit.SlimeAcademy;
import me.xpyex.plugin.slimeacademy.bukkit.util.VectorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
public class ViewEntity {
    private final Entity entity;
    private ViewType type = ViewType.RELEASE;
    private final HashSet<UUID> viewers = new HashSet<>();

    private ViewEntity(Location loc, EntityType type) {
        this(loc.getWorld().spawnEntity(loc, type));
    }

    public static ViewEntity of(Location location, EntityType showType) {
        return new ViewEntity(location, showType);
    }

    public void onlyShowTo(Player... players) {
        viewers.clear();
        showTo(players);
    }

    public void showTo(Player... players) {
        if (players == null || players.length == 0) {  //如果这个列表为空，则展示给所有玩家
            Bukkit.getOnlinePlayers().forEach(p -> p.showEntity(SlimeAcademy.getInstance(), getEntity()));
            return;
        }
        viewers.addAll(Arrays.stream(players).map(Player::getUniqueId).toList());
        new BukkitRunnable() {  //TODO 日后应当更换到仅一个异步任务内运行，而非新建一个展示实体就新建任务，这样开销过大。当前只是写得顺手
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().isEmpty()) {  //没有玩家在线了
                    cancel();
                    setType(ViewType.REMOVE);  //展示实体是展示给玩家的，无人在线就不再展示
                    return;
                }
                if (viewers.size() != players.length) {  //修改过玩家列表，取消当前任务，新的任务会处理
                    cancel();
                    return;
                }
                if (!getEntity().isValid()) {
                    cancel();
                    return;
                }
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if (viewers.contains(p.getUniqueId())) {
                        p.showEntity(SlimeAcademy.getInstance(), getEntity());
                    } else {
                        p.hideEntity(SlimeAcademy.getInstance(), getEntity());
                    }
                });
            }
        }.runTaskTimerAsynchronously(SlimeAcademy.getInstance(), 0, 1);
    }

    public ViewEntity focusEntityAtView(Player target, double distance, boolean ignoreBlock) {
        setType(ViewType.FOCUS);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!ViewEntity.this.getEntity().isValid() || !target.isValid() || !target.isOnline()) {
                    cancel();
                    return;
                }
                switch (ViewEntity.this.getType()) {
                    case FOCUS:
                        if (!ignoreBlock) {
                            Block targetBlockExact = target.getTargetBlockExact(Math.toIntExact(Math.round(distance)));
                            if (targetBlockExact != null && targetBlockExact.getType().isSolid()) {
                                if (target.getLocation().distance(targetBlockExact.getLocation()) < distance) {
                                    ViewEntity.this.getEntity().teleport(targetBlockExact.getLocation());
                                    return;
                                }
                            }
                        }
                        ViewEntity.this.getEntity().teleport(ViewEntity.this.getEntity().getLocation().add(VectorUtil.getVectorFromYawAndPitch(target, distance)));
                        break;
                    case REMOVE:
                        ViewEntity.this.getEntity().remove();
                        cancel();
                        return;
                    case RELEASE:
                        cancel();
                        return;
                    case FOLLOW:
                        ViewEntity.this.getEntity().teleport(target.getLocation());
                        break;
                }
            }
        }.runTaskTimerAsynchronously(SlimeAcademy.getInstance(), 0, 1);
        return this;
    }

    public enum ViewType {
        FOCUS,
        FOLLOW,
        RELEASE,
        REMOVE
    }
}
