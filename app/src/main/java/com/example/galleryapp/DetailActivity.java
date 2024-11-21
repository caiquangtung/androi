package com.example.galleryapp;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ArrayList<String> imagePaths = getIntent().getStringArrayListExtra("imagePaths");
        int position = getIntent().getIntExtra("position", 0);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new DetailAdapter(this, imagePaths));
        viewPager.setCurrentItem(position);

        // Áp dụng hiệu ứng chuyển động (tùy chỉnh nếu cần)
        viewPager.setPageTransformer(new CustomPageTransformer());

        // Cấu hình GestureDetector để nhận diện cử chỉ
        gestureDetector = new GestureDetector(this, new GestureListener());
        viewPager.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return false;
        });
    }

    // Lớp để xử lý cử chỉ
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100; // Khoảng cách vuốt tối thiểu
        private static final int SWIPE_VELOCITY_THRESHOLD = 100; // Tốc độ vuốt tối thiểu

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e2.getPointerCount() == 3) { // 3 ngón tay
                float deltaX = e2.getX() - e1.getX();
                if (Math.abs(deltaX) > SWIPE_THRESHOLD && Math.abs(distanceX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (deltaX > 0) {
                        moveToPreviousImage();
                    } else {
                        moveToNextImage();
                    }
                    return true;
                }
            }
            return false;
        }
    }

    private void moveToNextImage() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem < viewPager.getAdapter().getItemCount() - 1) {
            viewPager.setCurrentItem(currentItem + 1);
        }
    }

    private void moveToPreviousImage() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem > 0) {
            viewPager.setCurrentItem(currentItem - 1);
        }
    }
}
