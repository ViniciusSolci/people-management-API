package com.peoplemanagementapi.framework;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void createPerson() throws Exception {
        mockMvc.perform(post("/v1/person")
                        .contentType("application/json")
                        .content("{\"name\":\"person test\", \"birthDate\":\"2013-10-21\", \"addresses\":[]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.is("person test")))
                .andExpect(jsonPath("$.birthDate", Matchers.is("2013-10-21")));
    }

    @Test
    void updatePerson() throws Exception {
        mockMvc.perform(post("/v1/person")
                .contentType("application/json")
                .content("{\"name\":\"person test\", \"birthDate\":\"2013-10-21\", \"addresses\":[]}"));

        mockMvc.perform(put("/v1/person/1")
                .contentType("application/json")
                .content("{\"name\":\"test person\", \"birthDate\":\"2000-01-01\", \"addresses\":[]}"))
                .andExpect(jsonPath("$.name", Matchers.is("test person")))
                .andExpect(jsonPath("$.birthDate", Matchers.is("2000-01-01")));
    }

    @Test
    void getPersonById() throws Exception{
        mockMvc.perform(post("/v1/person")
                .contentType("application/json")
                .content("{\"name\":\"person test\", \"birthDate\":\"2013-10-21\", \"addresses\":[]}"));

        mockMvc.perform(get("/v1/person/1")
                        .contentType("application/json")
                        .content("{\"name\":\"test person\", \"birthDate\":\"2000-01-01\", \"addresses\":[]}"))
                .andExpect(jsonPath("$.name", Matchers.is("person test")))
                .andExpect(jsonPath("$.birthDate", Matchers.is("2013-10-21")));

    }

    @Test
    void getAllPeople() throws Exception{
        mockMvc.perform(post("/v1/person")
                .contentType("application/json")
                .content("{\"name\":\"person test\", \"birthDate\":\"2013-10-21\", \"addresses\":[]}"));

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
    }

    @Test
    void getAllAddressForPerson() throws Exception{
    }

    @Test
    void updatePersonMainAddress() throws Exception{
    }
}