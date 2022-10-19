package com.study.javaquestions.bot.componenents;

import com.study.javaquestions.domain.QuestionSession;

import java.util.HashMap;
import java.util.Map;

public interface BotSession{

    Map<String, String> sessions = new HashMap<>();

    Map<String, QuestionSession> questionsSessions = new HashMap<>();

}
