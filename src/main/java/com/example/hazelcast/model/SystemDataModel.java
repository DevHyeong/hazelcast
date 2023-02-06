package com.example.hazelcast.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemDataModel {

    private String key;

    private String ip;

    private int port;

    private String system;

    private long collectTime;

    private long freeMemory;

    private long totalMemory;

    private float cpuUsedPercentage;

    private long receivedPerSec;

    private long sentPerSec;

    private String version;

    private String customValues;




}
