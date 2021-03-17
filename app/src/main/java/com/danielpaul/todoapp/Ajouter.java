package com.danielpaul.todoapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Ajouter extends AppCompatActivity {
    FirebaseFirestore db;

    TextInputEditText titreTextView, descriptionTextView;

    FloatingActionButton validerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        db = FirebaseFirestore.getInstance();

        titreTextView = findViewById(R.id.titre);
        descriptionTextView = findViewById(R.id.description);

        validerButton = findViewById(R.id.btn_valider);
        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouter();
            }
        });
    }

    public void ajouter(){
        Map<String, Object> user = new HashMap<>();
        user.put("titre", titreTextView.getText().toString());
        user.put("description", descriptionTextView.getText().toString());

// Add a new document with a generated ID
        db.collection("taches")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Snackbar.make(findViewById(R.id.activity_ajouter), "Tâches ajoutée avec succès !", Snackbar.LENGTH_LONG).show();

                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(findViewById(R.id.activity_ajouter), "Une erreur est survenue", Snackbar.LENGTH_LONG).show();
                    }
                });
    }
}