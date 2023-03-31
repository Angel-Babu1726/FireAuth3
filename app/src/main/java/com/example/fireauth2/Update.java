package com.example.fireauth2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {


    EditText ex,nv;
    Button upbn;
    FirebaseFirestore ff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ex = findViewById(R.id.exval);
        nv = findViewById(R.id.nwval);
        upbn = findViewById(R.id.upbtn);


        upbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String exvl = ex.getText().toString().trim();
                String nvl = nv.getText().toString().trim();
                ex.setText("");
                nv.setText("");
                UpdateData(exvl,nvl);

            }

            private void UpdateData(String exvl, String nvl) {

                Map<String,Object> userdetails = new HashMap<>();
                userdetails.put("name",nvl);


                ff.collection("students").whereEqualTo("name",exvl).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String DocumentId = documentSnapshot.getId();





                            ff.collection("student").document(DocumentId).update(userdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Update.this, "Data updated", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Update.this, "update failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(new Intent(Update.this,HomeActivity.class));
                        }
                    }
                });


            }
        });


    }
}