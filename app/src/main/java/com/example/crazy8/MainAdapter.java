package com.example.crazy8;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class MainAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    int[] listImage = {
            R.drawable.ic_photo_library,
            R.drawable.ic_video_library,
            R.drawable.ic_library_music
    };

    String[] listTitle = {"Photo","Video","Music"};

    int[] listColor = {
            Color.rgb(147,112,219),
            Color.rgb(255,127,80),
            Color.rgb(100,149,237)
    };

    public MainAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return listTitle.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.slide_item,container,false);

        LinearLayout linearLayout = view.findViewById(R.id.linear_layout);
        ImageView imageView = view.findViewById(R.id.image_view);
        TextView textView = view.findViewById(R.id.text_view);

        imageView.setImageResource(listImage[position]);
        textView.setText(listTitle[position]);
        linearLayout.setBackgroundColor(listColor[position]);

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
