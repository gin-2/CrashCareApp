package me.relex.circleindicator.sample.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ImageView;

import me.relex.circleindicator.sample.R;
import me.relex.circleindicator.sample.fragment.DefaultFragment;
import me.relex.circleindicator.sample.fragment.DefaultFragment2;
import me.relex.circleindicator.sample.fragment.DefaultFragment3;
import me.relex.circleindicator.sample.fragment.DefaultFragment4;
import me.relex.circleindicator.sample.fragment.DefaultFragment5;
import me.relex.circleindicator.sample.fragment.infoFragment;

/**
 * Created by YoungJung on 2016-08-01.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // 해당하는 page의 Fragment를 생성합니다.
        switch (position){
            case 0:
                return DefaultFragment.create(position);

            case 1:
                return DefaultFragment2.create(position);

            case 2:
                return DefaultFragment3.create(position);

            case 3:
                return DefaultFragment4.create(position);

            case 4:
                return DefaultFragment5.create(position);
            default:
                return infoFragment.create(position);
        }
    }

    @Override
    public int getCount() {
        return 6;  // DefaultFragment를 몇개 생성할것이냐.
    }
}