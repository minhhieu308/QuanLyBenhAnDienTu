package com.example.quanlybenhandientu;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private Button signup, login;
    private TextView forgotthepassword;
    private EditText emailEditText, passwordEditText;
    private ImageButton showPassword;
    String email, password;
    private FirebaseAuth mAuth;
    private boolean passwordVisible = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        showPassword = findViewById(R.id.show_pass);
        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_pass);
        signup = findViewById(R.id.signup_button);
        forgotthepassword = findViewById(R.id.forgot_pass);
        login = findViewById(R.id.login_button);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUpActivity.class));
            }
        });

        forgotthepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgotPasswordActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this, "Email or password is empty",
                            Toast.LENGTH_SHORT).show();
                }else{
                    xacThucDangNhap();
                }

            }
        });
        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePasswordVisibility();
            }
        });
    }
    private void xacThucDangNhap(){
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("CHECK", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Success",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, HomeFragmentsActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("CHECK", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
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
