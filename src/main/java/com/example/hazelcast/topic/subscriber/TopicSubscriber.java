package com.example.hazelcast.topic.subscriber;

import com.example.hazelcast.topic.listener.TopicListener;
import com.example.hazelcast.topic.message.TopicEvent;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TopicSubscriber implements MessageListener<TopicEvent> {

    private Map<String, TopicListener> listenerMap = new ConcurrentHashMap<>();

    @Override
    public void onMessage(Message<TopicEvent> message) {
        TopicEvent event = message.getMessageObject();

        System.out.println(event.getKey());

        if (listenerMap.containsKey(event.getType())) {
            listenerMap.get(event.getType()).execute(event);
        }
    }

    public void addListener(String type, TopicListener listener) {
        this.listenerMap.put(type, listener);
    }

    public void removeListener(String type) {
        listenerMap.remove(type);
    }
}
