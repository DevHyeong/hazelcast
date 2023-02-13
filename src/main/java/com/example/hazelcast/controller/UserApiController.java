package com.example.hazelcast.controller;

import com.example.hazelcast.model.User;
import com.example.hazelcast.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> users() {
        return userService.getUsers();
    }

}
