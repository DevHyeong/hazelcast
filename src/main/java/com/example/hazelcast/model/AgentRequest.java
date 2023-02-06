package com.example.hazelcast.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class AgentRequest implements Serializable {

    //private static final long serialVersionUID = 1L;

    private final String agentIp;
    private final String agentName;

    @Setter
    private RequestType requestType;

    public enum RequestType{
        STOP_AGENT,
        UPDATE_AGENT
    }

    public AgentRequest(String agentIp, String agentName, RequestType requestType) {
        this.agentIp = agentIp;
        this.agentName = agentName;
        this.requestType = requestType;
    }

}
