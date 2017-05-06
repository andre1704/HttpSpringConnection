package com.sdacademy.slowinski.andrzej.httpspringconnection;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sdacademy.slowinski.andrzej.httpspringconnection.model.Task;
import com.sdacademy.slowinski.andrzej.httpspringconnection.service.TaskService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sdacademy.slowinski.andrzej.httpspringconnection.mapper.TaskMapper.taskToTaskDTO;

/**
 * Created by RENT on 2017-05-06.
 */

public class Adapter extends ArrayAdapter<Task> {
    private TaskService taskService;
    private boolean EditMode;
    public Adapter(Context context, List<Task> objects, TaskService taskService) {
        super(context, 0, objects);
        this.taskService=taskService;
    }
    
    public View getView(int position, View convertView, ViewGroup parent){
        final Task item=  getItem(position);
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.single_row,parent,false);
        }
        TextView itemNo= (TextView) convertView.findViewById(R.id.singleRowNo);
        TextView itemText= (TextView) convertView.findViewById(R.id.singleRowText);
        final CheckBox itemCheckBox= (CheckBox) convertView.findViewById(R.id.itemCHeckBox);
        checboxListener(item, itemCheckBox);

        itemNo.setText(String.valueOf(position));
        itemText.setText(item.getValue());
        itemCheckBox.setChecked(item.getCompleted());

        return convertView;
    }

    private void checboxListener(final Task item, final CheckBox itemCheckBox) {
        itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setCompleted(itemCheckBox.isChecked());
                Call<TaskDTO> call = taskService.updateTask(taskToTaskDTO(item));
                call.enqueue(new Callback<TaskDTO>() {
                    @Override
                    public void onResponse(Call<TaskDTO> call, Response<TaskDTO> response) {
                        TaskDTO received = response.body();
                        item.setCompleted(received.getCompleted());
                        notifyDataSetChanged();
                        Log.d("add_checked_status", "successed add checked status");
                    }

                    @Override
                    public void onFailure(Call<TaskDTO> call, Throwable t) {
                        Log.d("add_checked_status", "failure add checked status");
                    }
                });


            }
        });
    }
}
