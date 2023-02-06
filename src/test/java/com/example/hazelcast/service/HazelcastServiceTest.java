package com.example.hazelcast.service;

import com.example.hazelcast.common.constant.CacheConstants;
import com.example.hazelcast.model.AgentRequest;
import com.example.hazelcast.topic.message.TopicEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HazelcastServiceTest implements CacheConstants {

    @Autowired
    private HazelcastService hazelcastService;

    @Test
    void publish() {
        String agentIp = "127.0.0.1";
        String agentName = "agentTest";
        String key = "4466";
        AgentRequest agentRequest = new AgentRequest(agentIp, agentName, AgentRequest.RequestType.UPDATE_AGENT);

        hazelcastService.publish(AGENT_TOPIC_NAME,
                new TopicEvent(AGENT_TOPIC_LISTENER_NAME, key, agentRequest));
    }
}