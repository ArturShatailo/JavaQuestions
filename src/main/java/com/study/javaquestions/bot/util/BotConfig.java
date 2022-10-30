package com.study.javaquestions.bot.util;

import com.study.javaquestions.bot.model.BotEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {

    @Bean
    public BotEntity botBean(){
        return new BotEntity("5625550512:AAFSdO15T_EWhiwz9kMlX-HYslCBh1M7Bw4", "JavaQuestions_bot");
    }

}
