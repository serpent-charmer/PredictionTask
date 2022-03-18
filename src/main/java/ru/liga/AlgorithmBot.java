package ru.liga;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import ru.liga.api.DateTooDistantException;
import ru.liga.api.MissingArgsException;
import ru.liga.api.PredictorBuildFailedException;
import ru.liga.impl.CommandEvaluator;

import java.util.List;

public class AlgorithmBot {

    public static void main(String[] args) {
        String botToken = System.getenv("BOT_TOKEN");

        TelegramBot bot = new TelegramBot(botToken);


        bot.setUpdatesListener(updates -> {

            if (updates.size() < 1) {
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            } else {
                System.out.println(updates);
                updates.forEach(u -> {
                    if (u.message() == null) {
                        return;
                    }
                    long chatId = u.message().chat().id();

                    CommandEvaluator commandEvaluator = new CommandEvaluator();

                    try {

                        try {
                            commandEvaluator.parse(u.message().text());
                        } catch (MissingArgsException e) {
                            SendMessage action = new SendMessage(chatId, e.getMessage());
                            bot.execute(action);
                            return;
                        } catch (RuntimeException e) {
                            SendMessage action = new SendMessage(chatId, e.getMessage());
                            bot.execute(action);
                            return;
                        }



                    try {
                        Object result = commandEvaluator.run();
                        if (result instanceof byte[]) {
                            SendPhoto action = new SendPhoto(chatId, (byte[]) result);
                            bot.execute(action);
                        }
                        if (result instanceof List) {
                            SendMessage action = new SendMessage(chatId, result.toString());
                            bot.execute(action);
                        }
                    } catch (DateTooDistantException e) {
                        SendMessage action = new SendMessage(chatId, e.toString());
                        bot.execute(action);
                        return;
                    }

                } catch(PredictorBuildFailedException e){
                    SendMessage action = new SendMessage(chatId, e.toString());
                    bot.execute(action);
                }
            });

        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    });

}

}
