package com.eripe14.currency;

import java.util.UUID;
import java.util.function.Function;

public interface CurrencyService {

    void processUserBalance(UUID userUniqueId, Function<Double, Double> balanceProcessor);

    boolean canUserAfford(UUID userUniqueId, double balance);

    double getUserBalance(UUID userUniqueId);

}