package com.example.intervalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.jfugue.player.Player;
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

    String[] keyArr = {"c4", "csharp4", "d4", "dsharp4", "e4", "esharp4", "f4", "fsharp4", "g4", "gsharp4", "a4", "asharp4", "b4",
            "c5", "csharp5", "d5", "dsharp5", "e5", "esharp5", "f5", "fsharp5", "g5", "gsharp5", "a5", "asharp5", "b5"};
    String[] intervalArr;

    TextView resultTextView;
    Button nextButton;

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


        resultTextView = findViewById(R.id.resultText);
        nextButton = findViewById(R.id.newQuestion);
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

        intervalArr = arrInterval(numInterval);

    }

    public void makeSound(View view){


        Log.i("Play sound", intervalArr[0]);
        Log.i("Play sound", intervalArr[1]);

        //get the resource ids for the start and the end notes
        int startNoteID = getResources().getIdentifier(intervalArr[0], "raw", getPackageName());
        int endNoteID = getResources().getIdentifier(intervalArr[1], "raw", getPackageName());

        //create the start and end note Media Player
        MediaPlayer startNote = MediaPlayer.create(MainActivity.this, startNoteID);
        MediaPlayer endNote = MediaPlayer.create(MainActivity.this, endNoteID);

        //play the start and the end notes
        startNote.start();
        endNote.start();

        //noteSound.start();
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
        nextButton.setVisibility(View.VISIBLE);

    }

    public void nextQuestion(View view){
        resultTextView.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.INVISIBLE);
        question();
    }
    public void createNewInterval(){
        Random random = new Random();
        correctAnswerLocation = random.nextInt(4);
        determineInterval();

    }



}