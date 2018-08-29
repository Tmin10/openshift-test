package ru.tmin10.openshifttest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ErepublikInfoBot extends TelegramLongPollingBot {

    @Autowired
    private Environment env;
    private static final Logger log = LoggerFactory.getLogger(RussiaTreasuryScanner.class);

    @Override
    public void onUpdateReceived(Update update) {
        log.info(update.toString());
    }

    @Override
    public String getBotUsername() {
        return env.getProperty("BOT_NAME");
    }

    @Override
    public String getBotToken() {
        return env.getProperty("BOT_TOKEN");
    }
}
