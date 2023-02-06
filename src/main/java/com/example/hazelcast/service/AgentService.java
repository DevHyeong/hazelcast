package com.example.hazelcast.service;

import com.example.hazelcast.common.constant.CacheConstants;
import com.example.hazelcast.model.AgentInfo;
import com.example.hazelcast.model.AgentRequest;
import com.example.hazelcast.store.AgentInfoStore;
import com.example.hazelcast.topic.listener.TopicListener;
import com.example.hazelcast.topic.message.TopicEvent;
import com.example.hazelcast.topic.subscriber.TopicSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class AgentService implements CacheConstants, TopicListener<AgentRequest> {

    private final HazelcastService hazelcastService;

    private final TopicSubscriber topicSubscriber;

    protected final AgentInfoStore agentInfoStore;

    @PostConstruct
    public void init(){
        topicSubscriber.addListener(AGENT_TOPIC_LISTENER_NAME, this);
    }


    public void publishTopic(AgentInfo agentInfo, AgentRequest.RequestType requestType) {
        hazelcastService.publish(AGENT_TOPIC_NAME, new TopicEvent<>(AGENT_TOPIC_LISTENER_NAME,
                agentInfo.getRegion(), new AgentRequest(agentInfo.getIp(), agentInfo.getName(), requestType)));
    }

    public List<AgentInfo> getAllActive() {
        return agentInfoStore.getAllAgentInfo();
    }

    public AgentInfo getAgent(String ip, String name) {
        return agentInfoStore.getAgentInfo(createAgentKey(ip, name));
    }

    private String createAgentKey(String ip, String name) {
        return ip + "_" + name;
    }

    @Override
    public void execute(TopicEvent<AgentRequest> event) {

        log.info("test " + event.getKey());
    }
}
