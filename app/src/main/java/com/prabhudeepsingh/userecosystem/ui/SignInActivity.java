package com.prabhudeepsingh.userecosystem.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.prabhudeepsingh.userecosystem.R;
import com.prabhudeepsingh.userecosystem.model.User;

public class SignInActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonSignIn;
    TextView textViewCreateAccount;
    LinearLayout linearLayoutSignIn;

    User user;

    FirebaseAuth auth;

    ProgressDialog progressDialog;

    void initViews(){
        editTextEmail = findViewById(R.id.editTextEmailSignIn);
        editTextPassword = findViewById(R.id.editTextPasswordSignIn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in.");
        progressDialog.setCancelable(false);

        linearLayoutSignIn = findViewById(R.id.linearlayoutSignIn);

        user = new User();

        auth = FirebaseAuth.getInstance();

        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.email = editTextEmail.getText().toString().trim();
                user.password = editTextPassword.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(!user.email.matches(emailPattern))
                    Snackbar.make(linearLayoutSignIn, "Please Enter a valid Email.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                else if(user.password == null || user.password.length()<6)
                    Snackbar.make(linearLayoutSignIn, "Please Enter a valid Password.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    else
                        loginUser();
            }
        });

        textViewCreateAccount = findViewById(R.id.textViewCreateAccount);

        SpannableString content = new SpannableString("New Here? Click to create an Account.");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textViewCreateAccount.setText(content);

        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void loginUser(){

        progressDialog.show();

        auth.signInWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignInActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                            Intent intent =  new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(intent);
                            progressDialog.dismiss();
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignInActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
    }
}
