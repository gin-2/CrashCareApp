package me.relex.circleindicator.sample.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

import me.relex.circleindicator.sample.R;

public class SamplePagerAdapter extends PagerAdapter {
    private final Random random = new Random();
    private int mSize;

    public SamplePagerAdapter() {
        mSize = 6;
    }

    public SamplePagerAdapter(int count) {
        mSize = count;
    }

    @Override
    public int getCount() {
        return mSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setBackgroundColor(0xff000000 | random.nextInt(0x00ffffff)); //배경화면 색 랜덤 변경
        imageView.setScaleType(ImageView.ScaleType.FIT_XY); //사진을 화면 꽉차게 보여주기
        TextView textView = new TextView((container.getContext()));
        if(position<=4) {
        switch (position) {
            case 0:
                imageView.setImageResource(R.drawable.car_accident1);
                break;
            case 1:
                imageView.setImageResource(R.drawable.car_accident2);
                break;
            case 2:
                imageView.setImageResource(R.drawable.car_accident3);
                break;
            case 3:
                imageView.setImageResource(R.drawable.car_accident4);
                break;
            case 4:
                imageView.setImageResource(R.drawable.car_accident5);
                break;
        }
            container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return imageView;
        }else{
            textView.setText("HI");
            }
            container.addView(textView);
            return textView;
        }
}

