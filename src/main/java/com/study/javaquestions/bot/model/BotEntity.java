package com.study.javaquestions.bot.model;

import lombok.Getter;

@Getter
public class BotEntity{

    private final String token;

    private final String username;

    public BotEntity(String token, String username) {
        this.token = token;
        this.username = username;
    }

}
