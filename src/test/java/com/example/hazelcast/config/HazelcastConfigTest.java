package com.example.hazelcast.config;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.topic.ITopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HazelcastConfigTest {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Test
    public void 테스트() throws Exception{

        ITopic iTopic = hazelcastInstance.getTopic("topic");

        IMap iMap = hazelcastInstance.getMap("users");

        System.out.println(iTopic.getName());

    }

}