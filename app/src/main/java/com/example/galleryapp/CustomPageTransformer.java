package com.example.galleryapp;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

public class CustomPageTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        if (position < -1) {
            // Page is way off-screen to the left.
            page.setAlpha(0);
        } else if (position <= 1) {
            // Modify the default slide transition to shrink the page as well
            page.setAlpha(1 - Math.abs(position));
            page.setScaleX(1 - Math.abs(position) * 0.3f);
            page.setScaleY(1 - Math.abs(position) * 0.3f);
            page.setTranslationX(-position * page.getWidth());
        } else {
            // Page is way off-screen to the right.
            page.setAlpha(0);
        }
    }
}
