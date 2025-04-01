package com.eripe14.currency.config.implementation;

import com.eripe14.currency.reward.RewardConfig;
import com.eripe14.currency.shop.ShopItemConfig;
import eu.okaeri.configs.OkaeriConfig;

public class PluginConfig extends OkaeriConfig {

    public StorageConfig storage = new StorageConfig();

    public RewardConfig rewardConfig = new RewardConfig();

    public ShopItemConfig shopItemConfig = new ShopItemConfig();

    public static class StorageConfig extends OkaeriConfig {
        public String prefix = "currency";

        //jdbc:mysql://{host}:{port}/{database}?user={username}&password={password}
        public String url = "jdbc:mysql://localhost:3306/currency?user=root&password=";

    }

}