package com.random.colorgame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            Button fab = (Button)rootView.findViewById(R.id.timeButton);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(),TimeGameActivity.class));
                    getActivity().finish();
                }
            });
            Button fab2 = (Button)rootView.findViewById(R.id.livesButton);
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LivesGameActivity.class));
                }
            });
            TextView highScoreLives = (TextView)rootView.findViewById(R.id.highScoreMainLives);
            highScoreLives.setText("Arcade    : " + getActivity().getSharedPreferences("high_scores", MODE_PRIVATE).getInt("high_score_lives", 0));
            TextView highScoreTime = (TextView)rootView.findViewById(R.id.highScoreMainLives);
            highScoreTime.setText("Time Trial : " + getActivity().getSharedPreferences("high_scores",MODE_PRIVATE).getInt("high_score_time",0));
            return rootView;
        }
    }
}
