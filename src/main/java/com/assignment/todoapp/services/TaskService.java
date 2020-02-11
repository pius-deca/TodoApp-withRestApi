package com.assignment.todoapp.services;

import com.assignment.todoapp.models.Task;
import com.assignment.todoapp.repository.TaskRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    private TaskRespository taskRespository;

    @Autowired
    public TaskService(TaskRespository taskRespository){
        this.taskRespository = taskRespository;
    }

    public Task createTask(Task task){
        Task todo = null;
        try{
            todo = taskRespository.save(task);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return todo;
    }

    public Task viewTask(Integer id){
        Task todo = taskRespository.findById(id).orElse(null);
        if (todo != null){
            return taskRespository.save(todo);
        }
        return null;
    }

    public List<Task> viewAllTask(){
        List<Task> t = null;
        try{
            t = taskRespository.findAll();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return t;
    }

    public Task updateTask(Task taskToUpdate, Integer id){
        Task todo = taskRespository.findById(id).orElse(null);
        if (todo != null){
            todo.setTitle(taskToUpdate.getTitle());
            todo.setDescription(taskToUpdate.getDescription());

            Date time = new Date();
            long newTime = time.getTime();
            Timestamp completedTime = new Timestamp(newTime);
            String status = taskToUpdate.getStatus();
            if (status.equalsIgnoreCase("completed")){
                todo.setStatus(status);
                todo.setCompletedAt(completedTime);
            }
            else{
                todo.setStatus(status);
                todo.setCompletedAt(null);
            }
            return taskRespository.save(todo);
        }
        return null;
    }

    public String deleteTask(Integer id){
        if (taskRespository.existsById(id)){
            taskRespository.deleteById(id);
            return "Todo deleted successfully";
        }
        return null;
    }

    public List<Task> viewByStatus(String status){
        return taskRespository.findByStatus(status);
    }
}
