package com.example.hazelcast.store;

import com.example.hazelcast.common.constant.CacheConstants;
import com.example.hazelcast.model.AgentInfo;
import com.example.hazelcast.service.HazelcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.hazelcast.common.util.TypeConvertUtils.cast;


@Component
@RequiredArgsConstructor
public class AgentInfoStore implements CacheConstants {

    private final HazelcastService hazelcastService;

    public AgentInfo getAgentInfo(Object key) {
        return hazelcastService.get(DIST_MAP_NAME_AGENT, key);
    }

    public void deleteAgentInfo(Object key) {
        hazelcastService.delete(DIST_MAP_NAME_AGENT, key);
    }

    public void updateAgentInfo(Object key, AgentInfo agentInfo) {
        hazelcastService.put(DIST_MAP_NAME_AGENT, key, agentInfo);
    }

    public List<AgentInfo> getAllAgentInfo() {
        return cast(hazelcastService.getValuesAsList(DIST_MAP_NAME_AGENT));
    }
}
