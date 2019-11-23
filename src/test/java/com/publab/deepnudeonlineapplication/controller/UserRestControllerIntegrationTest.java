package com.publab.deepnudeonlineapplication.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.publab.deepnudeonlineapplication.DeepnudeonlineApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = DeepnudeonlineApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class UserRestControllerIntegrationTest {
    private final String TEST_USERNAME = "test username";
    private final String TEST_FIRST_NAME = "test first name";
    private final String TEST_LAST_NAME = "test last name";
    private final String BAD_REQUEST_STATUS = "BAD_REQUEST";
    private final String CONSTRAINT_VIOLATION_MESSAGE = "Constraint violation";
    private final int USER_NEXT_AUTO_INCREMENT_ID = 4;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenInsertingUser_thenUserIsCreated() throws Exception {
        this.mockMvc.perform(post("/api/users")
                .param("username", TEST_USERNAME)
                .param("firstName", TEST_FIRST_NAME)
                .param("lastName", TEST_LAST_NAME)
                .param("avatarId", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(USER_NEXT_AUTO_INCREMENT_ID)));
    }

    @Test
    void whenUserHasEmptyUsername_thenValidationError() throws Exception {
        this.mockMvc.perform(post("/api/users")
                .param("username", "")
                .param("firstName", TEST_FIRST_NAME)
                .param("lastName", TEST_LAST_NAME)
                .param("avatarId", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(BAD_REQUEST_STATUS)))
                .andExpect(jsonPath("$.message", is(CONSTRAINT_VIOLATION_MESSAGE)))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                        "com.publab.deepnudeonlineapplication.model.User username: *Please provide valid username"
                )));
    }

    @Test
    void whenUserHasEmptyFirstName_thenValidationError() throws Exception {
        this.mockMvc.perform(post("/api/users")
                .param("username", TEST_USERNAME)
                .param("firstName", "")
                .param("lastName", TEST_LAST_NAME)
                .param("avatarId", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(BAD_REQUEST_STATUS)))
                .andExpect(jsonPath("$.message", is(CONSTRAINT_VIOLATION_MESSAGE)))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                        "com.publab.deepnudeonlineapplication.model.User firstName: *Please provide valid first name"
                )));
    }

    @Test
    void whenUserHasEmptyLastName_thenValidationError() throws Exception {
        this.mockMvc.perform(post("/api/users")
                .param("username", TEST_USERNAME)
                .param("firstName", TEST_FIRST_NAME)
                .param("lastName", "")
                .param("avatarId", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(BAD_REQUEST_STATUS)))
                .andExpect(jsonPath("$.message", is(CONSTRAINT_VIOLATION_MESSAGE)))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                        "com.publab.deepnudeonlineapplication.model.User lastName: *Please provide valid last name"
                )));
    }
}

