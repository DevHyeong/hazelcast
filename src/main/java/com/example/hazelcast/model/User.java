package com.example.hazelcast.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Getter

@Entity
public class User implements Serializable {

    @Id
    private Long id;

    private String username;

    public User(){

    }

    public User(Long id, String username){
        this.id = id;
        this.username = username;
    }

}
