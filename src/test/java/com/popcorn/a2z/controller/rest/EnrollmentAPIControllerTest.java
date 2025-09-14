package com.popcorn.a2z.controller.rest;

import com.popcorn.a2z.advice.GlobalRestAPIControllerAdvice;
import com.popcorn.a2z.domain.request.EnrollmentRequest;
import com.popcorn.a2z.search.SortingOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(profiles = {"unit-test"})
class EnrollmentAPIControllerTest {
    private static String ENROLL_REQUEST_BODY = """
        {
          "isChallenged": false,
          "isSalaried": true,
          "age": 28,
          "height": 175,
          "weight": 72.350,
          "salary": 55000.0,
          "revenue": 2500000.00,
          "email": "jane.doe@example.com",
          "username": "jane_doe",
          "password": "StrongP@ssw0rd!",
          "firstName": "Jane",
          "lastName": "Doe",
          "gender": "FEMALE",
          "dob": "1995-05-20",
          "attendedOn": "2025-09-09",
          "travellingOn": "2035-09-10",
          "maturityDate": "2075-01-01",
          "searchPattern": ".*",
          "papers": [
            "Learning Dynamics of LLM Fine-tuning — Yi Ren, Danica Sutherland",
            "SAM 2: Segment Anything in Images and Videos — Nikhila Ravi",
            "Faster Cascades via Speculative Decoding — Harikrishna Narasimhan",
            "Transformers Learn Low Sensitivity Functions — Bhavya Vasudeva"
          ],
          "address": {
            "addressLine1": "456 Elm Street",
            "addressLine2": "Suite 12C",
            "landmark": "Near City Mall",
            "city": "Los Angeles",
            "state": "CA",
            "country": "USA",
            "zipcode": "90001",
            "addressType": "CURRENT"
          },
          "ranks": [9,15]
        }
        """;
    private MockMvc mockMvc;

    @Autowired
    private EnrollmentAPIController enrollmentAPIController;
    @Autowired
    private GlobalRestAPIControllerAdvice globalRestAPIControllerAdvice;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(enrollmentAPIController)
                .setControllerAdvice(globalRestAPIControllerAdvice)
                .build();
    }

    @Test
    void testEnrollShouldReturn201Created() throws Exception {
        mockMvc.perform(
                    post("/api/v1/enrollments/{countryId}", "USA")
                            .param("sortBy", "firstName")
                            .param("sortingOrder", String.valueOf(SortingOrder.ASC))
                            .param("gender", String.valueOf(EnrollmentRequest.Gender.UNDISCLOSED))
                            .header("client-id", "A1234567")
                            .header("ip-address", "192.168.19.19")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .content(ENROLL_REQUEST_BODY)
                            .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }
}