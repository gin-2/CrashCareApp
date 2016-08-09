package me.relex.circleindicator.sample.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.sample.Adapter.PagerAdapter;
import me.relex.circleindicator.sample.Adapter.SamplePagerAdapter;
import me.relex.circleindicator.sample.PermissionRequest;
import me.relex.circleindicator.sample.R;
import me.relex.circleindicator.sample.SampleActivity;

public class DefaultFragment extends Fragment{
    private String imagePath;
    final static int SELECT_IMAGE =1;

    public DefaultFragment(){}
    public static DefaultFragment create(int pageNumber) {
        DefaultFragment fragment = new DefaultFragment();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sample_default, container, false);

        ImageButton btn =(ImageButton)rootView.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File picture = savePictureFile();
                if (picture != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));
                    startActivityForResult(intent, 1001);

                } else {
                    Toast.makeText(getActivity(), "카메라 앱을 설치하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    private File savePictureFile() {

        // 외부 저장소 쓰기 권한을 얻어온다.
        PermissionRequest.Builder requester =
                new PermissionRequest.Builder(getActivity());

        int result = requester
                .create()
                .request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        20000,
                        new PermissionRequest.OnClickDenyButtonListener() {
                            @Override
                            public void onClick(Activity activity) {

                            }
                        });

        // 사용자가 권한을 수락한 경우
        if (result == PermissionRequest.ALREADY_GRANTED
                || result == PermissionRequest.REQUEST_PERMISSION) {

            // 사진 파일의 이름을 만든다.
            // Date는 java.util 을 Import 한다.
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());
            String fileName = "IMG_" + timestamp;

            /**
             * 사진파일이 저장될 장소를 구한다.
             * 외장메모리에서 사진을 저장하는 폴더를 찾아서
             * 그곳에 test 라는 폴더를 만든다.
             */
            File pictureStorage = new File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES), "test용/");

            // 만약 장소가 존재하지 않는다면 폴더를 새롭게 만든다.
            if (!pictureStorage.exists()) {

                /**
                 * mkdir은 폴더를 하나만 만들고,
                 * mkdirs는 경로상에 존재하는 모든 폴더를 만들어준다.
                 */
                pictureStorage.mkdirs();
            }

            try {
                File file = File.createTempFile(fileName, ".jpg", pictureStorage);

                // ImageView에 보여주기위해 사진파일의 절대 경로를 얻어온다.
                imagePath = file.getAbsolutePath();


                // 찍힌 사진을 "갤러리" 앱에 추가한다.
                Intent mediaScanIntent =
                        new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE );
                File f = new File( imagePath );
                Uri contentUri = Uri.fromFile( f );
                mediaScanIntent.setData( contentUri );
                getActivity().sendBroadcast(mediaScanIntent);

                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 사용자가 권한을 거부한 경우
        else {
        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        Bitmap bitmap = null;
        try{
            if(resultCode == Activity.RESULT_OK && requestCode == SELECT_IMAGE){
                Uri image = intent.getData();
                BitmapFactory.Options factory = new BitmapFactory.Options();
                factory.inJustDecodeBounds = false;
                factory.inPurgeable = true;
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),image);
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }
            }}catch(IOException e){
            e.printStackTrace();
        }
//        ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageView);
//        imageView.setImageBitmap(bitmap);
    }
}
