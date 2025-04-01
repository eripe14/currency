package com.eripe14.currency.persistance;

import com.eripe14.currency.CurrencyPlugin;
import com.eripe14.currency.config.implementation.PluginConfig;
import com.zaxxer.hikari.HikariConfig;
import eu.okaeri.configs.json.simple.JsonSimpleConfigurer;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.persistence.PersistencePath;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.jdbc.MariaDbPersistence;

public class DatabaseManager {

    private final CurrencyPlugin plugin;
    private final PluginConfig pluginConfig;

    public DatabaseManager(CurrencyPlugin plugin, PluginConfig pluginConfig) {
        this.plugin = plugin;
        this.pluginConfig = pluginConfig;
    }

    public DocumentPersistence connect() {
        HikariConfig mysqlHikari = new HikariConfig();
        mysqlHikari.setJdbcUrl(this.pluginConfig.storage.url);

        PersistencePath basePath = PersistencePath.of(this.pluginConfig.storage.prefix);

        return new DocumentPersistence(
                new MariaDbPersistence(
                        basePath,
                        mysqlHikari
                ),
                JsonSimpleConfigurer::new,
                new SerdesCommons(),
                new SerdesCommons()
        );
    }

}