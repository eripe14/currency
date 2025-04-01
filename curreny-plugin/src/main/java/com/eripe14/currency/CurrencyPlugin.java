package com.eripe14.currency;

import com.eripe14.currency.command.InvalidUsageHandler;
import com.eripe14.currency.command.MissingPermissionsHandler;
import com.eripe14.currency.config.ConfigManager;
import com.eripe14.currency.config.implementation.MessageConfig;
import com.eripe14.currency.config.implementation.PluginConfig;
import com.eripe14.currency.currency.CurrencyCommand;
import com.eripe14.currency.currency.CurrencyServiceImpl;
import com.eripe14.currency.notice.NoticeService;
import com.eripe14.currency.notice.adventure.LegacyColorProcessor;
import com.eripe14.currency.persistance.DatabaseManager;
import com.eripe14.currency.reward.RewardController;
import com.eripe14.currency.shop.ShopCommand;
import com.eripe14.currency.user.UserController;
import com.eripe14.currency.user.UserRepository;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.repository.RepositoryDeclaration;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CurrencyPlugin extends JavaPlugin implements CurrencyApi {

    private AudienceProvider audienceProvider;
    private MiniMessage miniMessage;
    private NoticeService noticeService;

    private ConfigManager configManager;
    private PluginConfig pluginConfig;
    private MessageConfig messageConfig;

    private DatabaseManager databaseManager;
    private DocumentPersistence documentPersistence;

    private UserRepository userRepository;

    private CurrencyServiceImpl currencyService;

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        Server server = this.getServer();
        this.messageConfig = new MessageConfig();

        this.audienceProvider = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.builder()
                .postProcessor(new LegacyColorProcessor())
                .build();
        this.noticeService = new NoticeService(this.messageConfig, this.audienceProvider, this.miniMessage);

        this.configManager = new ConfigManager(this.noticeService.getNoticeRegistry());
        this.pluginConfig = this.configManager.load(PluginConfig.class, this.getDataFolder(), "config.yml");
        this.messageConfig = this.configManager.load(MessageConfig.class, this.getDataFolder(), "messages.yml");

        this.databaseManager = new DatabaseManager(this, this.pluginConfig);
        this.documentPersistence = this.databaseManager.connect();

        PersistenceCollection userCollection = PersistenceCollection.of(UserRepository.class);
        this.documentPersistence.registerCollection(userCollection);
        this.userRepository = RepositoryDeclaration.of(UserRepository.class)
                .newProxy(this.documentPersistence, userCollection, this.getClass().getClassLoader());

        this.currencyService = new CurrencyServiceImpl(this.userRepository);

        this.initializeCommands();
        server.getPluginManager().registerEvents(new UserController(this.userRepository), this);
        server.getPluginManager().registerEvents(
                new RewardController(server, this.userRepository, this.pluginConfig.rewardConfig),
                this
        );
    }

    void initializeCommands() {
        this.liteCommands = LiteBukkitFactory.builder("currency", this)
                .settings(settings -> settings.fallbackPrefix("[Currency]").nativePermissions(false))
                .message(LiteBukkitMessages.PLAYER_NOT_FOUND, this.messageConfig.cantFindPlayer)
                .message(LiteBukkitMessages.PLAYER_ONLY, input -> this.messageConfig.onlyForPlayer)
                .missingPermission(new MissingPermissionsHandler(this.noticeService))
                .invalidUsage(new InvalidUsageHandler(this.noticeService))
                .commands(
                        new CurrencyCommand(this.currencyService, this.noticeService),
                        new ShopCommand(this.pluginConfig.shopItemConfig, this.currencyService, this.noticeService)
                )
                .build();
    }

    @Override
    public CurrencyService getCurrencyService() {
        return this.currencyService;
    }

}