package com.danielpaul.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.danielpaul.todoapp.models.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ListView todosListView;

    List<Todo> todos = new ArrayList<>();
    List<String> listeTodos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todosListView = findViewById(R.id.liste_todos);

        db = FirebaseFirestore.getInstance();

        chargerTodos();

        FloatingActionButton ajouter = findViewById(R.id.btn_ajouter);
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Ajouter.class);
                startActivity(i);
            }
        });
    }

    public void chargerTodos() {
        db.collection("taches").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("ERROR", "Listen failed.", e);
                }

                todos = new ArrayList<>();
                listeTodos = new ArrayList<>();
                for (QueryDocumentSnapshot document : value) {
                    todos.add(new Todo(document.getId(), document.getString("titre"), document.getString("description")));
                    listeTodos.add(document.getString("titre"));
                }
                todosListView.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, listeTodos));
            }
        });
    }
}