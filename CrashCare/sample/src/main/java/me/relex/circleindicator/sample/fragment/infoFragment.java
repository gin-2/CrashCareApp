package me.relex.circleindicator.sample.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.sample.R;
import me.relex.circleindicator.sample.Adapter.SamplePagerAdapter;

/**
 * Created by YoungJung on 2016-08-01.
 */
public class infoFragment extends android.support.v4.app.Fragment {
    public static infoFragment create(int pageNumber) {
        infoFragment fragment = new infoFragment();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        return rootView;
    }

//    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        ViewPager viewpager = (ViewPager) view.findViewById(R.id.viewpager_info);
//        viewpager.setAdapter(new SamplePagerAdapter());
//        viewpager.setCurrentItem(0);
//    }
}