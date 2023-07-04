package com.example.college;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class FadeInAnimation {

    public static Animation create() {
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(1000);
        return fadeIn;
    }
}

