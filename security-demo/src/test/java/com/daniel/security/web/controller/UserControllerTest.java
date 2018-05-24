package com.daniel.security.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void update() throws Exception {
        long epochMilli = LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String content = "{\"userName\":\"daniel\",\"password\":\"\",\"birthday\":" + epochMilli + "}";
        String result = mockMvc.perform(put("/user/1")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void valid() throws Exception {
        String content = "{\"userName\":" + null + ",\"password\":\"ttt\",\"birthday\":\"1527155078886\"}";
        String result = mockMvc.perform(post("/user")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void createUser() throws Exception {
        String content="{\"userName\":\"jack\",\"password\":\"ttt\",\"birthday\":\"1527155078886\"}";
        String result = mockMvc.perform(post("/user")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void getUserById() throws Exception {
        mockMvc.perform(get("/user/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("jack"))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        mockMvc.perform(get("/user")
                    .param("page","10")
                    .param("size","50")
                    .param("sort","userName,DESC")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getByUserName() throws Exception {
        mockMvc.perform(get("/user1").param("nickName","tom").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByCondition() throws Exception {
        mockMvc.perform(get("/user2")
                    .param("id","10")
                    .param("userName","test")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
}
