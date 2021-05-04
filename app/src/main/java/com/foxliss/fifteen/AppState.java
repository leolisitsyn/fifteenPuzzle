package com.foxliss.fifteen;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

class AppState {
    final String SIZE = "Field size";
    final String DIFFICULT = "Difficult";
    final String AUDIO = "SoundOn";
    final String FINISHED = "Finished";
    final String FIELD = "Field numbers";
    final String STEPS = "Steps";
    final String BEST = "Best";
    final String WINCOUNT = "WINCOUNT";
    final int sizeId;
    final int difficulty;
    final int[] bestScores;
    final int start;
    final boolean best;
    final Context context;
    SharedPreferences sPref;
    boolean isFinished;
    String[] fieldNumbers;
    boolean audio;
    int steps;
    int winCount;

    AppState(Context context) {

        this.context = context;

        this.sizeId = loadInt(SIZE);
        this.difficulty = loadInt(DIFFICULT);

        this.isFinished = loadBool(FINISHED);
        this.audio = loadBool(AUDIO);
        this.steps = loadInt(STEPS);
        this.winCount = loadInt(WINCOUNT);
        if (!isFinished) this.fieldNumbers = loadField();

        this.bestScores = loadBest();
        this.start = sizeId * 10 + difficulty * 5;
        best = steps < loadBestScore();
    }

    int[] loadBest() {
        int[] arr = new int[5 * 2 * 5];

        sPref = context.getSharedPreferences(BEST, MODE_PRIVATE);
        for (int i = 0; i < 5 * 2 * 5; i++) {
            arr[i] = sPref.getInt(BEST + i, 1000);
        }
        return arr;
    }

    int loadBestScore() {
        return bestScores[start];
    }

    String[] selectedScores() {
        String[] str = new String[5];
        for (int i = 0; i < 5; i++) {
            if (bestScores[start + i] == 1000) str[i] = "--- steps";
            else str[i] = bestScores[start + i] + " steps";
        }
        return str;
    }

    String[] selectedScores(int sizeId, int diffId) {
        String[] str = new String[5];
        int start = sizeId * 10 + diffId * 5;
        for (int i = 0; i < 5; i++) {
            if (bestScores[start + i] == 1000) str[i] = "--- steps";
            else str[i] = bestScores[start + i] + " steps";
        }
        return str;
    }

    void clearScores(int sizeId, int difficultyId) {
        sPref = context.getSharedPreferences(BEST, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        int start = sizeId * 10 + difficultyId * 5;

        for (int i = start; i < start + 5; i++) {
            bestScores[i] = 1000;
            ed.putInt(BEST + i, 1000);
            ed.apply();
        }
    }

    void saveBest(int steps) {
        int idx = -1;
        int curSteps = steps;

        sPref = context.getSharedPreferences(BEST, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        for (int i = start; i < start + 5; i++) {
            int tmp = bestScores[i];
            if (tmp > curSteps) {
                if (idx == -1) idx = i;
                bestScores[i] = curSteps;
                ed.putInt(BEST + i, curSteps);
                ed.apply();
                curSteps = tmp;
            }
        }
    }

    int loadInt(String PREF) {
        sPref = context.getSharedPreferences(PREF, MODE_PRIVATE);

        if (!PREF.equals(STEPS)) return sPref.getInt(PREF, 1);
        else return sPref.getInt(PREF, 0);
    }

    boolean loadBool(String PREF) {
        sPref = context.getSharedPreferences(PREF, MODE_PRIVATE);
        return sPref.getBoolean(PREF, true);
    }

    String[] loadField() {
        sPref = context.getSharedPreferences(FIELD, MODE_PRIVATE);
        int square = (sizeId + 2) * (sizeId + 2);
        fieldNumbers = new String[square];

        for (int i = 0; i < square; i++) {
            fieldNumbers[i] = sPref.getString(FIELD + i, " ");
        }
        return fieldNumbers;
    }

    void saveInt(String PREF, int intPref) {
        sPref = context.getSharedPreferences(PREF, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(PREF, intPref);
        ed.apply();
    }

    void stepSteps() {
        steps += 1;
        sPref = context.getSharedPreferences(STEPS, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(STEPS, steps);
        ed.apply();
    }

    void zeroSteps() {
        steps = 0;
        saveInt(STEPS, steps);
    }

    void saveBool(String PREF, boolean boolPref) {
        sPref = context.getSharedPreferences(PREF, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(PREF, boolPref);
        ed.apply();
    }

    void saveField(int[] gameField) {
        int square = (sizeId + 2) * (sizeId + 2);
        fieldNumbers = new String[square];
        for (int i = 0; i < square; i++) {
            fieldNumbers[i] = String.valueOf(gameField[i]);
        }
        sPref = context.getSharedPreferences(FIELD, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        for (int i = 0; i < fieldNumbers.length; i++) {
            ed.putString(FIELD + i, fieldNumbers[i]);
            ed.apply();
        }
    }

    void setFinished(boolean finished) {
        isFinished = finished;
        saveBool(FINISHED, isFinished);
    }

    boolean getAudio() {
        return this.audio;
    }

    void setAudio(boolean audio) {
        this.audio = audio;
        saveBool(AUDIO, audio);
    }

    void addWin() {
        winCount += 1;
        sPref = context.getSharedPreferences(WINCOUNT, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(WINCOUNT, winCount);
        ed.apply();
    }

    int getWinCount() {
        return winCount;
    }
}
