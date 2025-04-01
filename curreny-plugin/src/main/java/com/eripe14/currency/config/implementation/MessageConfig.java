package com.eripe14.currency.config.implementation;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;

public class MessageConfig extends OkaeriConfig {

    public Notice invalidUsage = Notice.chat("&4Wrong command usage &8>> &7{COMMAND}.");

    public Notice invalidUsageHeader = Notice.chat("&cWrong command usage!");

    public Notice invalidUsageEntry = Notice.chat("&8 >> &7{SCHEME}");

    public Notice noPermission = Notice.chat("&4You do not have permission to use this command!");

    public Notice cantFindPlayer = Notice.chat("&4Can not find that player!");

    public Notice onlyForPlayer = Notice.chat("&4Command only for players!");

    public Notice giveMoney = Notice.chat(
            "<color:#22DD22>✔</color> <color:#00FF7F>You gave <color:#FFFF55>{amount}$</color> to <color:#FFFF55>{player}</color>!"
    );

    public Notice setMoney = Notice.chat(
            "<color:#22DD22>✔</color> <color:#00FF7F>You set <color:#FFFF55>{amount}$</color> to <color:#FFFF55>{player}</color>!"
    );

    public Notice yourMoneySet = Notice.chat(
            "<color:#22DD22>✔</color> <color:#00FF7F>Your money has been set to <color:#FFFF55>{amount}$</color>!"
    );

    public Notice notEnoughMoney = Notice.chat(
            "<color:#FF2222>❌</color> <color:#FF4444>You don't have enough money to buy this item!"
    );

    public Notice receivedMoney = Notice.chat(
            "<color:#22DD22>✔</color> <color:#00FF7F>You received <color:#FFFF55>{amount}$</color>!"
    );

    public Notice balance = Notice.chat(
            "<color:#22DD22>✔</color> <color:#00FF7F>Your balance is <color:#FFFF55>{amount}$</color>!"
    );

    public Notice balanceOther = Notice.chat(
            "<color:#22DD22>✔</color> <color:#00FF7F>Balance of <color:#FFFF55>{player}</color> is <color:#FFFF55>{amount}$</color>!"
    );

    public Notice shopListHeader = Notice.chat(
            "<color:#2299FF>ℹ</color> <color:#00AAFF>Shop list:</color>"
    );

    public Notice shopListEntry = Notice.chat(
            "<color:#2299FF>•</color> <color:#00AAFF>{material} - {price}$</color>"
    );

    public Notice boughtItem = Notice.chat(
            "<color:#22DD22>✔</color> <color:#00FF7F>You bought <color:#FFFF55>{material}</color> " +
                    "for <color:#FFFF55>{price}$</color>!"
    );

    public Notice cannotFindItem = Notice.chat(
            "<color:#FF2222>❌</color> <color:#FF4444>Can't find item <color:#FFFF55>({material})</color>!"
    );

}