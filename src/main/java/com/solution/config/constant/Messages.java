package com.solution.config.constant;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Messages {
    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor messageSourceAccessor;

    @PostConstruct
    public void initBundle() {
        messageSourceAccessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
    }

    public String get(String code){
        return messageSourceAccessor.getMessage(code);
    }

    public String get(String code, Object... args){ return messageSourceAccessor.getMessage(code, args); }
}
