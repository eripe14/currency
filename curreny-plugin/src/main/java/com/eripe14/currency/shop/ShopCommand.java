package com.eripe14.currency.shop;

import com.eripe14.currency.CurrencyService;
import com.eripe14.currency.notice.NoticeService;
import com.eternalcode.multification.shared.Formatter;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Command(name = "shop")
public class ShopCommand {

    private final ShopItemConfig shopItemConfig;
    private final CurrencyService currencyService;
    private final NoticeService noticeService;

    public ShopCommand(ShopItemConfig shopItemConfig, CurrencyService currencyService, NoticeService noticeService) {
        this.shopItemConfig = shopItemConfig;
        this.currencyService = currencyService;
        this.noticeService = noticeService;
    }

    @Execute(name = "list")
    @Permission("currency.shop.list")
    void list(@Context Player player) {
        Formatter formatter = new Formatter();

        this.noticeService.create()
                .notice(messages -> messages.shopListHeader)
                .player(player.getUniqueId())
                .send();

        this.shopItemConfig.getItems().forEach(shopItem -> {
            formatter.register("{price}", shopItem.getPrice());
            formatter.register("{material}", shopItem.getMaterial().name().toLowerCase().replace("_", " "));

            this.noticeService.create()
                    .notice(messages -> messages.shopListEntry)
                    .formatter(formatter)
                    .player(player.getUniqueId())
                    .send();
        });
    }

    @Execute(name = "buy")
    @Permission("currency.shop.buy")
    void buy(@Context Player player, @Arg Material material) {
        UUID playerUniqueId = player.getUniqueId();
        Formatter formatter = new Formatter();
        formatter.register("{material}", material.name().toLowerCase().replace("_", " "));

        this.shopItemConfig.getItem(material).ifPresentOrElse(shopItem -> {
            if (!this.currencyService.canUserAfford(playerUniqueId, shopItem.getPrice())) {
                this.noticeService.create()
                        .notice(messages -> messages.notEnoughMoney)
                        .player(playerUniqueId)
                        .send();
                return;
            }

            formatter.register("{price}", shopItem.getPrice());

            player.getInventory().addItem(new ItemStack(material, 1));

            this.currencyService.processUserBalance(playerUniqueId, balance -> balance - shopItem.getPrice());
            this.noticeService.create()
                    .notice(messages -> messages.boughtItem)
                    .player(playerUniqueId)
                    .formatter(formatter)
                    .send();
        }, () -> this.noticeService.create()
                .notice(messages -> messages.cannotFindItem)
                .player(playerUniqueId)
                .formatter(formatter)
                .send()
        );
    }

}