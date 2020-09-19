package com.whiskels.telegrambot.bot.handler;

import com.whiskels.telegrambot.bot.command.Command;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public abstract class AbstractHandler {
    protected static final String END_LINE = "%n";
    protected static final String EMPTY_LINE = "---------------------------";
    protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public abstract List<PartialBotApiMethod<? extends Serializable>> operate(String chatId, Message message);

    /*
     * Creates SendMessage template with markdown enabled for user with chatId
     */
    protected final SendMessage createMessageTemplate(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);

        return sendMessage;
    }

    public abstract Command supportedCommand();
}
