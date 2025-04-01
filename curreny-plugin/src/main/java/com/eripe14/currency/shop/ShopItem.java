package com.eripe14.currency.shop;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;

public class ShopItem extends OkaeriConfig {

    private Material material;
    private double price;

    public ShopItem(Material material, double price) {
        this.material = material;
        this.price = price;
    }

    public Material getMaterial() {
        return this.material;
    }

    public double getPrice() {
        return this.price;
    }

}