package com.satyajit.Speak_Japanese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        /**
         * Thread is a build in class in java
         * to parallelly work with different
         */
        Thread thread = new Thread(){

            public void run(){
                try {

                    sleep(1000);

                }catch (Exception e){

                    //Mother of all exceptions
                    e.printStackTrace();

                }finally {

                    Intent intent = new Intent(splashScreen.this,MainActivity.class);
                    startActivity(intent);

                }
            }
        };thread.start();
    }
}