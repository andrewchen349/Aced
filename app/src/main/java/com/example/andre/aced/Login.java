package com.example.andre.aced;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    //Fields and Constants
    private EditText email;
    private EditText password;
    private Button user_login;
    private TextView user_register;
    private TextView toggle_visible;
    private ImageView fb;
    private ImageView ig;
    private ImageView gitH;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Find Corresponding XML components
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        user_login = (Button) findViewById(R.id.login);
        user_register = (TextView) findViewById(R.id.register);
        toggle_visible = (TextView)findViewById(R.id.visible);
        fb = (ImageView)findViewById(R.id.facebook);
        ig = (ImageView)findViewById(R.id.instagram);
        gitH = (ImageView)findViewById(R.id.github);

        mAuth = FirebaseAuth.getInstance();  //creates Firebase Instance
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(Login.this, Calendar.class);
                    Login.this.startActivity(intent);
                }
            }
        };

        //Toggles Password Viewablitlity
        toggle_visible.setVisibility(View.GONE);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(password.getText().length() > 0){
                    toggle_visible.setVisibility(View.VISIBLE);
                }
                else{
                    toggle_visible.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Show and Hide Password When Typing
        toggle_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle_visible.getText() == "Show") {
                    toggle_visible.setText("Hide");
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());
                }

                else{
                    toggle_visible.setText("Show");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());
                    password.setSelection(password.length());
                }
            }});

        //Logs Users In
        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        //Takes Users to registration page
        user_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                Login.this.startActivity(intent);
            }
        });

        //Social Media Icons Links
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/andrew.chen.902"));
                Login.this.startActivity(browserIntent);
            }
        });
        gitH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browsergitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/andrewchen349/Aced"));
                Login.this.startActivity(browsergitIntent);
            }
        });
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserigIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/realandrewchen/?hl=en"));
                Login.this.startActivity(browserigIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    //Firebase Auth for Login
    private void userLogin() {

        String userPassword = password.getText().toString();
        String userEmail = email.getText().toString();

        if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
            Toast.makeText(Login.this, "Please Enter in Valid email and password", Toast.LENGTH_LONG).show();
        } else {
            mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login.this, Calendar.class);
                        Login.this.startActivity(intent);
                    } else if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "Login unsuccessful", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
}