package com.assignment.todoapp.integration;

import com.assignment.todoapp.controllers.TasksController;
import com.assignment.todoapp.models.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TasksController tasksController;

    private static String asJsonString(final Object obj) {
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createTodo() throws Exception{
        Task task = new Task("title", "desc");

        this.mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(task)))
        .andDo(print()
        ).andExpect(status().isCreated()
        ).andExpect(content().string(containsString("pending")));
    }

    @Test
    public void getTodo() throws Exception{
        this.mockMvc.perform(get("/1").contentType(MediaType.APPLICATION_JSON))
        .andDo(print()
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("pending")));
    }

    @Test
    public void getAllTodo() throws Exception{
        this.mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
        .andDo(print()
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("All Todo's retrieved successfully")));
    }

    @Test
    public void deleteTodo() throws Exception{
        this.mockMvc.perform(delete("/1").contentType(MediaType.APPLICATION_JSON))
        .andDo(print()
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Todo of id: 1 deleted successfully")));
    }

    @Test
    public void updateTodo() throws Exception{
        this.mockMvc.perform(patch("/2").contentType(MediaType.APPLICATION_JSON))
        .andDo(print()
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Todo of id: 2 updated successfully")));
    }

}
