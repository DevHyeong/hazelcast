package com.example.hazelcast.config;


import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CacheConfig {

    private final HazelcastInstance hazelcastInstance;

    @Bean
    public CacheManager cacheManager(){
        return new HazelcastCacheManager(hazelcastInstance);
    }


}
