package com.example.intervalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    int min = 2;
    int max = 15;

    HashMap<Integer, String> intervalType = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //number of semi tones for each interval
        intervalType.put(1, "Minor Second");
        intervalType.put(2, "Major Second");
        intervalType.put(3, "Minor Third");
        intervalType.put(4, "Major Third");
        intervalType.put(5, "Perfect Forth");
        intervalType.put(6, "Augmented Forth");
        intervalType.put(7, "Perfect Fifth");
        intervalType.put(8, "Minor Sixth");
        intervalType.put(9, "Major Sixth");
        intervalType.put(10, "Minor Seventh");
        intervalType.put(11, "Major Seventh");
        intervalType.put(12, "Octave");
    }

    private void determineInterval(){
        int numSemitones = ThreadLocalRandom.current().nextInt(min, max + 1);
        //assume begin on middle C
        int numStart = 1;
        int diff = numSemitones - numStart;

        String interval = intervalType.get(diff);
        

    }
    private void makeSound(){

    }
}