package com.example.midtermtest_winter23;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    ListView list;
    FragmentManager fragmentManager;
    RecyclerView recyclerList;
    ArrayList<Task> tasks;
    ActivityResultLauncher<Intent> todoResultLauncher;
    TasksRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tasks = ((MyApp)getApplication()).taskArrayList;
        recyclerList = findViewById(R.id.recycler_list);
        fragmentManager = getSupportFragmentManager();

//        list = findViewById(R.id.listoftasks);
   //     TasksBaseAdapter baseadapter = new TasksBaseAdapter(tasks,this);
//        list.setAdapter(adapter);


        adapter = new TasksRecyclerViewAdapter(tasks, this);
        recyclerList.setAdapter(adapter);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));

        todoResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            Task newToDo = data.getParcelableExtra("newTodo");
                            tasks.add(newToDo);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.addNewTodoID:{
                Intent i = new Intent(this, AddNewToDo.class);
             //   startActivity(i);
                todoResultLauncher.launch(i);
                break;
            }

            case R.id.closeID:{
                finish();
                break;
            }
        }
        return true;
    }


}
