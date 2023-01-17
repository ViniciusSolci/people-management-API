package com.peoplemanagementapi.framework;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void createPersonForOtherTests() throws Exception {
        mockMvc.perform(post("/v1/person")
                .contentType("application/json")
                .content("{\"name\":\"person test\", \"birthDate\":\"2013-10-21\", \"addresses\":[{\"cityName\": \"Testpolis\", \"stateName\": \"UF\", \"streetName\": \"Test Avenue\", \"streetNumber\": \"1234\", \"zipCode\": \"12345\"}]}"));
    }

    @Test
    void createPersonTest() throws Exception {
        mockMvc.perform(post("/v1/person")
                        .contentType("application/json")
                        .content("{\"name\":\"person test\", \"birthDate\":\"2013-10-21\", \"addresses\":[]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.is("person test")))
                .andExpect(jsonPath("$.birthDate", Matchers.is("2013-10-21")));
    }

    @Test
    void updatePerson() throws Exception {
        mockMvc.perform(put("/v1/person/1")
                        .contentType("application/json")
                        .content("{\"name\":\"test person\", \"birthDate\":\"2000-01-01\", \"addresses\":[]}"))
                .andExpect(jsonPath("$.name", Matchers.is("test person")))
                .andExpect(jsonPath("$.birthDate", Matchers.is("2000-01-01")));
    }

    @Test
    void getPersonById() throws Exception {
        mockMvc.perform(get("/v1/person/1"))
                .andExpect(jsonPath("$.name", Matchers.is("person test")))
                .andExpect(jsonPath("$.birthDate", Matchers.is("2013-10-21")));

    }

    @Test
    void getAllPeople() throws Exception {
        mockMvc.perform(post("/v1/person")
                .contentType("application/json")
                .content("{\"name\":\"test person\", \"birthDate\":\"2000-01-01\", \"addresses\":[]}"));

        mockMvc.perform(get("/v1/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.content.[0].name", Matchers.is("person test")))
                .andExpect(jsonPath("$.content.[1].name", Matchers.is("test person")));

    }

    @Test
    void createPersonAddress() throws Exception {
        mockMvc.perform(post("/v1/person/1/address")
                .contentType("application/json")
                .content("{\n" +
                        "\"cityName\": \"test\",\n" +
                        "\"stateName\": \"test\",\n" +
                        "\"streetName\": \"test\",\n" +
                        "\"streetNumber\": \"1234\",\n" +
                        "\"zipCode\": \"12345\"\n" +
                        "}"));

        mockMvc.perform(get("/v1/person/1"))
                .andExpect(jsonPath("$.name", Matchers.is("person test")))
                .andExpect(jsonPath("$.birthDate", Matchers.is("2013-10-21")))
                .andExpect(jsonPath("$.addresses", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.addresses.[1].cityName", Matchers.is("test")));

    }

    @Test
    void getAllAddressForPerson() throws Exception {
        mockMvc.perform(get("/v1/person/1/address"))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].cityName", Matchers.is("Testpolis")))
                .andExpect(jsonPath("$.[0].stateName", Matchers.is("UF")))
                .andExpect(jsonPath("$.[0].streetName", Matchers.is("Test Avenue")))
                .andExpect(jsonPath("$.[0].streetNumber", Matchers.is("1234")))
                .andExpect(jsonPath("$.[0].zipCode", Matchers.is("12345")))
                .andExpect(jsonPath("$.[0].mainAddress", Matchers.is(false)));
    }

    @Test
    void updatePersonMainAddress() throws Exception {
        mockMvc.perform(put("/v1/person/1/address/2"));

        mockMvc.perform(get("/v1/person/1/address"))
                .andExpect(jsonPath("$.[0].mainAddress", Matchers.is(true)));
    }
}