package com.example.andre.aced;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.tlaabs.timetableview.TimetableView;

public class WeeklyView extends AppCompatActivity {

    private TimetableView timetableView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_view);

        }
}
