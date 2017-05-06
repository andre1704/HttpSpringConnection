package com.sdacademy.slowinski.andrzej.httpspringconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sdacademy.slowinski.andrzej.httpspringconnection.model.Task;
import com.sdacademy.slowinski.andrzej.httpspringconnection.service.TaskService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sdacademy.slowinski.andrzej.httpspringconnection.mapper.TaskMapper.taskDTOTotask;
import static com.sdacademy.slowinski.andrzej.httpspringconnection.mapper.TaskMapper.taskToTaskDTO;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private List<Task> tasks;
    private ListView listView;
    private Adapter adapter;
    private TaskService taskService;
    private Button button;
    private List<TaskDTO> taskDTOs;
    private final  String BASE_URL="https://shrouded-fjord-81597.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tasks=new ArrayList<>();
        taskDTOs=new ArrayList<>();
        editText = (EditText) findViewById(R.id.edit);
        listView= (ListView) findViewById(R.id.list);
        button= (Button) findViewById(R.id.button);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        taskService=retrofit.create(TaskService.class);
        adapter=new Adapter(this,tasks,taskService);
        listView.setAdapter(adapter);
        Call<List<TaskDTO>> call =taskService.findAllTaskByUser((long) 30);
        call.enqueue(new Callback<List<TaskDTO>>() {
            @Override
            public void onResponse(Call<List<TaskDTO>> call, Response<List<TaskDTO>> response) {
                for(int i=0;i<response.body().size();i++){
                    tasks.add(taskDTOTotask(response.body().get(i)));
                }
                adapter.notifyDataSetChanged();
                Log.d("successLog","success download values from the server");
            }

            @Override
            public void onFailure(Call<List<TaskDTO>> call, Throwable t) {
                Log.d("failLog","fail to download values from the server");
            }
        });
        removeRecord();
    }

    private void removeRecord() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Task task= tasks.get(position);
                taskService.deleteTask(task.getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        tasks.remove(position);
                        Log.d("successLog","success delete record from the server");
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("failLog","fail to delete record from the server");
                    }
                });

                return false;
            }
        });
    }

    public void onClick(View view){
        final Task[] task = {new Task(null, editText.getText().toString(), false)};

        taskService.updateTask(taskToTaskDTO(task[0])).enqueue(new Callback<TaskDTO>() {
            @Override
            public void onResponse(Call<TaskDTO> call, Response<TaskDTO> response) {
                task[0] =taskDTOTotask(response.body());
                tasks.add(task[0]);
                Log.d("add_record_log", "successed add new record");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TaskDTO> call, Throwable t) {
                Log.d("add_record_log", "failured add new record");
            }
        });

    }


}
