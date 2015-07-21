package com.random.colorgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class GameOver extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game_over);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_over, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_game_over, container, false);
            Intent intent = getActivity().getIntent();
            int score = intent.getIntExtra("score",0);
            TextView scoreView = (TextView)rootView.findViewById(R.id.scoreText);
            scoreView.setText("Your score :  " + score);
            TextView highScoreView = (TextView)rootView.findViewById(R.id.highScore);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("high_scores", MODE_PRIVATE);
            final int mode = intent.getIntExtra("mode",0);
            int high_score;
            if(mode == 0) {

                high_score = sharedPreferences.getInt("high_score_time", 0);
                if (score > high_score) {
                    high_score = score;
                    sharedPreferences.edit().putInt("high_score_time", high_score).apply();
                    highScoreView.setTextColor(Color.RED);
                }
            }
            else {

                high_score = sharedPreferences.getInt("high_score_lives", 0);
                if (score > high_score) {
                    high_score = score;
                    sharedPreferences.edit().putInt("high_score_lives", high_score).apply();
                    highScoreView.setTextColor(Color.RED);
                }

            }
            highScoreView.setText("High score : "+high_score);
            Button playAgainButton = (Button)rootView.findViewById(R.id.playAgainButton);
            playAgainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mode == 0 )
                        startActivity(new Intent(getActivity(),TimeGameActivity.class));
                    else
                        startActivity(new Intent(getActivity(), LivesGameActivity.class));
                    getActivity().finish();
                }
            });
            Button mainMenuButton = (Button)rootView.findViewById(R.id.mainMenu);
            mainMenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(),MainActivity.class));
                    getActivity().finish();
                }
            });
            return rootView;
        }
    }
}
