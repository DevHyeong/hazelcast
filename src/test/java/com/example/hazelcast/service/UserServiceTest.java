package com.example.hazelcast.service;

import com.example.hazelcast.model.User;
import com.example.hazelcast.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.IntStream;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {


    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void testCacheGetOne() throws Exception{
        final String username = "테스트1";
        User user = new User(1L, username);
        when(userRepository.findByUsername(username)).thenReturn(user);
        IntStream.range(0, 100)
                .forEach(i -> userService.getOne(username));

        verify(userRepository, times(1)).findByUsername(username);

    }

    @Test
    void 테() throws Exception{
        User user = new User(1L, "테스트1");


    }


}