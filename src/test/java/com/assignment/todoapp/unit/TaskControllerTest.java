package com.assignment.todoapp.unit;

import com.assignment.todoapp.controllers.MyResponse;
import com.assignment.todoapp.controllers.TasksController;
import com.assignment.todoapp.models.Task;
import com.assignment.todoapp.repository.TaskRespository;
import com.assignment.todoapp.services.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    private static Task task1;
    private static Task task2;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TasksController tasksController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        task1 = new Task("title1", "desc1");
        task2 = new Task("title2", "desc2");
        task1.setId(1);
        task2.setId(2);
    }

    private static String asJsonString(final Object obj) {
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createTodo() throws Exception{
        MyResponse<Task> p = tasksController.creatTask(task1);
        Mockito.verify(taskService, Mockito.times(1)).createTask(task1);

    }

    @Test
    public void getTodo() throws Exception{
        Mockito.when(taskService.viewTask(task1.getId())).thenReturn(task1);
        MyResponse<Task> p = tasksController.viewOneTask(task1.getId());
        assertThat(p.getStatus(), is(HttpStatus.OK));
        assertThat(p.getData(), is(task1));
    }

    @Test
    public void getAllTodo() throws Exception{
       Mockito.when(taskService.viewAllTask()).thenReturn(Arrays.asList(task1,task2));
       assertThat(tasksController.viewAllTask().getData().size(), is(2));
       assertThat(tasksController.viewAllTask().getStatus(), is(HttpStatus.OK));
    }

    @Test
    public void update_a_todo() throws Exception{
        Task newTodo = new Task("new title", "new desc");
        Mockito.when(taskService.updateTask(newTodo, task1.getId())).thenReturn(newTodo);
        assertThat(tasksController.updateTask(newTodo, task1.getId()).getData(), is(newTodo));
        assertThat(tasksController.updateTask(newTodo, task1.getId()).getStatus(), is(HttpStatus.OK));
    }

    @Test
    public void deleteTodo() throws Exception{
        tasksController.deleteTask(task1.getId());
        Mockito.verify(taskService, Mockito.times(1)).deleteTask(task1.getId());
    }

    @Test
    public void get_todos_by_status() throws Exception{
        Mockito.when(taskService.viewByStatus("pending")).thenReturn(Arrays.asList(task1, task2));
        assertThat(tasksController.viewStatus("pending").getData().size(), is(2));
        assertThat(tasksController.viewStatus("pending").getStatus(), is(HttpStatus.OK));
    }
}
