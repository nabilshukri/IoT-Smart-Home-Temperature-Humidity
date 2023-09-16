package com.example.dht11esp8266firebasejava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth auth;
    private String Email;
    private String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        Email="";
        Password="";

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        //getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        if (intent != null) {

            String email = intent.getStringExtra("email");
            String password = intent.getStringExtra("password");

            // Use the email and password as needed
            // For example, pre-fill the email and password fields in the login form
            EditText edtEmail = findViewById(R.id.email);
            EditText edtPassword = findViewById(R.id.password);
            edtEmail.setText(email);
            //edtPassword.setText(password);
        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null)
        {
            Toast.makeText(Login.this,"You are logged in", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Login.this,MainActivity.class);
            startActivity(i);
        }
    }

    public void Register(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void OpenHomePage(View view) {

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        Email = email.getText().toString();
        Password = password.getText().toString();

        System.out.println(Email);
        if (Email.isEmpty() || Password.equals(null)) {
            Toast.makeText(Login.this, "Please Enter Your Credentials", Toast.LENGTH_LONG).show();
        } else {
            auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        System.out.println("hey");
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                    } else {
                        Intent i = new Intent(Login.this, MainActivity.class);
                        startActivity(i);
                    }
                }
            });

        }
    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void forgotPassword(View view) {
        EditText edtEmail = findViewById(R.id.email);
        String email = edtEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(Login.this, "Enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}