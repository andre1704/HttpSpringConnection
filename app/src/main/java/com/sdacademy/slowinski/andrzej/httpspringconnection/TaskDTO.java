package com.sdacademy.slowinski.andrzej.httpspringconnection;

import com.sdacademy.slowinski.andrzej.httpspringconnection.model.Task;

/**
 * Created by RENT on 2017-05-06.
 */

public class TaskDTO {
    private Boolean completed;
    private Long user;
    private Long id;
    private String value;

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public TaskDTO(Long user, Long id, String value, Boolean completed) {

        this.user = user;
        this.id = id;
        this.value = value;
        this.completed = completed;
    }


}
