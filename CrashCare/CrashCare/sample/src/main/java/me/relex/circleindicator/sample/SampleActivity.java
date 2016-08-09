package me.relex.circleindicator.sample;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.sample.Adapter.PagerAdapter;

public class SampleActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sample);

        mViewPager = (ViewPager) findViewById(R.id.view);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        CircleIndicator circleIndicator =(CircleIndicator)findViewById(R.id.indicator1);
        circleIndicator.setViewPager(mViewPager);

        initToolbar();

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        int count = getSupportFragmentManager().getBackStackEntryCount();
                        ActionBar actionbar = getSupportActionBar();
                        if (actionbar != null) {
                            actionbar.setDisplayHomeAsUpEnabled(count > 0);
                            actionbar.setDisplayShowHomeEnabled(count > 0);
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

