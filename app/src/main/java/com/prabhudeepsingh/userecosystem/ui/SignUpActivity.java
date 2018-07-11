package com.prabhudeepsingh.userecosystem.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prabhudeepsingh.userecosystem.R;
import com.prabhudeepsingh.userecosystem.model.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    EditText editTextUserName, editTextEmail, editTextPassword, editTextConfirmPassword, editTextContact;
    RadioButton rbMale, rbFemale;
    Button buttonSignUp;
    TextView textViewClickHere;

    User user;

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    CollectionReference userCollection;

    String uid;

    ProgressDialog progressDialog;

    String namePattern = "[a-zA-Z ]+";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    void initViews(){
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextContact = findViewById(R.id.editTextContact);

        rbMale = findViewById(R.id.radioButtonMale);
        rbFemale = findViewById(R.id.radioButtonFemale);

        rbMale.setOnCheckedChangeListener(this);
        rbFemale.setOnCheckedChangeListener(this);

        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(this);

        textViewClickHere = findViewById(R.id.textViewClickHere);

        SpannableString content = new SpannableString("Already have an account? Click Here.");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textViewClickHere.setText(content);


        textViewClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userCollection = firestore.collection("users");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        user = new User();
    }

    void registerUser(){
        progressDialog.show();

        auth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            uid = task.getResult().getUser().getUid();
                            Toast.makeText(SignUpActivity.this, "User Signed Up Successfully! "+uid, Toast.LENGTH_SHORT).show();
                            saveUser();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "Error while signing up. \n Please try again.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                });
    }

    void saveUser(){
        userCollection.document(uid).set(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SignUpActivity.this, "User Saved on Cloud"+uid, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Error while saving user.", Toast.LENGTH_SHORT).show();
            }
        });

        //progressDialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
    }

    @Override
    public void onClick(View view) {
        String cPwd;
        user.name = editTextUserName.getText().toString().trim();
        user.email = editTextEmail.getText().toString().trim();
        user.password = editTextPassword.getText().toString().trim();
        user.contact = editTextContact.getText().toString().trim();
        cPwd = editTextConfirmPassword.getText().toString().trim();

        if(! user.name.matches(namePattern))
            Snackbar.make(view, "Please Enter a valid User Name.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        else if(! user.email.matches(emailPattern))
                Snackbar.make(view, "Please Enter a valid Email.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            else if(! user.password.equals(cPwd))
                    Snackbar.make(view, "Passwords are not identical", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    else if(user.password.length()<6)
                       Snackbar.make(view, "Password should contain 6 digits.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        else if(user.contact.length() != 10)
                                Snackbar.make(view, "Please Enter a valid 10 digit contact.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                else if(user.gender == null)
                                    Snackbar.make(view, "Please Select Gender", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                    else registerUser();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if(id == R.id.radioButtonMale)
            user.gender = "Male";
        else user.gender = "Female";
    }
}
