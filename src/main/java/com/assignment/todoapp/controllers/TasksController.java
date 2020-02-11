package com.assignment.todoapp.controllers;

import com.assignment.todoapp.models.Task;
import com.assignment.todoapp.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class TasksController{

    private TaskService taskService;

    @Autowired
  public TasksController(TaskService taskService){
      this.taskService = taskService;
  }

    @PostMapping
    public MyResponse<Task> creatTask(@RequestBody Task task, HttpServletResponse response){
        Task todo = taskService.createTask(task);
        HttpStatus status = HttpStatus.CREATED;
        String message = "Todo created successfully";
        if (todo == null){
            status = HttpStatus.BAD_REQUEST;
            message = "Todo cannot be created";
        }
        response.setStatus(status.value());
        return new MyResponse<>(status, message, todo);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public MyResponse<Task> viewOneTask(@PathVariable Integer id, HttpServletResponse response){
        Task todo = taskService.viewTask(id);
        HttpStatus status = HttpStatus.OK;
        String message = "Todo of id: "+id+" retrieved successfully";
        if (todo == null){
            status = HttpStatus.BAD_REQUEST;
            message = "Todo does not exist";
        }
        response.setStatus(status.value());
        return new MyResponse<>(status, message, todo);
    }

    @GetMapping
    public MyResponse<List<Task>> viewAllTask(HttpServletResponse response){
        List<Task> todos = taskService.viewAllTask();
        HttpStatus status = HttpStatus.OK;
        String message = "All Todo's retrieved successfully";
        if (todos.isEmpty()){
            status = HttpStatus.BAD_REQUEST;
            message = "No task available";
        }
        response.setStatus(status.value());
        return new MyResponse<>(status, message, todos);
    }

    @RequestMapping(path = "/status/{status}", method = RequestMethod.GET)
    public MyResponse<List<Task>> viewStatus(@PathVariable String status, HttpServletResponse response){
        List<Task> todos = taskService.viewByStatus(status);
        HttpStatus statusCode = HttpStatus.OK;
        String message = "All " + status + " Todo's retrieved successfully";
        if (todos.isEmpty()){
            statusCode = HttpStatus.BAD_REQUEST;
            message = "There is no todo that is " +status;
        }
        response.setStatus(statusCode.value());
        return new MyResponse<>(statusCode, message, todos);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PATCH)
    public MyResponse<Task> updateTask(@RequestBody Task taskToUpdate, @PathVariable Integer id,  HttpServletResponse response){
        Task todo = taskService.updateTask(taskToUpdate, id);
        HttpStatus status = HttpStatus.OK;
        String message = "Todo of id: "+id+" updated successfully";
        if (todo == null){
            status = HttpStatus.BAD_REQUEST;
            message = "Todo does not exist";
        }
        response.setStatus(status.value());
        return new MyResponse<>(status, message, todo);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public MyResponse<String> deleteTask(@PathVariable Integer id, HttpServletResponse response){
        String todo = taskService.deleteTask(id);
        HttpStatus status = HttpStatus.OK;
        String message = "Todo of id: "+id+" deleted successfully";
        if (todo == null){
            status = HttpStatus.BAD_REQUEST;
            message = "Todo does not exist or was deleted";
        }
        response.setStatus(status.value());
        return new MyResponse<>(status, message, todo);
    }
}
