package com.eripe14.currency.reward;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class Reward extends OkaeriConfig {

    private int id;
    private int goalAmount;
    private String command;
    private Object filter;
    private RewardType type;

    public Reward(int id, int goalAmount, String command, Object filter, RewardType type) {
        this.id = id;
        this.goalAmount = goalAmount;
        this.command = command;
        this.filter = filter;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getGoalAmount() {
        return goalAmount;
    }

    public String getCommand() {
        return command;
    }

    public RewardType getType() {
        return type;
    }

    public Material getMaterial() {
        return type == RewardType.MINING ? Material.getMaterial(this.filter.toString()) : null;
    }

    public EntityType getEntityType() {
        return type == RewardType.KILLING ? EntityType.fromName(this.filter.toString()) : null;
    }
}