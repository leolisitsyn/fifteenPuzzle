package com.foxliss.fifteen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class GridField extends OnSwipeTouchListener implements AdapterView.OnItemClickListener {

    final GridView grid;
    final Field field;
    final int size;
    final int screenHeight;
    final int screenWidth;

    final Context c;
    final AppState appState;
    final boolean audio;
    final MediaPlayer winSound;
    final MediaPlayer swipeSound;
    ArrayList<Integer> neighbours;
    int position;
    private InterstitialAd mInterstitialAd;


    @SuppressLint("ClickableViewAccessibility")
    GridField(Context c, Field field, int screenHeight, int screenWidth) {
        this.field = field;
        size = field.getSize();
        TextViewAdapter adapter = new TextViewAdapter(c, field, screenHeight, screenWidth);
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.c = c;
        appState = new AppState(c);

        grid = new GridView(c);
        grid.setNumColumns(field.getSize());
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(this);
        grid.setOnTouchListener(this);

        audio = appState.getAudio();
        winSound = MediaPlayer.create(c, R.raw.win);
        swipeSound = MediaPlayer.create(c, R.raw.swipe);


        // Interstitial Ad
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(c, "ca-app-pub-9434973627187830/1103981266", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                mInterstitialAd = null;
            }
        });

    }

    public GridView getGrid() {
        return grid;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
        findNeighbours();
        switchButtonsOnClick();
    }


    private void findNeighbours() {
        neighbours = new ArrayList<>();
        if (position % size > 0) neighbours.add(position - 1);
        if (position % size < size - 1) neighbours.add(position + 1);
        if (position / size > 0) neighbours.add(position - size);
        if (position / size < size - 1) neighbours.add(position + size);
    }

    public void switchButtonsOnClick() {

        int idx;
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < neighbours.size(); i++) {
            int neighbour = neighbours.get(i);
            if (field.field[neighbour] == 0) {
                arr.add(neighbour);
            }
        }
        if (!arr.isEmpty()) {
            if (arr.size() == 1) idx = arr.get(0);
            else {
                Random random = new Random();
                idx = arr.get(random.nextInt(2));
            }

            AnimationParameters animationParameters =
                    new AnimationParameters(size, relativePosition(idx, position), screenWidth);
            grid.getChildAt(position).startAnimation(animationParameters.makeAnimation());

            AnimationParameters animationParameters2 =
                    new AnimationParameters(size, relativePosition(position, idx), screenWidth);
            grid.getChildAt(idx).startAnimation(animationParameters2.makeAnimation());

            field.changeField(position, idx);
            grid.setAdapter(new TextViewAdapter(c, field, screenHeight, screenWidth));
            appState.saveField(field.getField());
            addStep();

            win();

            if (audio) {
                if (swipeSound.isPlaying()) swipeSound.seekTo(0);
                swipeSound.start();
            }
        }
    }


    @Override
    public boolean onSwipeRight() {
        move(6);
        return super.onSwipeRight();
    }

    @Override
    public boolean onSwipeLeft() {
        move(4);
        return super.onSwipeLeft();
    }

    @Override
    public boolean onSwipeTop() {
        move(2);
        return super.onSwipeTop();
    }

    @Override
    public boolean onSwipeBottom() {
        move(8);
        return super.onSwipeBottom();
    }

    void move(int direction) {
        int idx;
        int[] dirs;
        boolean acceptable;

        position = getPosition();

        switch (direction) {
            case 2: {
                idx = position - size;
                acceptable = position / size - 1 >= 0 && field.getField()[idx] == 0;
                dirs = new int[]{2, 8};
                break;
            }
            case 4: {
                idx = position - 1;
                acceptable = position % size - 1 < size && field.getField()[idx] == 0;
                dirs = new int[]{4, 6};
                break;
            }
            case 6: {
                idx = position + 1;
                acceptable = position % size + 1 < size && field.getField()[idx] == 0;
                dirs = new int[]{6, 4};
                break;
            }
            case 8: {
                idx = position + size;
                acceptable = position / size + 1 >= 0 && field.getField()[idx] == 0;
                dirs = new int[]{8, 2};
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }

        if (acceptable) {

            AnimationParameters animationParameters2 = new AnimationParameters(size, dirs[1], screenWidth);
            grid.getChildAt(position).startAnimation(animationParameters2.makeAnimation());

            AnimationParameters animationParameters = new AnimationParameters(size, dirs[0], screenWidth);
            grid.getChildAt(idx).startAnimation(animationParameters.makeAnimation());


            field.changeField(position, idx);
            grid.setAdapter(new TextViewAdapter(c, field, screenHeight, screenWidth));

            appState.saveField(field.getField());
            addStep();
            if (audio) {
                if (swipeSound.isPlaying()) swipeSound.seekTo(0);
                swipeSound.start();
            }
            win();
        }
    }

    int relativePosition(int position, int idx) {
        if (position - idx == size) return 2;
        if (position - idx == -size) return 8;
        if (position - idx == 1) return 4;
        if (position - idx == -1) return 6;
        else throw new IllegalStateException("Unexpected value: " + idx);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void win() {
        if (Arrays.equals(field.getField(), field.winning)) {
            appState.setFinished(true);
            appState.addWin();

            if (audio) {winSound.start();}

            grid.setOnItemClickListener(null);
            grid.setOnTouchListener(null);
            LinearLayout linearLayout = (LinearLayout) grid.getParent();

            TextView back = (TextView) linearLayout.getChildAt(2);
            back.setText(R.string.taptocontinue);
            back.setOnClickListener(v -> {

                Intent intent = new Intent(c, BestScores.class);
                c.startActivity(intent);

                if (mInterstitialAd != null) {
                    if (((appState.sizeId == 1 || appState.sizeId == 2) && appState.getWinCount() % 2 == 0) ||
                            (appState.sizeId > 2)) {
                        mInterstitialAd.show((Activity) c);
                    }
                }
            });
        }
    }

    void addStep() {
        appState.stepSteps();
        LinearLayout linearLayout = (LinearLayout) grid.getParent();
        TextView nSteps = (TextView) linearLayout.getChildAt(0);
        nSteps.setText(String.format(Locale.ENGLISH, "%d steps made", appState.steps));
    }
}
