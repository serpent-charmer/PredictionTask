package ru.liga;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import ru.liga.api.exceptions.MissingArgsException;
import ru.liga.impl.CommandParser;
import ru.liga.impl.CommandExecutor;
import ru.liga.output.GraphResult;
import ru.liga.output.TextResult;

public class AlgorithmBot {

    private static void sendHelpMessage(TelegramBot bot, long chatId) {
        String help = "rate <USD|TRY|BGN|EUR>\n" +
                "-alg <mystic|contemp|linear>\n" +
                "-period <month|week>\n" +
                "-output <graph|text>\n";
        sendMessageFromBot(bot, chatId, help);
    }

    private static void sendMessageFromBot(TelegramBot bot, long chatId, String msg) {
        SendMessage action = new SendMessage(chatId, msg);
        bot.execute(action);
    }

    public static void main(String[] args) {
        String botToken = System.getenv("BOT_TOKEN");
        TelegramBot bot = new TelegramBot(botToken);
        bot.setUpdatesListener(updates -> {

            if (updates.size() < 1) {
                return UpdatesListener.CONFIRMED_UPDATES_NONE;
            }

            updates.forEach(u -> {
                long chatId = u.message().chat().id();
                if (u.message() == null) {
                    return;
                }
                try {
                    String cmd = u.message().text();
                    if (cmd.equals("-help")) {
                        sendHelpMessage(bot, chatId);
                        return;
                    }
                    CommandExecutor executor = CommandParser.parse(cmd);
                    executor.registerResultHandler(TextResult.class,
                            (TextResult o) -> {
                                sendMessageFromBot(bot, chatId, o.get());
                            });
                    executor.registerResultHandler(GraphResult.class,
                            (GraphResult o) -> {
                                SendPhoto action = new SendPhoto(chatId, o.get());
                                bot.execute(action);
                            });
                    executor.eval();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (MissingArgsException e) {
                    sendMessageFromBot(bot, chatId,
                            String.format("%s\nTry -help command\n", e.getMessage()));
                } catch (Exception e) {
//                    e.printStackTrace();
                    sendMessageFromBot(bot, chatId, e.getMessage());
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }

}
