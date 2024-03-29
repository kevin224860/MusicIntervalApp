package com.example.intervalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import com.example.intervalapp.Interval;

public class MainActivity extends AppCompatActivity {
    //range of semitones that an interval can have
    int min = 2;
    int max = 12;

    //assigned the number of semitones to the correct type of interval
    HashMap<Integer, String> intervalType = new HashMap<Integer, String>();

    //list of the 4 options that the interval can be
    ArrayList<String> answers = new ArrayList<String>();

    //buttons to select the answer
    Button button1;
    Button button2;
    Button button3;
    Button button4;

    //correct location among the buttons which has the correct answer
    int correctAnswerLocation;

    int numInterval;

    String[] keyArr = {"c4", "csharp4", "d4", "dsharp4", "e4", "esharp4", "f4", "fsharp4", "g4", "gsharp4", "a4", "asharp4", "b4",
            "c5", "csharp5", "d5", "dsharp5", "e5", "f5", "fsharp5", "g5", "gsharp5", "a5", "asharp5", "b5"};
    String[] intervalArr;

    TextView resultTextView;
    Button nextButton;

    Interval intervalObj;
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


        resultTextView = findViewById(R.id.resultText);
        nextButton = findViewById(R.id.newQuestion);
        //create new question
        question();
    }

    private int determineInterval(){
        int numSemitones = ThreadLocalRandom.current().nextInt(min, max + 1);

        int numStart = 1;
        int diff = numSemitones - numStart;

        return diff;

    }

    private String[] arrInterval(int n){
        // arr will have the notes for the interval
        String[] arr = new String[2];
        // use a random integer to use and the index of keysArr
        int randomInt;
        Random random = new Random();
        randomInt = random.nextInt(keyArr.length);
        // with the keysArr and the random integer make sure that ranInt + n < keysArr.length()
        while(keyArr.length < randomInt+n){
            randomInt = random.nextInt(keyArr.length);
        }


        arr[0] = keyArr[randomInt];

        arr[1] = keyArr[randomInt + n];


        return arr;
    }
    @SuppressLint("ResourceType")
    public void question(){
        numInterval = determineInterval();
        String interval =  intervalType.get(numInterval);
        intervalArr = arrInterval(numInterval);
        int played = ThreadLocalRandom.current().nextInt(0, 3);
        intervalObj = new Interval(intervalArr, played);
        HashSet<Integer> set = new HashSet<>();
        Random random = new Random();
        answers.clear();
        correctAnswerLocation = random.nextInt(4);

        for(int i = 0; i < 4; i++){
            if(i == correctAnswerLocation){
                set.add(numInterval);
                answers.add(interval);
            }else{
                int btnInterval = ThreadLocalRandom.current().nextInt(1, 13);
                while(set.contains(btnInterval) || btnInterval == numInterval){
                    btnInterval = ThreadLocalRandom.current().nextInt(1, 13);
                }
                answers.add(intervalType.get(btnInterval));
                set.add(btnInterval);
            }

        }

        for(int i = 0; i < 4; i++){
            Log.i("answers:", answers.get(i));
        }

        button1.setText(answers.get(0));
        button2.setText(answers.get(1));
        button3.setText(answers.get(2));
        button4.setText(answers.get(3));



    }

    public void makeSound(View view) throws IOException, InterruptedException {


        Log.i("Play sound", intervalArr[0]);
        Log.i("Play sound", intervalArr[1]);

        //get the resource ids for the start and the end notes
        int startNoteID = getResources().getIdentifier(intervalArr[0], "raw", getPackageName());
        int endNoteID = getResources().getIdentifier(intervalArr[1], "raw", getPackageName());

        //create the start and end note Media Player
        MediaPlayer startNote = MediaPlayer.create(MainActivity.this, startNoteID);
        MediaPlayer endNote = MediaPlayer.create(MainActivity.this, endNoteID);

        //play the start and the end notes
        switch (intervalObj.played){
            // play interval
            case 0:
                startNote.start();
                Thread.sleep(1000);
                endNote.start();
                break;
            // play interval in reverse
            case 1:
                endNote.start();
                Thread.sleep(1000);
                startNote.start();
                break;
            // play interval solid
            case 2:
                startNote.start();
                endNote.start();
                break;
            default:
                Log.i("Error:", "intervalObj.play is out of bounds");
        }



    }

    public void chooseAnswer(View view){
        Log.i("Answer is chosen", "_____________________");
        Log.i("Hello", String.valueOf(view.getTag()));
        if(view.getTag().toString().equals(Integer.toString(correctAnswerLocation))){
            Log.i("Answer:", "Correct");
            resultTextView.setVisibility(View.VISIBLE);
            resultTextView.setText("correct");
        }else{
            Log.i("Answer:", "Wrong");
            String interval =  intervalType.get(numInterval);
            resultTextView.setVisibility(View.VISIBLE);
            resultTextView.setText("It was " + interval);
        }
        resultTextView.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);

    }

    public void nextQuestion(View view){
        resultTextView.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.INVISIBLE);
        question();
    }


}