package com.example.intervalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
 *Class structure
 *
 *
 *onCreate()
 *  created when the app is started
 *
 *question()
 *  creates a question
 *  needs to determine an interval, input it as one of the buttons which would be the answer, have 3 different intervals as incorrect answers
 *
 *chooseAnswer()
 *  user selects one of the buttons
 *  determine if user is correct in guessing the interval
 *  create new question, interval ect
 *
 *playInterval()
 *  method for the button to play the sound of the interval
 * */

public class MainActivity extends AppCompatActivity {
    int min = 2;
    int max = 15;
    int isBlocked = 0;
    HashMap<Integer, String> intervalType = new HashMap<Integer, String>();
    ArrayList<String> answers = new ArrayList<String>();

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    Button interval;

    int locationOfAnswer;
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

        button1 = findViewById(R.id.answer1);
        button2 = findViewById(R.id.answer2);
        button3 = findViewById(R.id.answer3);
        button4 = findViewById(R.id.answer4);

        interval = findViewById(R.id.callSound);
    }

    private String determineInterval(){
        int numSemitones = ThreadLocalRandom.current().nextInt(min, max + 1);

        int numStart = 1;
        int diff = numSemitones - numStart;

        return intervalType.get(diff);


        //Random random = new Random();
        //if 0 play broken else play solid
        //isBlocked = random.nextInt(1);
    }

    public void question(){
        String interval = determineInterval();

        Random random = new Random();

        int correctAnswerLocation = random.nextInt(4);

        for(int i = 0; i < 4; i++){
            if(i == correctAnswerLocation){
                answers.add(interval);
            }else{
                int btnInterval = ThreadLocalRandom.current().nextInt(1, 13);
                while(!answers.add(intervalType.get(btnInterval))){
                    btnInterval = ThreadLocalRandom.current().nextInt(1, 13);
                }
            }
        }

        button1.setText(answers.get(0));
        button2.setText(answers.get(1));
        button3.setText(answers.get(2));
        button4.setText(answers.get(3));



    }
    public void makeSound(View view){
        Log.i("Play sound", "");
    }

    public void chooseAnswer(View view){

        question();
    }

    public void createNewInterval(){
        Random random = new Random();
        locationOfAnswer = random.nextInt(4);
        determineInterval();

    }



}