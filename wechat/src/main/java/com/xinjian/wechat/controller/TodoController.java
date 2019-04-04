package com.freshman.controller;

import com.freshman.vo.TodoVo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "${api.base-path}/todo")
public class TodoController {

    private static List<TodoVo> todos = new ArrayList();

    @PostMapping
    public List<TodoVo> addTodo(@RequestBody TodoVo todo) {
        System.out.println(todo);
        todos.add(todo);
        return todos;
    }

    @DeleteMapping(value = "/{id}")
    public List<TodoVo> deleteTodo(@PathVariable(value = "id") final String id) {
        todos = todos.stream().filter(todo -> {
            if (id.equals(todo.getId())) return false;
            return true;
        }).collect(Collectors.toList());
        return todos;

    }

    @GetMapping
    public List<TodoVo> getTodos() {
        return todos;
    }

    @GetMapping("/{id}")
    public TodoVo getTodo(@PathVariable(value = "id") final String id) {
        return todos.stream().filter(todo -> id.equals(todo.getId())).collect(Collectors.toList()).get(0);
    }

    @PutMapping
    public TodoVo toggleTodo(@RequestBody final TodoVo todo) {

        todos.forEach(
                todoVo -> {
                    if (todoVo.getId().equals(todo.getId())) {
                        todoVo.setCompleted(!todo.getCompleted());
                        todoVo.setDesc(todo.getDesc());
                    }
                }
        );
        todo.setCompleted(!todo.getCompleted());
        return todo;
    }

    @PutMapping("/all")
    public List<TodoVo> toggleAll(@RequestBody final boolean status) {
        todos.forEach(
                todoVo -> {
                    todoVo.setCompleted(status);
                }
        );
        return todos;
    }

    @DeleteMapping(value = "/completed")
    public List<TodoVo> deleteCompleted() {
         todos= todos.stream().filter(todo -> {
            if (todo.getCompleted())
                return false;
            return true;
        }).collect(Collectors.toList());

         return todos;

    }
}
