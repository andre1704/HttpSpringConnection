package com.sdacademy.slowinski.andrzej.httpspringconnection.service;

import com.sdacademy.slowinski.andrzej.httpspringconnection.TaskDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by RENT on 2017-05-06.
 */

public interface TaskService {
    @GET("/api/task/all/{user}")
    Call<List<TaskDTO>> findAllTaskByUser(@Path("user") Long user);

    @PUT("/api/task")
    Call<TaskDTO> updateTask(@Body TaskDTO taskDTO);

    @DELETE("/api/task/{id}")
    Call<Void> deleteTask(@Path("id") Long id);

    @GET("/api/task/{id}")
    Call<TaskDTO> getTask(@Path("id") Long id);

}
