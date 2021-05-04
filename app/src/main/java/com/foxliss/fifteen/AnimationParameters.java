package com.foxliss.fifteen;

import android.view.animation.TranslateAnimation;

class AnimationParameters {
    final int size;
    final int direction;
    final int screenWidth;
    final float shift;

    AnimationParameters (int size, int direction, int screenWidth) {
        this.screenWidth = screenWidth;
        this.size = size;
        this.direction = direction;
        shift = (float) screenWidth / size;
    }

    TranslateAnimation makeAnimation () {
        TranslateAnimation anim;
        switch (direction) {
            // UP
            case 2 : {
                anim = new TranslateAnimation(0, 0, 0, shift); break;
            }
            // LEFT
            case 4 : {
                anim = new TranslateAnimation(0, shift, 0, 0); break;
            }
            // RIGHT
            case 6 : {
                anim = new TranslateAnimation(0, -shift, 0, 0); break;
            }
            // DOWN
            case 8 : {
                anim = new TranslateAnimation(0, 0, 0, -shift); break;
            }
            default : anim = new TranslateAnimation(0,0,0,0);
        }
        anim.setDuration(200);
        return anim;
    }
}