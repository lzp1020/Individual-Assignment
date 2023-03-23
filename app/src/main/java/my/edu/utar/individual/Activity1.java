package my.edu.utar.individual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class Activity1 extends AppCompatActivity {

    private Button[] tiles;
    private Button startButton;
    private CountDownTimer countDownTimer;
    private int clickedCount = 0;
    private int numTiles = 4;
    private int redTileIndex;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);



        String string =
                this.getIntent().getStringExtra("Indicator");

        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        tiles = new Button[numTiles];
        tiles[0] = findViewById(R.id.button1);
        tiles[1] = findViewById(R.id.button2);
        tiles[2] = findViewById(R.id.button3);
        tiles[3] = findViewById(R.id.button4);
    }

    private void startGame() {

        clickedCount = 0;
        score = 0;

        int[] colors = {Color.RED, Color.WHITE, Color.GREEN};
        Random random = new Random();
        redTileIndex = random.nextInt(numTiles);

        for (int i = 0; i < numTiles; i++) {
            tiles[i].setOnClickListener(null);
        }

        boolean[] allTilesFilled = new boolean[numTiles];
        Arrays.fill(allTilesFilled, false);
        for (int i = 0; i < numTiles; i++) {
            if (i == redTileIndex) {
                tiles[i].setBackgroundColor(colors[0]);
                allTilesFilled[i] = true;
            } else {
                tiles[i].setBackgroundColor(colors[1]);
            }
            final int index = i;
            tiles[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (index == redTileIndex) {
                        view.setBackgroundColor(colors[2]);
                        clickedCount++;
                        score++;
                        TextView scoreView = findViewById(R.id.textView2);
                        scoreView.setText("Score: " + score);
                        if (clickedCount == numTiles) {
                            countDownTimer.cancel();
                            Toast.makeText(Activity1.this, "You win!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Find an unfilled tile for the next red tile
                            int newRedTileIndex = random.nextInt(numTiles);
                            while (allTilesFilled[newRedTileIndex]) {
                                newRedTileIndex = random.nextInt(numTiles);
                            }
                            redTileIndex = newRedTileIndex;
                            allTilesFilled[redTileIndex] = true;
                            tiles[redTileIndex].setBackgroundColor(colors[0]);
                        }
                    }
                }
            });
        }


        countDownTimer = new CountDownTimer(5000, 1000) {
            TextView tv1 = findViewById(R.id.textView1);

            public void onTick(long millisUntilFinished) {
                tv1.setText("Time Left: " + (millisUntilFinished-1000) / 1000 + "seconds");
            }

            public void onFinish() {
                countDownTimer.cancel();
                Toast.makeText(Activity1.this, "Time's up! You lose!", Toast.LENGTH_SHORT).show();
                score = 0;
                for (int i = 0; i < numTiles; i++) {
                    tiles[i].setOnClickListener(null);


                }
            }


        }.start();

        Button nextLevel = findViewById(R.id.next_level);
        nextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity1.this, Activity2.class);
                startActivity(intent);
            }
        });
    }
}