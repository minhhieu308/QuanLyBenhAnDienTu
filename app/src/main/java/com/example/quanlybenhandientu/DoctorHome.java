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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctorhome);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        getInfo();
    }
    private void getInfo(){
        String email = mAuth.getCurrentUser().getEmail().toString();
        String object;
        db.collection("users").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("CHECK", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("CHECK", "No such document");
                    }
                } else {
                    Log.d("CHECK", "Failed", task.getException());
                }

            }
        });
/*
        db.collection("users")
                .document(email).collection().document("info").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("CHECK", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("CHECK", "Error getting documents.", task.getException());
                        }
                    }
                });*/
    }
}