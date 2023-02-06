package com.example.hazelcast.topic.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TopicEvent<T> implements Serializable {
    private String type;
    private String key;
    private T data;

    public TopicEvent(String type, String key, T data) {
        this.type = type;
        this.key = key;
        this.data = data;
    }



}
