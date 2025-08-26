package com.vktech.goaltrackerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GoalAdapter.OnGoalClickListener {

    private RecyclerView goalsRecyclerView;
    private GoalAdapter goalAdapter;
    private List<Goal> goalList;
    private FloatingActionButton addGoalFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goalList = new ArrayList<>();

        goalsRecyclerView = findViewById(R.id.goals_recycler_view);
        goalsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        goalAdapter = new GoalAdapter(goalList, this);
        goalsRecyclerView.setAdapter(goalAdapter);

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        goalsRecyclerView.setLayoutAnimation(animation);

        addGoalFab = findViewById(R.id.add_goal_fab);
        addGoalFab.setOnClickListener(view -> showAddGoalDialog());
    }

    private void showAddGoalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_goal, null);
        builder.setView(dialogView);

        final EditText titleEditText = dialogView.findViewById(R.id.edit_text_title);
        final EditText descriptionEditText = dialogView.findViewById(R.id.edit_text_description);
        Button saveButton = dialogView.findViewById(R.id.button_save_goal);
        Button cancelButton = dialogView.findViewById(R.id.button_cancel);

        final AlertDialog dialog = builder.create();

        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();

            if (!title.isEmpty()) {
                Goal newGoal = new Goal(title, description);
                goalList.add(0, newGoal);
                goalAdapter.notifyItemInserted(0);
                goalsRecyclerView.scrollToPosition(0);
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public void onGoalClick(Goal goal) {
        showManageSubtasksDialog(goal);
    }

    private void showManageSubtasksDialog(Goal goal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_manage_subtasks, null);
        builder.setView(dialogView);

        TextView titleTextView = dialogView.findViewById(R.id.subtask_dialog_title);
        RecyclerView subtasksRecyclerView = dialogView.findViewById(R.id.subtasks_recycler_view);
        EditText newSubtaskEditText = dialogView.findViewById(R.id.edit_text_new_subtask);
        Button addSubtaskButton = dialogView.findViewById(R.id.button_add_subtask);

        titleTextView.setText("Sub-tasks for: " + goal.getTitle());

        SubtaskAdapter subtaskAdapter = new SubtaskAdapter(goal.getSubTaskList(), () -> {
            goalAdapter.notifyItemChanged(goalList.indexOf(goal));
        });
        subtasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subtasksRecyclerView.setAdapter(subtaskAdapter);

        addSubtaskButton.setOnClickListener(v -> {
            String subtaskTitle = newSubtaskEditText.getText().toString().trim();
            if (!subtaskTitle.isEmpty()) {
                goal.addSubTask(new SubTask(subtaskTitle));
                subtaskAdapter.notifyItemInserted(goal.getSubTaskList().size() - 1);
                newSubtaskEditText.setText("");
                goalAdapter.notifyItemChanged(goalList.indexOf(goal));
            }
        });

        builder.setPositiveButton("Done", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}