package com.study.javaquestions.bot.componenents;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

public interface BotFactory{

    JavaQuestionBot bot = new JavaQuestionBot("5625550512:AAFSdO15T_EWhiwz9kMlX-HYslCBh1M7Bw4", "JavaQuestions_bot");

    @Getter
    class JavaQuestionBot{

        private final String token;

        private final String username;

        public JavaQuestionBot(@Value("${bot.TOKEN}") String token, @Value("${bot.USERNAME}") String username) {
            this.token = token;
            this.username = username;
        }

    }

}
