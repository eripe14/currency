package com.eripe14.currency.currency;

import com.eripe14.currency.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "currency")
public class CurrencyCommand {

    private final CurrencyServiceImpl currencyService;
    private final NoticeService noticeService;

    public CurrencyCommand(CurrencyServiceImpl currencyService, NoticeService noticeService) {
        this.currencyService = currencyService;
        this.noticeService = noticeService;
    }

    @Execute(name = "give")
    @Permission("currency.give")
    void give(@Context CommandSender sender, @Arg Player target, @Arg double amount) {
        this.currencyService.processUserBalance(target.getUniqueId(), balance -> balance + amount);

        if (sender instanceof Player player) {
            this.noticeService.create()
                    .notice(messages -> messages.giveMoney)
                    .placeholder("{amount}", String.valueOf(amount))
                    .placeholder("{player}", target.getName())
                    .player(player.getUniqueId())
                    .send();
        }

        this.noticeService.create()
                .notice(messages -> messages.receivedMoney)
                .placeholder("{amount}", String.valueOf(amount))
                .player(target.getUniqueId())
                .send();
    }

    @Execute(name = "set")
    @Permission("currency.set")
    void set(@Context CommandSender sender, @Arg Player target, @Arg double amount) {
        this.currencyService.processUserBalance(target.getUniqueId(), balance -> amount);

        if (sender instanceof Player player) {
            this.noticeService.create()
                    .notice(messages -> messages.setMoney)
                    .placeholder("{amount}", String.valueOf(amount))
                    .placeholder("{player}", target.getName())
                    .player(player.getUniqueId())
                    .send();
        }

        this.noticeService.create()
                .notice(messages -> messages.yourMoneySet)
                .placeholder("{amount}", String.valueOf(amount))
                .player(target.getUniqueId())
                .send();
    }

    @Execute(name = "balance")
    @Permission("currency.balance")
    void balance(@Context Player player) {
        double balance = this.currencyService.getUserBalance(player.getUniqueId());

        this.noticeService.create()
                .notice(messages -> messages.balance)
                .placeholder("{amount}", String.valueOf(balance))
                .player(player.getUniqueId())
                .send();
    }

    @Execute(name = "balance")
    @Permission("currency.balance.other")
    void balanceOther(@Context Player player, @Arg Player target) {
        double balance = this.currencyService.getUserBalance(target.getUniqueId());

        this.noticeService.create()
                .notice(messages -> messages.balanceOther)
                .placeholder("{amount}", String.valueOf(balance))
                .placeholder("{player}", target.getName())
                .player(player.getUniqueId())
                .send();
    }

}