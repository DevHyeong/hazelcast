package com.example.hazelcast.topic.listener;

import com.example.hazelcast.topic.message.TopicEvent;

public interface TopicListener<T> {
    void execute(TopicEvent<T> event);
}
