package com.example.crazy8;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class IntroActivity extends AppCompatActivity {

    ViewPager viewPager;
    Button btSkip,btNext;
    LinearLayout linearLayout;
    int currentPage;
    TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.view_pager);
        btSkip = findViewById(R.id.bt_skip);
        btNext = findViewById(R.id.bt_next);
        linearLayout = findViewById(R.id.linear_layout);

        MainAdapter adapter = new MainAdapter (this);
        viewPager.setAdapter(adapter);
        adddots(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adddots(position);
                currentPage = position;
                if(currentPage==dots.length-1){
                    btNext.setText("finish");
                    btSkip.setVisibility(View.GONE);
                    btNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(IntroActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else {
                    btNext.setText("next");
                    btSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPage+1);
            }
        });

        btSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                }
        });

    }

    private void adddots(int currentPage) {
        dots = new  TextView[3];
        linearLayout.removeAllViews();

        for(int a=0;a<dots.length;a++){
            dots[a] = new TextView(this);
            dots[a].setText(Html.fromHtml("&#8226"));
            dots[a].setTextSize(40);
            dots[a].setTextColor(getResources().getColor(android.R.color.darker_gray));
            linearLayout.addView(dots[a]);
        }
        if(dots.length>0){
            dots[currentPage].setTextColor(getResources().getColor(android.R.color.white));
        }
    }
}
