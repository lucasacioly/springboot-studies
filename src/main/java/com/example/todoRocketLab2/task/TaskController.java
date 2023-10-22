package com.example.todoRocketLab2.task;

import com.example.todoRocketLab2.util.Utils;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @GetMapping
    public ResponseEntity<List<TaskModel>>  getUserTasks(HttpServletRequest request){
        var userId = request.getAttribute("userId");
        var tasks = this.taskRepository.findByUserId((UUID) userId);

        return ResponseEntity.ok().body(tasks);
    }

    @PutMapping("/teste/{taskId}")
    public TaskModel update( @RequestBody TaskModel task, HttpServletRequest request, @PathVariable UUID taskId){
        task.setId(taskId);
        return this.taskRepository.save(task);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity partialUpdate( @RequestBody TaskModel task, HttpServletRequest request, @PathVariable UUID taskId){

        var bdTask = this.taskRepository.findById(taskId).orElse(null);

        if (bdTask == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task não encontrada");
        }

        var idUser = request.getAttribute("userId");

        if (!task.getUserId().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sem permissão para modificar essa tarefa");
        }

        Utils.copyNonNullProperties(task, bdTask);

        var updatedTask = this.taskRepository.save(task);

        return ResponseEntity.ok().body(updatedTask);
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskModel task, HttpServletRequest request){
        task.setUserId((UUID) request.getAttribute("userId"));

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(task.getDataIni()) || currentDate.isAfter(task.getDataFim())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de inicio/término menor que a data atual");
        }

        if (task.getDataIni().isAfter(task.getDataFim())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de inicio maior que a data de término");
        }

        var createdTask = this.taskRepository.save(task);

        System.out.println("TaskController.create");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }


    @PostMapping("/create/{taskId}")
    public ResponseEntity delete(@PathVariable UUID taskId, HttpServletRequest request){
        var idUser = request.getAttribute("userId");

        this.taskRepository.deleteById(taskId);

        return ResponseEntity.ok().body("Task apagada");
    }


}
