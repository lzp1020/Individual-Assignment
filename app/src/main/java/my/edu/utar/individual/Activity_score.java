package my.edu.utar.individual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Activity_score extends AppCompatActivity {

    private my.edu.utar.individual.MyDatabaseHelper MyDatabaseHelper;

    private TextView tv_score;
    private int YourScore;
    private String YourName;
//    private ArrayList<Score> scoreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        tv_score = findViewById(R.id.textView);
        tv_score.setTextSize(24);
        //Load Scores
        SharedPreferences preferences = getSharedPreferences("PREPS",0);
        YourScore = preferences.getInt("YourScore",0);
        YourName = preferences.getString("YourName","");

        //Add current score to database
        addScoreToDatabase(YourName, YourScore);

        //Retrieve scores from database and display
        getScoresFromDatabase();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void addScoreToDatabase(String name, int score){
        MyDatabaseHelper = new my.edu.utar.individual.MyDatabaseHelper(this);
        MyDatabaseHelper.openToWrite();
        MyDatabaseHelper.deleteAll();
        MyDatabaseHelper.insert(YourName,YourScore);
        MyDatabaseHelper.close();
    }

    public void getScoresFromDatabase(){
        MyDatabaseHelper = new my.edu.utar.individual.MyDatabaseHelper(this);
        MyDatabaseHelper.openToRead();
        String contentRead = MyDatabaseHelper.queueAll();
        tv_score.setText(contentRead);
        MyDatabaseHelper.close();
    }



}


