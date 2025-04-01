package com.eripe14.currency.user;

import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Document {

    private String name;
    private double balance;
    private UserStatistics userStatistics;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}