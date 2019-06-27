package com.example.andre.aced;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainScreen extends AppCompatActivity {

    private TextView mTextMessage;
    private FirebaseAuth mAuth1;
    private ImageView moreOptions;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    mTextMessage.setText("Calendar");
                    return true;
                case R.id.navigation_checklist:
                    mTextMessage.setText("Checklist");
                    return true;
                case R.id.navigation_notes:
                    mTextMessage.setText("Notes");
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mTextMessage = (TextView) findViewById(R.id.message);
        moreOptions = (ImageView)findViewById(R.id.moreoption);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mAuth1 = FirebaseAuth.getInstance();

        moreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainScreen.this, Option.class);
                MainScreen.this.startActivity(intent1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.moreoptionmenue, menu);
        //return super.OnCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                mAuth1.signOut();
                finish();
                Intent intent = new Intent(MainScreen.this, Login.class);
                MainScreen.this.startActivity(intent);
                break;
        }
        return true;
        //return super.onOptionsItemSelected(item);
    }
}
