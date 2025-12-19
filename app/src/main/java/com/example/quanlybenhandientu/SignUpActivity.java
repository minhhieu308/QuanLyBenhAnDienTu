package com.example.quanlybenhandientu;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private Button signup;
    private TextView loginButton;
    private Spinner spinner;
    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText, hoEditText, tenEditText, phoneEditText;
    String[] luaChon = {"Bệnh nhân", "Bác sĩ"};
    String ho, ten, phone, email, password;
    int selectedItem;
    FirebaseFirestore db;
    private boolean passwordVisible = false;
    private ImageButton showPassword;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        showPassword = findViewById(R.id.imageButton);
        hoEditText = findViewById(R.id.ho_signup);
        tenEditText = findViewById(R.id.ten_signup);
        phoneEditText = findViewById(R.id.phone_signup);
        emailEditText = findViewById(R.id.signup_email);
        passwordEditText = findViewById(R.id.signup_password);
        signup = findViewById(R.id.button_in_signup);
        spinner = findViewById(R.id.spinner);

        loginButton = findViewById(R.id.buttontext_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, Login.class));
                finish();
            }
        });

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePasswordVisibility();
            }
        });

        SpinnerItem spinnerItem = new SpinnerItem(this, luaChon);
        spinner.setAdapter(spinnerItem);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ho = hoEditText.getText().toString();
                ten = tenEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                selectedItem = spinner.getSelectedItemPosition();
                Map<String, Object> user = new HashMap<>();
                user.put("ho", ho);
                user.put("ten", ten);
                user.put("phone", phone);
                user.put("email", email);
                user.put("password", password);
                user.put("doiTuong", selectedItem);
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Email or password is empty",
                            Toast.LENGTH_SHORT).show();
                }else{
                    xacThucDangKy(user);
                }

            }
        });
    }
    private void taoDataBase(String email, int i,Map<String, Object> user){
        db.collection("users").document(email).collection("info").document("info").set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("CHECK", "Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("CHECK", "Failed");
            }
        });

    }
    private void xacThucDangKy(Map<String, Object> user){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("CHECK", "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            taoDataBase(email, selectedItem, user);
                            startActivity(new Intent(SignUpActivity.this, Login.class));
                            Toast.makeText(SignUpActivity.this, "Authentication Succeeded.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("CHECK", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void togglePasswordVisibility() {
        if (passwordVisible) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPassword.setImageResource(R.drawable.baseline_visibility_off_24);
            passwordVisible = false;
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPassword.setImageResource(R.drawable.baseline_visibility_24);

            passwordVisible = true;
        }
        passwordEditText.setSelection(passwordEditText.getText().length());
    }
}
