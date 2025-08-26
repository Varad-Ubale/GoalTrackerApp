package com.vktech.goaltrackerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

    private List<Goal> goalList;
    private OnGoalClickListener onGoalClickListener;

    public interface OnGoalClickListener {
        void onGoalClick(Goal goal);
    }

    public GoalAdapter(List<Goal> goalList, OnGoalClickListener onGoalClickListener) {
        this.goalList = goalList;
        this.onGoalClickListener = onGoalClickListener;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_item, parent, false);
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal goal = goalList.get(position);
        holder.titleTextView.setText(goal.getTitle());
        holder.descriptionTextView.setText(goal.getDescription());
        holder.progressBar.setProgress(goal.getProgress());
        holder.progressTextView.setText(goal.getProgress() + "%");
        holder.itemView.setOnClickListener(v -> onGoalClickListener.onGoalClick(goal));
    }

    @Override
    public int getItemCount() { return goalList.size(); }

    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public ProgressBar progressBar;
        public TextView progressTextView;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.goal_title_text_view);
            descriptionTextView = itemView.findViewById(R.id.goal_description_text_view);
            progressBar = itemView.findViewById(R.id.goal_progress_bar);
            progressTextView = itemView.findViewById(R.id.progress_text_view);
        }
    }
}