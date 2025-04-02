package me.xpyex.plugin.slimeacademy.bukkit.listener;

import me.xpyex.plugin.slimeacademy.bukkit.ability.Ability;
import me.xpyex.plugin.slimeacademy.bukkit.implementation.AcademyPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class SkillUseListener implements Listener {
    @EventHandler
    public void onChangeSkillMode(PlayerSwapHandItemsEvent event) {
        if (event.getPlayer().isSneaking()) {  //Shift+F
            AcademyPlayer academyPlayer = AcademyPlayer.getPlayer(event.getPlayer().getUniqueId());  //装箱
            if (academyPlayer.getData().getAbility() != Ability.NotDevelopedYet) {  //玩家不是无能力者
                event.setCancelled(true);
                if (academyPlayer.isAcademyEnabled()) {  //玩家当前处于能力模式
                    academyPlayer.setAcademyEnabled(false);  //先关闭
                    academyPlayer.getPlayer().getInventory().setHeldItemSlot(academyPlayer.getLastSlot());  //恢复玩家之前选中的格子
                } else {
                    academyPlayer.setLastSlot(event.getPlayer().getInventory().getHeldItemSlot());  //保存玩家当前选中的格子
                    academyPlayer.getPlayer().getInventory().setHeldItemSlot(0);  //默认玩家手选择到第一格，预留出玩家使用技能的空间。第一格将会填充为“不使用技能”槽
                    academyPlayer.setAcademyEnabled(true);  //切换
                }
                academyPlayer.sendAction("&a已" + (academyPlayer.isAcademyEnabled() ? "&6开启" : "&c关闭") + "&a超能力模式");
            }
        }
    }

    @EventHandler
    public void onChangeHand(PlayerItemHeldEvent event) {
        AcademyPlayer academyPlayer = AcademyPlayer.getPlayer(event.getPlayer());
        if (academyPlayer.isAcademyEnabled()) {  //需要为能力者才能开启能力，无需判定能力
            event.setCancelled(true);  //无需切换格子，计数+1即可，还可以判断蓄力。此处 TODO
            if (event.getNewSlot() + 1 <= academyPlayer.getData().getSkillSelectQueue().size()) {  //选中的格子是否有技能？
                academyPlayer.getData().getSkillSelectQueue().get(event.getNewSlot())
                    .onExecute(event.getPlayer());  //执行技能，或是蓄力, TODO
            }
        }
    }
}
