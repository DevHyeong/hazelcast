package com.example.hazelcast.service;

import com.example.hazelcast.model.AgentInfo;
import com.example.hazelcast.model.AgentRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AgentServiceTest {

    @Autowired
    private AgentService agentService;

    @Test
    @DisplayName("")
    void execute() {
        AgentInfo agentInfo = new AgentInfo();

        agentInfo.setIp("");
        agentInfo.setRegion("");
        agentInfo.setName("");

        agentService.publishTopic(agentInfo, AgentRequest.RequestType.UPDATE_AGENT);

    }
}