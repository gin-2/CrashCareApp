package me.relex.circleindicator.sample;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AppPermission";
    private final int MY_PERMISSION_REQUEST_STORAGE = 100;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab_content1, fab_content2, fab_content3;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    //sms
    final static String ACTION_SENT = "ACTION_MESSAGE_SENT";
    final static String ACTION_DELIVERY = "ACTION_MESSAGE_DELIVERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab_content1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab_content2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab_content3 = (FloatingActionButton) findViewById(R.id.fab3);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        checkPermission();
    }

    public void onFabClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1: //112 sms 전송
                SmsManager sms = SmsManager.getDefault();
                String num = "010-7666-0495";
                String text = "교통사고 신고 접수, 현재 위치:";

                Toast.makeText(MainActivity.this, "송신 대기...", Toast.LENGTH_LONG);
                Toast.makeText(MainActivity.this, "상대방 수신 대기...", Toast.LENGTH_LONG);

                PendingIntent SentIntent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(ACTION_SENT), 0);
                PendingIntent DeliveryIntent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(ACTION_DELIVERY), 0);
                sms.sendTextMessage(num, null, text, SentIntent, DeliveryIntent);

                Log.d("Raj", "Fab 1");
                break;
            case R.id.fab2:

                Log.d("Raj", "Fab 2");
                break;
            case R.id.fab3: //보험회사 call
                Log.d("Raj", "Fab 3");
                Uri number = Uri.parse("tel:" + "010-7666-0495");
                Intent intent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(intent);
                break;
        }
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab_content1.startAnimation(fab_close);
            fab_content2.startAnimation(fab_close);
            fab_content3.startAnimation(fab_close);

            fab_content1.setClickable(false);
            fab_content2.setClickable(false);
            fab_content3.setClickable(false);

            isFabOpen = false;
            Log.d("Raj", "close");

        } else {
            fab.startAnimation(rotate_forward);
            fab_content1.startAnimation(fab_open);
            fab_content2.startAnimation(fab_open);
            fab_content3.startAnimation(fab_open);

            fab_content1.setClickable(true);
            fab_content2.setClickable(true);
            fab_content2.setClickable(true);

            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    public void onImgBtnClicked(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.acdMenualBtn:
                intent = new Intent(this, SampleActivity.class);
                break;
        }
        if(intent != null)
            startActivity(intent);
    }

    //sms BR 등록 및 해제
    protected void onResume() {
        super.onResume();
        registerReceiver(mSentBR, new IntentFilter(ACTION_SENT));
        registerReceiver(mDeliveryBR, new IntentFilter(ACTION_DELIVERY));
    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(mSentBR);
        unregisterReceiver(mDeliveryBR);
    }

    //송신 확인
    BroadcastReceiver mSentBR = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(getResultCode() == Activity.RESULT_OK) {
                Toast.makeText(MainActivity.this, "메세지 송신 성공!", Toast.LENGTH_LONG);
            } else {
                Toast.makeText(MainActivity.this, "메세지 송신 실패!", Toast.LENGTH_LONG);

            }
        }
    };

    //수신 확인
    BroadcastReceiver mDeliveryBR = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if(getResultCode() == Activity.RESULT_OK) {
                Toast.makeText(MainActivity.this, "상대방 수신 성공!", Toast.LENGTH_LONG);
            } else {
                Toast.makeText(MainActivity.this, "상대방 수신 실패!", Toast.LENGTH_LONG);

            }
        }
    };

    /**
     * Permission check.
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        Log.i(TAG, "CheckPermission : " + checkSelfPermission(Manifest.permission.SEND_SMS));
        if (checkSelfPermission(Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                // Explain to the user why we need to write the permission.
                Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS},
                    MY_PERMISSION_REQUEST_STORAGE);

            // MY_PERMISSION_REQUEST_STORAGE is an
            // app-defined int constant

        } else {
            Log.e(TAG, "permission deny");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! do the
                    // calendar task you need to do.
                } else {

                    Log.d(TAG, "Permission always deny");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }
}
