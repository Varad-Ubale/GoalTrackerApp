package com.vktech.goaltrackerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SubtaskAdapter extends RecyclerView.Adapter<SubtaskAdapter.SubtaskViewHolder> {

    private List<SubTask> subTaskList;
    private OnSubtaskChangeListener onSubtaskChangeListener;

    public interface OnSubtaskChangeListener {
        void onSubtaskChanged();
    }

    public SubtaskAdapter(List<SubTask> subTaskList, OnSubtaskChangeListener onSubtaskChangeListener) {
        this.subTaskList = subTaskList;
        this.onSubtaskChangeListener = onSubtaskChangeListener;
    }

    @NonNull
    @Override
    public SubtaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_item, parent, false);
        return new SubtaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubtaskViewHolder holder, int position) {
        SubTask subTask = subTaskList.get(position);
        holder.subtaskCheckBox.setText(subTask.getTitle());
        holder.subtaskCheckBox.setChecked(subTask.isCompleted());
        holder.subtaskCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            subTask.setCompleted(isChecked);
            if (onSubtaskChangeListener != null) {
                onSubtaskChangeListener.onSubtaskChanged();
            }
        });
    }

    @Override
    public int getItemCount() { return subTaskList.size(); }

    public static class SubtaskViewHolder extends RecyclerView.ViewHolder {
        public CheckBox subtaskCheckBox;

        public SubtaskViewHolder(@NonNull View itemView) {
            super(itemView);
            subtaskCheckBox = itemView.findViewById(R.id.subtask_checkbox);
        }
    }
}