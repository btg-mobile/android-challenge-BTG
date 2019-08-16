package com.androidchallengebtg.helpers;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {

    private static EventBus instance;
    private Map<String,EventBusListener> listeners;

    public interface EventBusListener {
        void onEvent(JSONObject jsonObject);
    }

    private EventBus() {
        this.listeners = new HashMap<>();
    }

    public static EventBus getInstance() {
        if(instance == null){
            instance = new EventBus();
        }
        return instance;
    }

    public void register(EventBusListener eventBusListener){
        String classId = eventBusListener.getClass().getCanonicalName();
        if(classId == null){
            classId = eventBusListener.getClass().getName();
        }
        listeners.put(classId,eventBusListener);
    }

    public void unRegister(EventBusListener eventBusListener){
        String classId = eventBusListener.getClass().getCanonicalName();
        if(classId == null){
            classId = eventBusListener.getClass().getName();
        }
        listeners.remove(classId);
    }

    public void emit(JSONObject jsonObject){
        List<EventBusListener> tempListeners = new ArrayList<>(listeners.values());
        for (EventBusListener listener: tempListeners) {
            listener.onEvent(jsonObject);
        }
    }
}
