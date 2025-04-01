package com.eripe14.currency.shop;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import org.bukkit.Material;

import java.util.List;
import java.util.Optional;

@Header("This is the configuration section for the shop items.")
public class ShopItemConfig extends OkaeriConfig {

    public final List<ShopItem> items = List.of(
        new ShopItem(Material.DIAMOND_SWORD, 100),
        new ShopItem(Material.DIAMOND_CHESTPLATE, 200),
        new ShopItem(Material.DIAMOND_LEGGINGS, 150),
        new ShopItem(Material.DIAMOND_BOOTS, 50),
        new ShopItem(Material.DIAMOND_HELMET, 75)
    );

    public List<ShopItem> getItems() {
        return this.items;
    }

    public Optional<ShopItem> getItem(Material material) {
        return this.items.stream().filter(item -> item.getMaterial().equals(material)).findFirst();
    }

}