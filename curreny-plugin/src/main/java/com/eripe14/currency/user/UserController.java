package com.eripe14.currency.user;

import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.Nullable;

public class UserController implements Listener {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventHandler
    void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        User user = this.userRepository.findOrCreateByPath(player.getUniqueId());

        if (user.getUserStatistics() == null) {
            user.setUserStatistics(new UserStatistics());
        }

        user.setName(player.getName());
        user.save();
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        User user = this.userRepository.findOrCreateByPath(player.getUniqueId());
        user.save();
    }

    @EventHandler
    void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        User user = this.userRepository.findOrCreateByPath(player.getUniqueId());

        user.getUserStatistics().incrementMinedBlocks();
        user.save();
    }

    @EventHandler
    void onEntityDeath(EntityDeathEvent event) {
        @Nullable Player killer = event.getEntity().getKiller();

        if (killer == null) {
            return;
        }

        User user = this.userRepository.findOrCreateByPath(killer.getUniqueId());

        user.getUserStatistics().incrementKilledMobs();
        user.save();
    }

    @EventHandler
    void onTrade(PlayerTradeEvent event) {
        Player player = event.getPlayer();
        User user = this.userRepository.findOrCreateByPath(player.getUniqueId());

        user.getUserStatistics().incrementTrades();
        user.save();
    }
}