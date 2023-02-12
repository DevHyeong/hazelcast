package com.example.hazelcast.common.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

@Slf4j
public class NetworkUtils {

    public static String getLocalHostAddress() {
        InetAddress localHost = null;

        try{
            localHost = InetAddress.getLocalHost();
        }catch (Exception e){
            log.error("Error while get localhost address", e);
        }

        if(localHost != null && !localHost.isLoopbackAddress()){
            return localHost.getHostAddress();
        }

        return "127.0.0.1";
    }

}
