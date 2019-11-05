package de.alizada.loan.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
public class PlanControllerUnitTest {
    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;
    private static final String URL="/generate";

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void shouldRespond200ForRightRequest() throws Exception {
        this.mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(getRequestStringByJson("request")))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldRespond400ForWrongRequest() throws Exception {
        this.mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(getRequestStringByJson("wrongRequest")))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    private String getRequestStringByJson(String jsonName) {
        try {
            return new String(Files.readAllBytes(Paths.get("src/test/resources/" + jsonName + ".json")),
                    StandardCharsets.UTF_8);
        }catch (Exception ex){
            return null;
        }
    }
}
