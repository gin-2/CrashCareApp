package me.relex.circleindicator.sample.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import me.relex.circleindicator.sample.R;

/**
 * Created by YoungJung on 2016-08-02.
 */
public class DefaultFragment3 extends Fragment {

    public DefaultFragment3(){}
    public static DefaultFragment3 create(int pageNumber) {
        DefaultFragment3 fragment = new DefaultFragment3();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sample_default3, container, false);

        ImageButton btn =(ImageButton)rootView.findViewById(R.id.btn3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, 1001);
            }
        });
        return rootView;
    }
}
