package com.example.hazelcast.service;

import com.example.hazelcast.common.constant.CacheConstants;
import com.example.hazelcast.model.AgentInfo;
import com.example.hazelcast.model.AgentRequest;
import com.example.hazelcast.model.Post;
import com.example.hazelcast.model.User;
import com.example.hazelcast.repository.UserRepository;
import com.example.hazelcast.store.AgentInfoStore;
import com.example.hazelcast.topic.listener.TopicListener;
import com.example.hazelcast.topic.message.TopicEvent;
import com.example.hazelcast.topic.subscriber.TopicSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements CacheConstants, TopicListener<AgentRequest> {

    private final HazelcastService hazelcastService;

    private final TopicSubscriber topicSubscriber;

    protected final AgentInfoStore agentInfoStore;

    private final UserRepository userRepository;

    @PostConstruct
    public void init(){
        topicSubscriber.addListener(AGENT_TOPIC_LISTENER_NAME, this);
    }


    @Cacheable(value = DIST_CACHE_USERS)
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @Cacheable(value = DIST_CACHE_USERS, key = "#username")
    public User getOne(String username){
        return userRepository.findByUsername(username);
    }

    @CachePut(value = DIST_CACHE_USERS)
    public User update(User user){
        return userRepository.save(user);
    }

    @Override
    public void execute(TopicEvent<AgentRequest> event) {

        log.info("test " + event.getKey());
    }
}
