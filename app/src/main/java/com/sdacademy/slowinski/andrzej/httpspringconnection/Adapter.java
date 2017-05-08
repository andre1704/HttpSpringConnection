package com.sdacademy.slowinski.andrzej.httpspringconnection;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

    public int getEditMode() {
        return editMode;
    }

    public void setEditMode(int editMode) {
        this.editMode = editMode;
    }

    private int editMode = -1;

    public Adapter(Context context, List<Task> objects, TaskService taskService, int editMode) {
        super(context, 0, objects);
        this.taskService = taskService;
        this.editMode = editMode;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Task item = getItem(position);
        if (convertView == null) {
            if (editMode == position) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_row_edit, parent, false);
            } else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_row, parent, false);
            }
        }
        if (editMode != position) {
            TextView itemNo = (TextView) convertView.findViewById(R.id.singleRowNo);
            TextView itemText = (TextView) convertView.findViewById(R.id.singleRowText);
            final CheckBox itemCheckBox = (CheckBox) convertView.findViewById(R.id.itemCHeckBox);

            checkboxListener(item, itemCheckBox);

            itemNo.setText(String.valueOf(position));
            itemText.setText(item.getValue());
            itemCheckBox.setChecked(item.getCompleted());
        } else {


            TextView itemNoE = (TextView) convertView.findViewById(R.id.singleRowNoEdit);
            TextView itemTextE = (TextView) convertView.findViewById(R.id.singleRowTextEdit);
            EditText itemTextEdit = (EditText) convertView.findViewById(R.id.edited_field);
            Button button = (Button) convertView.findViewById(R.id.save_editedEdit);
            final CheckBox itemCheckBoxE = (CheckBox) convertView.findViewById(R.id.itemCHeckBoxEdit);

            checkboxListener(item, itemCheckBoxE);
            buttonListener(item,button,itemTextEdit);

            itemNoE.setText(String.valueOf(position));
            itemTextE.setText(item.getValue());
            itemCheckBoxE.setChecked(item.getCompleted());

        }


        return convertView;
    }

    private void buttonListener(final Task item, Button button, final EditText editText) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<TaskDTO> call = taskService.updateTask(taskToTaskDTO(item));
                call.enqueue(new Callback<TaskDTO>() {
                    @Override
                    public void onResponse(Call<TaskDTO> call, Response<TaskDTO> response) {
                        TaskDTO received = response.body();
                        item.setValue(String.valueOf(editText.getText()));
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

    private void checkboxListener(final Task item, final CheckBox itemCheckBox) {
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
