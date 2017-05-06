package com.sdacademy.slowinski.andrzej.httpspringconnection.model;

/**
 * Created by RENT on 2017-05-06.
 */

public class Task {
    private Long id;
    private String value;
    private Boolean completed;

    public Task(Long id, String value, Boolean completed) {
        this.id = id;
        this.value = value;
        this.completed = completed;
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
}
