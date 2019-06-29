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


public class MainActivity extends AppCompatActivity {

    //Fields and Constants
    private EditText email;
    private EditText password;
    private Button register;
    private TextView toggleVisible;
    private  TextView login;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private ImageView fb;
    private ImageView ig;
    private ImageView gitH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find Corresponding XML Components
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button)findViewById(R.id.register);
        toggleVisible = (TextView)findViewById(R.id.visible);
        login = (TextView)findViewById(R.id.login);
        fb = (ImageView)findViewById(R.id.facebook);
        ig = (ImageView)findViewById(R.id.instagram);
        gitH = (ImageView)findViewById(R.id.github);



        firebaseAuth = FirebaseAuth.getInstance();  //gets Firebase instance
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    Intent intent = new Intent(MainActivity.this, Calendar.class);
                    MainActivity.this.startActivity(intent);
                }
            }
        };

        //Toggle Show Password
        toggleVisible.setVisibility(View.GONE);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(password.getText().length() > 0){
                    toggleVisible.setVisibility(View.VISIBLE);
                }
                else{
                    toggleVisible.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        toggleVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleVisible.getText() == "Show") {
                    toggleVisible.setText("Hide");
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());
                }

                else{
                    toggleVisible.setText("Show");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());
                    password.setSelection(password.length());
                }
                }});


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                MainActivity.this.startActivity(intent);
            }
        });


        //Social Media Icons
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/andrew.chen.902"));
               MainActivity.this.startActivity(browserIntent);
            }
        });
        gitH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browsergitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/andrewchen349/Aced"));
                MainActivity.this.startActivity(browsergitIntent);
            }
        });
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserigIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/realandrewchen/?hl=en"));
                MainActivity.this.startActivity(browserigIntent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    //Method for User to Create Account FireBase Auth
    private void createAccount(){

        String emailUser = email.getText().toString();
        String passwordUser = password.getText().toString();

        if(TextUtils.isEmpty(emailUser) || TextUtils.isEmpty(passwordUser)){
            Toast.makeText(MainActivity.this, "Please Enter in Valid email and password", Toast.LENGTH_LONG).show();
        }

        else {
            firebaseAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, Calendar.class);
                        MainActivity.this.startActivity(intent);
                    } else if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Registration Unsuccessful", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}



