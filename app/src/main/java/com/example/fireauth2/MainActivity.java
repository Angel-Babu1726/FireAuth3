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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    FirebaseFirestore ff;
    EditText name,dep,rollno;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        dep = findViewById(R.id.dep);
        rollno = findViewById(R.id.roll);
        bt = findViewById(R.id.addbtn);
        ff= FirebaseFirestore.getInstance();


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String  sd = name.getText().toString().trim();
                String  dp = dep.getText().toString().trim();
                String roll = rollno.getText().toString().trim();


                Map<String,Object> student = new HashMap<>();
                student.put("name",sd);
                student.put("dpn",dp);
                student.put("rno",roll);



                ff.collection("student").add(student).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(MainActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,HomeActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();

                    }
                });

                name.setText("");
                dep.setText("");
                rollno.setText("");
            }
        });
    }
}