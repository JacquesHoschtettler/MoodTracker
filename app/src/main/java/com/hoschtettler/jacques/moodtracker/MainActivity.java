package com.hoschtettler.jacques.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String mBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mBackground = "cornflower_blue_65";
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_main);
    }
}
