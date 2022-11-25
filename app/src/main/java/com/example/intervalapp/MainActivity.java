package com.example.intervalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
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
    int max = 12;
    int isBlocked = 0;
    HashMap<Integer, String> intervalType = new HashMap<Integer, String>();
    HashMap<Integer, String> pianoKeys = new HashMap<Integer, String>();
    ArrayList<String> answers = new ArrayList<String>();

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    //Button interval;

    int correctAnswerLocation;

    MediaPlayer noteSound;

    int numInterval;

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

        //all possible keys
        pianoKeys.put(0, "C");
        pianoKeys.put(1, "C#");
        pianoKeys.put(2, "D");
        pianoKeys.put(3, "D#");
        pianoKeys.put(4, "E");
        pianoKeys.put(5, "F");
        pianoKeys.put(6, "F#");
        pianoKeys.put(7, "G");
        pianoKeys.put(8, "G#");
        pianoKeys.put(9, "A");
        pianoKeys.put(10, "A#");
        pianoKeys.put(11, "B");
        pianoKeys.put(12, "high_C");


        button1 = findViewById(R.id.answer1);
        button2 = findViewById(R.id.answer2);
        button3 = findViewById(R.id.answer3);
        button4 = findViewById(R.id.answer4);

        //interval = findViewById(R.id.callSound);
        question();
    }

    private int determineInterval(){
        int numSemitones = ThreadLocalRandom.current().nextInt(min, max + 1);

        int numStart = 1;
        int diff = numSemitones - numStart;

        return diff;

    }

    private String[] arrInterval(int n){
        String[] arr = new String[2];

        arr[0] = "C";

        arr[1] = pianoKeys.get(n);


        return arr;
    }
    @SuppressLint("ResourceType")
    public void question(){
        numInterval = determineInterval();
        String interval =  intervalType.get(numInterval);



        Random random = new Random();
        answers.clear();
        correctAnswerLocation = random.nextInt(4);

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
        String[] intervalArr = arrInterval(numInterval);
        Log.i("Play sound", intervalArr[0]);
        Log.i("Play sound", intervalArr[1]);
        //noteSound.start();
    }

    public void chooseAnswer(View view){
        Log.i("Answer is chosen", "_____________________");
        Log.i("Hello", String.valueOf(view.getTag()));
        if(view.getTag().toString().equals(Integer.toString(correctAnswerLocation))){
            Log.i("Answer:", "Correct");
        }else{
            Log.i("Answer:", "Wrong");
        }
        question();
    }

    public void createNewInterval(){
        Random random = new Random();
        correctAnswerLocation = random.nextInt(4);
        determineInterval();

    }



}