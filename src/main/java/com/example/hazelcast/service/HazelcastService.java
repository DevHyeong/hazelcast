package com.example.hazelcast.service;

import com.example.hazelcast.topic.message.TopicEvent;
import com.hazelcast.cluster.Member;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.hazelcast.internal.util.Preconditions.checkNotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class HazelcastService {

   private final HazelcastInstance hazelcastInstance;

    protected Member findClusterMember(String userId) {
        Set<Member> clusterMember = hazelcastInstance.getCluster().getMembers();

        for(Member m : clusterMember){

            if(m.getAttributes().containsKey("") && userId.equals(m.getAttributes().get(""))){
                return m;
            }
        }
        return null;
       // throw new IllegalAccessException(region + "is not clustered region. ");
    }

    public <T> List<T> submitToAllUsers(String executorName, Callable<T> task){
        List<T> results = new ArrayList<>();
        IExecutorService executorService = hazelcastInstance.getExecutorService(executorName);
        Map<Member,Future<T>> futures = executorService.submitToAllMembers(task);
        for (Future<T> future : futures.values()){
            try {
                T result = future.get();
                results.add(result);
            } catch (InterruptedException | ExecutionException e) {
                log.error("Error while inquire region info. {}", e.getMessage());
            }
        }
        return results;
    }


    public <T> T submitToUser(String executorName, Callable<T> task, String region){
        Member member = findClusterMember(region);
        IExecutorService executorService = hazelcastInstance.getExecutorService(executorName);
        Future<T> future = executorService.submitToMember(task, member);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error while running task in region [{}] {}", region, e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void publish(String topic, TopicEvent event) {
        hazelcastInstance.getTopic(topic).publish(event);
    }

    public void put(String map, Object key, Object value) {
        hazelcastInstance.getMap(map).put(key, value);
    }

    public void delete(String map, Object key) {
        hazelcastInstance.getMap(map).delete(key);
    }

    public List getValuesAsList(String map) {
        return new ArrayList<>(hazelcastInstance.getMap(map).values());
    }

    public <K, V> V get(String map, K key) {
        IMap<K, V> distMap = hazelcastInstance.getMap(map);
        checkNotNull(distMap, "Cache(" + map +") is not exist");
        return distMap.get(key);
    }
}
