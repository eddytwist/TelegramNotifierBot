package com.whiskels.telegrambot.bot.handler;

import com.whiskels.telegrambot.bot.BotCommand;
import com.whiskels.telegrambot.model.User;
import com.whiskels.telegrambot.security.RequiredRoles;
import com.whiskels.telegrambot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.stream.Collectors;

import static com.whiskels.telegrambot.model.Role.ADMIN;
import static com.whiskels.telegrambot.util.TelegramUtil.createMessageTemplate;
import static com.whiskels.telegrambot.util.TelegramUtil.extractArguments;

/**
 * Notifies all bot users with a message from admin
 *
 * Available to: Admin
 */
@Component
@Slf4j
@BotCommand(command = "/ADMIN_MESSAGE")
public class AdminMessageHandler extends AbstractBaseHandler {
    private final UserService userService;

    public AdminMessageHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    @RequiredRoles(roles = ADMIN)
    public List<BotApiMethod<Message>> handle(User admin, String text) {
        log.debug("Preparing /ADMIN_MESSAGE");
        List<BotApiMethod<Message>> messagesToSend = userService.getUsers()
                .stream()
                .map(user -> createMessageTemplate(user)
                        .setText(extractArguments(text)))
                .collect(Collectors.toList());

        log.debug("Prepared {} messages", messagesToSend.size());
        messagesToSend.add(createMessageTemplate(admin)
                .setText(String.format("Notified %d users", messagesToSend.size())));

        return messagesToSend;
    }


}
