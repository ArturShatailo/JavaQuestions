package com.study.javaquestions.bot.util;

import com.study.javaquestions.bot.model.BotEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {

    @Bean
    public BotEntity botBean(){
        return new BotEntity("5678321117:AAHnhS4gRu5h8N_Xvelg2zpYkvzwBDG0Qyo", "JavaQuestions_bot");
    }

}
