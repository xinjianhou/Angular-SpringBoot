package com.xinjian.wechat.controller;

import com.xinjian.wechat.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    User user = new User();

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void createUser() {

        user.setUsername("hadamen");
    }

    @Test
    public void testGetUserByName() {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("username", "hadamen");
        Assert.assertEquals(user, restTemplate.getForObject("getUserByUsername/{username}", User.class, urlVariables));

    }
}
