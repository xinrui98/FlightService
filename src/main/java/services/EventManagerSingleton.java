package services;

import data.UserMonitoring;

import java.util.HashMap;
import java.util.Map;

public final class EventManagerSingleton {

    private static EventManagerSingleton INSTANCE;
    private Map<Long, UserMonitoring> subscribers;


    public EventManagerSingleton() {
        subscribers = new HashMap();
    }

    public static EventManagerSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventManagerSingleton();
        }
        return INSTANCE;
    }


    public void addSubscriber(Long f_id, UserMonitoring userMonitoring) {
        subscribers.put(f_id, userMonitoring);
    }


}