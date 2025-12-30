package com.example.quanlybenhandientu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditThongTinDoctorActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText hoEditText, tenEditText, sdtEditText, chuyenKhoaEditText, diaChiEditText;
    private String ho, ten, sdt, chuyenKhoa, diaChi;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_thong_tin_doctor);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        hoEditText = findViewById(R.id.ho_editProfile);
        tenEditText = findViewById(R.id.ten_editProfile);
        sdtEditText = findViewById(R.id.sdt_editProfile);
        chuyenKhoaEditText = findViewById(R.id.chuyenKhoa_editProfile);
        diaChiEditText = findViewById(R.id.diaChi_editProfile);
        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ho = hoEditText.getText().toString();
                ten = tenEditText.getText().toString();
                sdt = sdtEditText.getText().toString();
                chuyenKhoa = chuyenKhoaEditText.getText().toString();
                diaChi = diaChiEditText.getText().toString();
                user.put("ho", ho);
                user.put("ten", ten);
                user.put("phone", sdt);
                user.put("chuyenKhoa", chuyenKhoa);
                user.put("diaChi", diaChi);
                saveInfo(user);
            }
        });
        getInfo();
    }
    private void getInfo(){
        FirebaseUser currUser = mAuth.getCurrentUser();
        String email = currUser.getEmail();
        db.collection("users").document(email).collection("info").document("info").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(document.exists()){
                    ten = document.getString("ten");
                    tenEditText.setText(Objects.requireNonNullElse(ten, ""));

                    ho = document.getString("ho");
                    hoEditText.setText(Objects.requireNonNullElse(ho, ""));

                    sdt = document.getString("phone");
                    sdtEditText.setText(Objects.requireNonNullElse(sdt, ""));

                    chuyenKhoa = document.getString("chuyenKhoa");
                    chuyenKhoaEditText.setText(Objects.requireNonNullElse(chuyenKhoa, ""));

                    diaChi = document.getString("diaChi");
                    diaChiEditText.setText(Objects.requireNonNullElse(diaChi, ""));

                }
            }
        });
    }
    private void saveInfo(Map<String, Object> user){
        FirebaseUser currUser = mAuth.getCurrentUser();
        String email = currUser.getEmail();
        db.collection("users").document(email).collection("info").document("info").update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EditThongTinDoctorActivity.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}