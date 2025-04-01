package com.eripe14.currency.user;

import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserStatistics extends Document {

    private int killedMobs;
    private int minedBlocks;
    private int trades;

    public void incrementKilledMobs() {
        this.killedMobs++;
    }

    public void incrementMinedBlocks() {
        this.minedBlocks++;
    }

    public void incrementTrades() {
        this.trades++;
    }

}