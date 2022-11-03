package com.study.javaquestions.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

@Getter
@Setter
@AllArgsConstructor
public class Request {

    private SendMessage sendMessage;

    private Message message;

    private String sessionStep;

    private String step;

    private User user;
}
