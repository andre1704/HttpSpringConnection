package com.sdacademy.slowinski.andrzej.httpspringconnection.mapper;

import com.sdacademy.slowinski.andrzej.httpspringconnection.model.Task;
import com.sdacademy.slowinski.andrzej.httpspringconnection.TaskDTO;

/**
 * Created by RENT on 2017-05-06.
 */

public class TaskMapper {

    public static TaskDTO taskToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO((long) 30,task.getId(),task.getValue(),task.getCompleted());
        return taskDTO;
    }
    public static Task taskDTOTotask(TaskDTO taskDTO){
        Task task=new Task(taskDTO.getId(),taskDTO.getValue(),taskDTO.getCompleted());
        return task;
    }
}
