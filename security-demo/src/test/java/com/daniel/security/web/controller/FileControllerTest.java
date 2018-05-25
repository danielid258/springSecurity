package com.daniel.security.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void upload() throws Exception {
        String content = "abcdedghijk";
        String fileInfo = mockMvc.perform(fileUpload("/file")
                .file(new MockMultipartFile("file", "test.txt", "multipart/form-data", content.getBytes("UTF-8")))) //类似form表单的name=file
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(fileInfo);
    }
}
