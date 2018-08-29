package ru.tmin10.openshifttest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.tmin10.openshifttest.structures.country.economy.Treasury;

import java.io.IOException;

@Component
public class RussiaTreasuryScanner {

    private static final long CC_ALERT_THRESHOLD = 500000;

    @Autowired
    private Environment env;
    @Autowired
    private ErepublikInfoBot bot;

    private static final Logger log = LoggerFactory.getLogger(RussiaTreasuryScanner.class);

    @NonNull
    private Treasury getCountryTreasury() throws IOException {
        Document doc = Jsoup.connect("https://www.erepublik.com/en/country/economy/Russia").get();

        String integerGold      = doc.select(".donation_status_table tr:nth-child(1) td:first-child span").first().text().replace(",", "");
        String fractionalGold   = doc.select(".donation_status_table tr:nth-child(1) td:first-child sup").first().text().replace(".", "");

        String integerCc        = doc.select(".donation_status_table tr:nth-child(2) td:first-child span").first().text().replace(",", "");
        String fractionalCc     = doc.select(".donation_status_table tr:nth-child(2) td:first-child sup").first().text().replace(".", "");

        String energy           = doc.select(".donation_status_table tr:nth-child(3) td:first-child span").first().text().replace(",", "");

        return new Treasury(
                Float.parseFloat(integerGold+"."+fractionalGold),
                Float.parseFloat(integerCc+"."+fractionalCc),
                Long.parseLong(energy)
        );
    }

    @Scheduled(cron = "*/15 * * * * *")
    public void scan() throws IOException, TelegramApiException {
        Treasury treasury = getCountryTreasury();
        log.info(treasury.toString());
//        if (treasury.getCc() < CC_ALERT_THRESHOLD) {
            ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            telegramBotsApi.registerBot(bot);
            SendMessage message = new SendMessage().setChatId(env.getProperty("CHAT_ID")).setText(treasury.toString());
            bot.execute(message);
//        }
    }

}
