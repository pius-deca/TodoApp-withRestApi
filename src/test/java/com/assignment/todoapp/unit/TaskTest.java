package com.assignment.todoapp.unit;

import com.assignment.todoapp.controllers.TasksController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TaskTest {

    @Autowired
    private TasksController tasksController;

    @Test
    public void testController(){
        assertNotNull(tasksController);
    }
}
