package com.example.hazelcast.model;

import com.hazelcast.cluster.Member;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.cp.internal.datastructures.countdownlatch.CountDownLatch;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskTest {

    @Test
    public void 테스크() throws Exception{
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        IExecutorService executorService = instance.getExecutorService("executorService");
        Future<String> future = executorService.submit( new Task("test"));

        Set<Member> clusterMember = instance.getCluster().getMembers();

        System.out.println(clusterMember.size());
        for (Member member: clusterMember) {
            System.out.println(member.getAddress());
        }

        Set<String> propertyNames = instance.getConfig().getProperties().stringPropertyNames();


        for(String name : propertyNames){
            System.out.println(name);
        }


    }

    @Test
    public void testTopic() {
        // start two member cluster
        HazelcastInstance h1 = Hazelcast.newHazelcastInstance(null);
        HazelcastInstance h2 = Hazelcast.newHazelcastInstance(null);
        String topicName = "TestMessages";
        ITopic<String> topic1 = h1.getTopic(topicName);
        topic1.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println(message.getMessageObject().toString());
                //assertEquals("Test1", message);

            }


        });
        ITopic<String> topic2 = h2.getTopic(topicName);
        topic2.addMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                System.out.println(message.getMessageObject().toString());
                //assertEquals("Test1", message);
            }
        });
        // publish the first message, both should receive this
        topic1.publish("Test1");
        // shutdown the first member
        h1.shutdown();
        // publish the second message, second member's topic should receive this
        topic2.publish("Test1");

    }


}