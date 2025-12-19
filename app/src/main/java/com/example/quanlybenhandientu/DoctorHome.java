package com.example.quanlybenhandientu;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DoctorHome extends AppCompatActivity {
    String[] luaChon = {"Bệnh nhân", "Bác sĩ"};
    TextView name, chuyenKhoa;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String ten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctorhome);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        name = findViewById(R.id.name);
        getInfo();
    }
    private void getInfo(){
        String email = mAuth.getCurrentUser().getEmail().toString();
        String object;
        db.collection("users").document(email).collection("info").document("info").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ten = document.getString("ten");
                        name.setText(ten);
                        Log.d("CHECK", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("CHECK", "No such document");
                    }
                } else {
                    Log.d("CHECK", "Failed", task.getException());
                }

            }
        });
    }
}