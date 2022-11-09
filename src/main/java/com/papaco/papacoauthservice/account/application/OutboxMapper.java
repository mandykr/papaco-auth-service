package com.papaco.papacoauthservice.account.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.papaco.papacoauthservice.account.application.exception.OutboxCreateFailedException;
import com.papaco.papacoauthservice.account.domain.Outbox;
import com.papaco.papacoauthservice.account.domain.event.DomainEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class OutboxMapper {
    public <A extends Outbox> A createOutbox(Class<A> clazz, DomainEvent event) {
        try {
            Constructor<A> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            A a = constructor.newInstance();
            constructor.setAccessible(false);
            return (A) a.create(event, getPayload(event));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new OutboxCreateFailedException(e);
        }
    }

    private String getPayload(DomainEvent event) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new OutboxCreateFailedException(e);
        }
    }
}
