package com.whiskels.telegrambot.bot.handler;

import com.whiskels.telegrambot.bot.BotCommand;
import com.whiskels.telegrambot.bot.builder.MessageBuilder;
import com.whiskels.telegrambot.model.User;
import com.whiskels.telegrambot.security.RequiredRoles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.List;

import static com.whiskels.telegrambot.model.Role.ADMIN;

/**
 * Shows current time on bot's server
 * <p>
 * Available to: Admin
 */
@Component
@Slf4j
@BotCommand(command = "/ADMIN_TIME", message = "Show bot server time")
public class AdminTimeHandler extends AbstractBaseHandler {
    @Value("${bot.server.hour.offset}")
    private int serverHourOffset;

    @Override
    @RequiredRoles(roles = ADMIN)
    public List<BotApiMethod<Message>> handle(User admin, String message) {
        log.debug("Preparing /ADMIN_TIME");
        return List.of(MessageBuilder.create(admin)
                .line("*Bot current time*:")
                .line(LocalDateTime.now().toString())
                .line("Server hour offset is %d", serverHourOffset)
                .build());
    }
}
