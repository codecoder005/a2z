package com.popcorn.a2z.controller.rest;

import com.popcorn.a2z.advice.GlobalRestAPIControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(profiles = {"unit-test"})
class UserRestControllerV1Test {
    private MockMvc mockMvc;

    @Autowired
    private UserRestControllerV1 userRestControllerV1;
    @Autowired
    private GlobalRestAPIControllerAdvice globalRestAPIControllerAdvice;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestControllerV1)
                .setControllerAdvice(globalRestAPIControllerAdvice)
                .build();
    }

    @Test
    void testGetAllUsers200Response() throws Exception {
        mockMvc.perform(get("/api/v1/users").accept(MediaType.APPLICATION_JSON).characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].userEntityPK.userId").value(10001))
                .andExpect(jsonPath("$.[0].address.city").value("New York"))
                .andExpect(jsonPath("$.[0].bankAccounts[0].accountNo").value("5678123456"+10001))
                .andReturn();
    }
}