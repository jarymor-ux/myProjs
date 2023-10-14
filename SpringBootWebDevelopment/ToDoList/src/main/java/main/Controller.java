package main;

import main.Model.Task;
import main.Model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class Controller {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String getTime(){
        return LocalDateTime.now().toString();
    }
    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(@RequestBody Task task) {
        task.setCreationTime(LocalDateTime.now());
        task.setDone(false);

        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/tasks/{ID}")
    public ResponseEntity getTask(@PathVariable int ID){
        Optional<Task> task = taskRepository.findById(ID);
        if (taskRepository.findById(ID).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        System.out.println(task);
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = (List<Task>) taskRepository.findAll();

        return ResponseEntity.ok(tasks);
    }

    @PatchMapping("/tasks/{ID}")
    public ResponseEntity<Task> updateTask(@PathVariable int ID, @RequestBody Task task) {
        Task existingTask = taskRepository.findById(ID).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + ID));


        if (task.getTitle() != null) {
            existingTask.setTitle(task.getTitle());
        }
        if (task.getDescription() != null) {
            existingTask.setDescription(task.getDescription());
        }
        if (task.isDone()) {
            existingTask.setDone(task.isDone());
        }

        taskRepository.save(existingTask);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/tasks/{ID}")
    public ResponseEntity<Task> deleteTask(@PathVariable int ID){

        if (taskRepository.findById(ID).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            taskRepository.deleteById(ID);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

}
