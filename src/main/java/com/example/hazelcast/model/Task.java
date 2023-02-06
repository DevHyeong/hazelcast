package com.example.hazelcast.model;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;

import java.io.Serializable;
import java.util.concurrent.Callable;

public class Task implements Callable<String>, Serializable, HazelcastInstanceAware {

    private String input;

    private transient HazelcastInstance hazelcastInstance;

    public Task(){

    }

    public Task(String input){
        this.input = input;
    }


    @Override
    public String call() throws Exception {
        return hazelcastInstance.getCluster().getLocalMember().toString() + ":" + input;
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }
}
