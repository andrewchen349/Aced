package com.example.andre.aced;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class Tutorial extends AppIntro2 {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Welcome", "Maximize Your Daily Productivity With Calendar, Task Objectives And Course Reminders",
                R.drawable.openingpageview, Color.parseColor("#5AC9DD")));
        addSlide(AppIntroFragment.newInstance("Organize and Plan ", "Plan Out Your Study Sessions, Projects, Meetings and Never Forget Your Classes",
                R.drawable.tutorialview2display, Color.parseColor("#FCBD56")));
        addSlide(AppIntroFragment.newInstance("Stay Reminded", "Set Daily Tasks and Never Forget",
                R.drawable.tutorial3viewdisplay, Color.parseColor("#E2615C")));
        addSlide(AppIntroFragment.newInstance("Be Inspired", "Have A Great Idea? Never Lose A Thought...Jot It Down!",
                R.drawable.tutorial4viewdisplay, Color.parseColor("#00CC99")));

        // OPTIONAL METHODS

        // SHOW or HIDE the statusbar
        showStatusBar(true);

        // Edit the color of the nav bar on Lollipop+ devices
        //setNavBarColor(Color.parseColor("#3F51B5"));

        // Turn vibration on and set intensity
        // NOTE: you will need to ask VIBRATE permission in Manifest if you haven't already
        //setVibrate(true);
        //setVibrateIntensity(30);

        // Animations -- use only one of the below. Using both could cause errors.
        setFadeAnimation(); // OR
        //setZoomAnimation(); // OR
        //setFlowAnimation(); // OR
        //setSlideOverAnimation(); // OR
        //setDepthAnimation(); // OR
        //setCustomTransformer(yourCustomTransformer);

        // Permissions -- takes a permission and slide number
        //askForPermissions(new String[]{Manifest.permission.CAMERA}, 3);
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        finish();
        Intent intent = new Intent(Tutorial.this, Calendar.class);
        Tutorial.this.startActivity(intent);
    }

    @Override
    public void onSlideChanged() {
        // Do something when slide is changed
    }
}
