package com.eripe14.currency.currency;

import com.eripe14.currency.CurrencyService;
import com.eripe14.currency.user.User;
import com.eripe14.currency.user.UserRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class CurrencyServiceImpl implements CurrencyService {

    private final UserRepository userRepository;

    public CurrencyServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void processUserBalance(UUID userUniqueId, Function<Double, Double> balanceProcessor) {
        this.userRepository.findByPath(userUniqueId).ifPresent(user -> {
            user.setBalance(balanceProcessor.apply(user.getBalance()));
            user.save();
            this.userRepository.save(user);
        });
    }

    public void save(UUID uuid) {
        this.userRepository.findByPath(uuid).ifPresent(this.userRepository::save);
    }

    @Override
    public boolean canUserAfford(UUID userUniqueId, double balance) {
        Optional<User> user = this.userRepository.findByPath(userUniqueId);
        return user.filter(value -> value.getBalance() >= balance).isPresent();

    }

    @Override
    public double getUserBalance(UUID userUniqueId) {
        return this.userRepository.findByPath(userUniqueId).map(User::getBalance).orElse(0.0);
    }

}