package com.eripe14.currency.reward;

import com.eripe14.currency.user.User;
import com.eripe14.currency.user.UserRepository;
import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.Nullable;

public class RewardController implements Listener {

    private final Server server;
    private final UserRepository userRepository;
    private final RewardConfig rewardConfig;

    public RewardController(
            Server server,
            UserRepository userRepository,
            RewardConfig rewardConfig
    ) {
        this.server = server;
        this.userRepository = userRepository;
        this.rewardConfig = rewardConfig;
    }

    @EventHandler
    void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        User user = this.userRepository.findOrCreateByPath(player.getUniqueId());

        for (Reward reward : this.rewardConfig.getReward(RewardType.MINING)) {
            if (reward.getMaterial() != event.getBlock().getType()) {
                continue;
            }

            int goalAmount = reward.getGoalAmount();
            int currentAmount = user.getUserStatistics().getMinedBlocks();

            if (currentAmount % goalAmount != 0) {
                continue;
            }

            this.server.dispatchCommand(
                    this.server.getConsoleSender(),
                    reward.getCommand().replace("{player}", player.getName())
            );
        }
    }

    @EventHandler
    void onEntityDeath(EntityDeathEvent event) {
        @Nullable Player killer = event.getEntity().getKiller();

        if (killer == null) {
            return;
        }

        User user = this.userRepository.findOrCreateByPath(killer.getUniqueId());

        for (Reward reward : this.rewardConfig.getReward(RewardType.KILLING)) {
            if (reward.getEntityType() != event.getEntityType()) {
                continue;
            }

            int goalAmount = reward.getGoalAmount();
            int currentAmount = user.getUserStatistics().getKilledMobs();

            if (currentAmount % goalAmount != 0) {
                continue;
            }

            this.server.dispatchCommand(
                    this.server.getConsoleSender(),
                    reward.getCommand().replace("{player}", killer.getName())
            );
        }

        user.save();
    }

    @EventHandler
    void onTrade(PlayerTradeEvent event) {
        Player player = event.getPlayer();
        User user = this.userRepository.findOrCreateByPath(player.getUniqueId());

        for (Reward reward : this.rewardConfig.getReward(RewardType.TRADING)) {
            int goalAmount = reward.getGoalAmount();
            int currentAmount = user.getUserStatistics().getTrades();

            if (currentAmount % goalAmount != 0) {
                continue;
            }

            this.server.dispatchCommand(
                    this.server.getConsoleSender(),
                    reward.getCommand().replace("{player}", player.getName())
            );
        }
    }
}