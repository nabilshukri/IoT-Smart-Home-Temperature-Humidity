package com.example.dht11esp8266firebasejava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    //private EditText email;
    //private EditText password;
    private FirebaseAuth auth;
    //private String Email;
    //private String Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();


        EditText edtUsername = (EditText) findViewById(R.id.username);
        EditText edtEmail = (EditText) findViewById(R.id.email);
        EditText edtPassword = (EditText) findViewById(R.id.password);
        EditText edtRepassword = (EditText) findViewById(R.id.repassword);

        Button signupbtn = (Button) findViewById(R.id.signupbtn);


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, email, password, repassword;
                username = String.valueOf(edtUsername.getText());
                email = String.valueOf(edtEmail.getText());
                password = String.valueOf(edtPassword.getText());
                repassword = String.valueOf(edtRepassword.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Enter email",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(Register.this,"Enter username",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Enter password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(repassword)){
                    Toast.makeText(Register.this,"Enter repassword",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(repassword)) {
                    Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    edtPassword.setText("");
                    edtRepassword.setText("");
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(Register.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Register.this,"Account created.",
                                            Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Register.this, Login.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("password", password);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(intent);
                                    finish();


                                }
                                else {
                                    Toast.makeText(Register.this,"Registration Failed.",
                                            Toast.LENGTH_SHORT).show();
                                    edtUsername.setText("");
                                    edtEmail.setText("");
                                    edtPassword.setText("");
                                    edtRepassword.setText("");

                                }
                            }
                        });
            }
        });

    }
}



