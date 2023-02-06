package com.example.hazelcast.config;

import com.example.hazelcast.common.constant.CacheConstants;
import com.example.hazelcast.topic.message.TopicEvent;
import com.example.hazelcast.topic.subscriber.TopicSubscriber;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spi.merge.LatestUpdateMergePolicy;
import com.hazelcast.spring.context.SpringManagedContext;
import com.hazelcast.topic.ITopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HazelcastConfig implements CacheConstants {

    private static final int DAY = 24 * 60 * 60;
    private static final int HOUR = 60 * 60;
    private static final int MIN = 60;

    @Bean
    public SpringManagedContext managedContext(){
        return new SpringManagedContext();
    }

    @Bean
    public HazelcastInstance hazelcastInstance(){
        Config hazelcastConfig = new Config("hazelcast.config.test");
        hazelcastConfig.setManagedContext(managedContext());
       // hazelcastConfig.getMemberAttributeConfig().setAttribute()
        hazelcastConfig.setMapConfigs(cacheConfigMap().getHazelcastCacheConfigs());
        hazelcastConfig.addTopicConfig(getTopicConfig());

        NetworkConfig networkConfig = hazelcastConfig.getNetworkConfig();

        JoinConfig join = networkConfig.getJoin();
        join.getMulticastConfig().setEnabled(false);

        // 클러스터 환경이라면
        TcpIpConfig tcpIpConfig = join.getTcpIpConfig();
        tcpIpConfig.setEnabled(true);



        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(hazelcastConfig);
        ITopic<TopicEvent> topic = hazelcastInstance.getTopic(AGENT_TOPIC_NAME);
        topic.addMessageListener(topicSubscriber());

        return hazelcastInstance;
    }

    @Bean
    public CacheConfigHolder cacheConfigMap(){
        CacheConfigHolder cm = new CacheConfigHolder();

        cm.addDistMap(DIST_MAP_NAME_AGENT, 10);

        cm.addDistCache(DIST_CACHE_USERS, 1 * HOUR + 40 * MIN, 300);

        return cm;
    }


    static class CacheConfigHolder{

        private final Map<String, MapConfig> hazelcastCacheConfigs = new ConcurrentHashMap<>();

        void addDistMap(String cacheName, int timeout) {
            MapConfig mapConfig = createDistMapConfig(cacheName, timeout);
            hazelcastCacheConfigs.put(cacheName, mapConfig);
        }

        // count는 해당 cacheName에 담길 수 있는 객체 사이즈 수를 의미하는 것 같음
        void addDistCache(String cacheName, int timeout, int count){
            MapConfig mapConfig = createDistMapConfig(cacheName, timeout);

            NearCacheConfig nearCacheConfig = new NearCacheConfig(cacheName);
            nearCacheConfig.setTimeToLiveSeconds(timeout);

            if(count > 0){
                EvictionConfig evictionConfig = new EvictionConfig();
                evictionConfig.setEvictionPolicy(EvictionPolicy.LRU);
                evictionConfig.setSize(count);
                evictionConfig.setMaxSizePolicy(MaxSizePolicy.PER_NODE);

                mapConfig.setEvictionConfig(evictionConfig);

                nearCacheConfig.getEvictionConfig()
                        .setSize(count)
                        .setMaxSizePolicy(MaxSizePolicy.ENTRY_COUNT)
                        .setEvictionPolicy(EvictionPolicy.LRU);

            }

            mapConfig.setNearCacheConfig(nearCacheConfig);
            hazelcastCacheConfigs.put(cacheName, mapConfig);

        }


        private MapConfig createDistMapConfig(String cacheName, int timeout){
            MapConfig mapConfig = new MapConfig(cacheName);
            mapConfig.getMergePolicyConfig().setPolicy(LatestUpdateMergePolicy.class.getName());
            mapConfig.setTimeToLiveSeconds(timeout);
            return mapConfig;
        }

        Map<String, MapConfig> getHazelcastCacheConfigs(){
            return hazelcastCacheConfigs;
        }

    }

    private TopicConfig getTopicConfig(){
        TopicConfig topicConfig = new TopicConfig();
        topicConfig.setGlobalOrderingEnabled(true);
        topicConfig.setStatisticsEnabled(true);
        topicConfig.setName(AGENT_TOPIC_NAME);
        return topicConfig;
    }

    @Bean
    public TopicSubscriber topicSubscriber() {
        return new TopicSubscriber();
    }
}
