package com.study.javaquestions.bot.session;

import java.util.HashMap;
import java.util.Map;

public interface BotSession{

    Map<String, String> sessions = new HashMap<>();

    Map<String, String> sessionSteps = new HashMap<>();

}
