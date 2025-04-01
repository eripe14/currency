package com.eripe14.currency;

public class CurrencyApiProvider {

    private static CurrencyApi CURRENCY_API;

    public static CurrencyApi provide() {
        if (CURRENCY_API == null) {
            throw new IllegalStateException("Currency API is not initialized yet!");
        }

        return CURRENCY_API;
    }

    static void initialize(CurrencyApi currencyApi) {
        if (CURRENCY_API != null) {
            throw new IllegalStateException("Currency API is already initialized!");
        }

        CURRENCY_API = currencyApi;
    }

    static void deinitialize() {
        if (CURRENCY_API == null) {
            throw new IllegalStateException("Currency API is not initialized yet!");
        }

        CURRENCY_API = null;
    }

}