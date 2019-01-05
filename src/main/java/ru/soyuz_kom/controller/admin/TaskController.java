package ru.soyuz_kom.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.Task;
import ru.soyuz_kom.repository.TaskRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController extends AdminController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping({"v1/task/"})
    @ResponseBody
    public Iterable<Task> index() {
        Iterable<Task> task = taskRepository.findAll();

        return task;
    }

    @GetMapping({"v1/task/{id}"})
    public Optional<Task> show(@PathVariable Integer id) {
        Optional<Task> task = taskRepository.findById(id);

        return task;
    }

    @PostMapping({"v1/task/store"})
    @CacheEvict(value="schedule", allEntries=true)
    @ResponseBody
    public ResponseEntity store(@Valid @RequestBody Task task, Errors errors) {
        System.out.println("v1/task/store: " + task);
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Task addTask = taskRepository.save(task);

        return new ResponseEntity<Task>(addTask, HttpStatus.OK);
    }

    @DeleteMapping({"v1/task/delete/{id}"})
    @CacheEvict(value="schedule", allEntries=true)
    @ResponseBody
    public Boolean delete(@PathVariable Integer id) {
        System.out.println("delete task " + id);
        taskRepository.deleteById(id);

        return true;
    }
}
