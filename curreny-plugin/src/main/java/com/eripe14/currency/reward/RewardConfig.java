package com.eripe14.currency.reward;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.List;

@Header("This is the reward configuration section.")
public class RewardConfig extends OkaeriConfig {

    private final List<Reward> reward = List.of(
            new Reward(1, 10, "currency give {player} 100", Material.DIAMOND_ORE, RewardType.MINING),
            new Reward(2, 10, "currency give {player} 100", Material.EMERALD_ORE, RewardType.MINING),
            new Reward(3, 10, "currency give {player} 100", EntityType.ZOMBIE, RewardType.KILLING),
            new Reward(4, 10, "currency give {player} 100", EntityType.SKELETON, RewardType.KILLING),
            new Reward(5, 1, "currency give {player} 100", "NONE", RewardType.TRADING)
    );

    public List<Reward> getReward(RewardType rewardType) {
        return this.reward.stream()
                .filter(reward -> reward.getType() == rewardType)
                .toList();
    };

}