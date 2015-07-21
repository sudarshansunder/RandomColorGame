package com.random.colorgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.Random;


public class TimeGameActivity extends ActionBarActivity {

    static int numItems;
    static int numColumns = 2;
    static int score = 0;
    static int colorOne, colorTwo,colorBack,colorButton;
    static GridViewAdapter adapter;
    static TextView pausedText;
    static GridView gridView;
    static FloatingActionButton floatingActionButton;
    static int colorPos;
    static int answers = 0;
    static int level = 0;
    static CountDownTimerWithPause countDownTime;
    static int isPlay = 1;
    static int isOver = 0;
    static TextView timeText;


    // One - Play
    // Zero - Pause

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        countDownTime.pause();
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Return to main menu?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(isPlay == 1)
                        countDownTime.resume();
                    }
                })
                .show();

    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        if(isOver == 0) {
            countDownTime.pause();
            gridView.setVisibility(View.INVISIBLE);
            pausedText.setVisibility(View.VISIBLE);
            isPlay = 0;
            floatingActionButton.setImageResource(android.R.drawable.ic_media_play);
        }
    }
    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static void randColor() {
        int r, g, b, penis,d;
        Random rnd = new Random();
        r = rnd.nextInt(186);
        g = rnd.nextInt(186);
        b = rnd.nextInt(186);
        r=r+35;
        g=g+35;
        b=b+35;
        colorOne = Color.rgb(r, g, b);
        colorBack = Color.argb(70,r,g,b);
        colorButton = Color.argb(130,r,g,b);
        penis =35-level;
        d=rnd.nextInt(2);
        if(d==0)penis= -penis;
        colorTwo = Color.rgb(r + penis, g + penis, b + penis);
        colorPos = randInt(0, numItems - 1);

    }
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_game, container, false);
            answers = 0;
            level = 1;
            score = 0;
            numColumns = 2;
            isPlay = 1;
            isOver = 0;
            /*RelativeLayout gridRelativeLayout = (RelativeLayout)rootView.findViewById(R.id.gridRelativeLayout);
            int width = gridRelativeLayout.getLayoutParams().height;
            gridRelativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(width,width));*/
            gridView = (GridView) rootView.findViewById(R.id.gridLayout);
            gridView.setNumColumns(2);
            gridView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        return true;
                    }
                    return false;
                }

            });
            gridView.setNumColumns(numColumns);
            numItems = numColumns * numColumns;
            randColor();
            floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fabPause);
            final FloatingActionButton fabRestart = (FloatingActionButton)rootView.findViewById(R.id.fabRestart);
            adapter = new GridViewAdapter(getActivity(), colorOne, colorTwo, numItems, colorPos);
            gridView.setAdapter(adapter);
            fabRestart.setColorNormal(colorButton);
            floatingActionButton.setColorNormal(colorButton);
            rootView.setBackgroundColor(colorBack);
            final TextView scoreText = (TextView) rootView.findViewById(R.id.scoreText);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == colorPos) {
                        answers++;
                        level++;
                        if (answers == (level / 3) + 1) {
                            if (numColumns < 9)
                                numColumns++;
                            answers = 0;
                        }
                        numItems = numColumns * numColumns;
                        gridView.setNumColumns(numColumns);
                        score++;
                        scoreText.setText("Score : " + score);
                        randColor();
                        fabRestart.setColorNormal(colorButton);
                        floatingActionButton.setColorNormal(colorButton);
                        rootView.setBackgroundColor(colorBack);
                        adapter = new GridViewAdapter(getActivity(), colorOne, colorTwo, numItems, colorPos);
                        gridView.setAdapter(adapter);

                    }
                }
            });
            timeText = (TextView) rootView.findViewById(R.id.timeText);
            countDownTime = new CountDownTimerWithPause(60000, 1000, true) {

                public void onTick(long millisUntilFinished) {
                    timeText.setText("Time : " + millisUntilFinished / 1000);
                }

                public void onFinish() {

                    Intent intent = new Intent(getActivity(),GameOver.class);
                    intent.putExtra("score",score);
                    intent.putExtra("mode",0);
                    isOver = 1;
                    startActivity(intent);
                    getActivity().finish();

                }
            }.create();
            pausedText = (TextView)rootView.findViewById(R.id.pausedText);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Animation animFadeOut = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
                    animFadeOut.setDuration(100);
                    final Animation animFadeIn = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
                    animFadeIn.setDuration(100);
                    if (isPlay == 1) {
                        countDownTime.pause();
                        floatingActionButton.setImageResource(android.R.drawable.ic_media_play);
                        isPlay = 0;
                        gridView.clearAnimation();
                        pausedText.clearAnimation();
                        gridView.startAnimation(animFadeOut);
                        pausedText.startAnimation(animFadeIn);
                        gridView.setVisibility(View.INVISIBLE);
                        pausedText.setVisibility(View.VISIBLE);

                    } else if (isPlay == 0) {
                        countDownTime.resume();
                        floatingActionButton.setImageResource(android.R.drawable.ic_media_pause);
                        isPlay = 1;
                        gridView.clearAnimation();
                        pausedText.clearAnimation();
                        gridView.startAnimation(animFadeIn);
                        pausedText.startAnimation(animFadeOut);
                        gridView.setVisibility(View.VISIBLE);
                        pausedText.setVisibility(View.INVISIBLE);
                    }
                }
            });
            fabRestart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countDownTime.pause();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle("Main Menu");
                    alertDialog.setMessage("Are you sure you want to return to the main menu?");
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {

                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                            if(isPlay == 1)
                                countDownTime.resume();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }
            });
            return rootView;
        }
    }
}
