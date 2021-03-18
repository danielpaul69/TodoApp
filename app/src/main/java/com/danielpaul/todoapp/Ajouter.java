package com.danielpaul.todoapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    Button supprimerButton;
    FloatingActionButton validerButton;

    String tacheId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        db = FirebaseFirestore.getInstance();

        titreTextView = findViewById(R.id.titre);
        descriptionTextView = findViewById(R.id.description);

        tacheId = getIntent().getStringExtra("id");

        supprimerButton = findViewById(R.id.supprimer);
        if (tacheId != null && !tacheId.trim().isEmpty()) {
            titreTextView.setText(getIntent().getStringExtra("titre"));
            descriptionTextView.setText(getIntent().getStringExtra("description"));

            supprimerButton.setVisibility(View.VISIBLE);
        }

        supprimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimer();
            }
        });

        validerButton = findViewById(R.id.btn_valider);
        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> tache = new HashMap<>();
                tache.put("titre", titreTextView.getText().toString());
                tache.put("description", descriptionTextView.getText().toString());

                if (tacheId != null && !tacheId.trim().isEmpty()) {
                    modifier(tache);
                } else {
                    ajouter(tache);
                }
            }
        });
    }

    public void ajouter(Map<String, Object> tache) {
        db.collection("taches")
                .add(tache)
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

    public void modifier(Map<String, Object> tache) {
        db.collection("taches")
                .document(tacheId)
                .set(tache)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.activity_ajouter), "Tâche modifiée avec succès ! ", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(findViewById(R.id.activity_ajouter), "Une erreur est survenue", Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    public void supprimer() {
        if (tacheId != null && !tacheId.trim().isEmpty()) {
            db.collection("taches")
                    .document(tacheId)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(findViewById(R.id.activity_ajouter), "Tâche modifiée avec succès ! ", Snackbar.LENGTH_LONG).show();
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
}