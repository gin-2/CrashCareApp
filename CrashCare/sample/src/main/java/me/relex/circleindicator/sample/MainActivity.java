package me.relex.circleindicator.sample;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AppPermission";
    private final int MY_PERMISSION_REQUEST_STORAGE = 100;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab_content1, fab_content2, fab_content3;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private SmsManager sms;
    private static final String NUM_112 = "112";
    private static final String NUM_119 = "119";
    private static final String NUM_my = "010-7666-0495";
    private String TEXT = "교통사고 신고 접수, 현재 위치: ";

    //sms
    private final static String ACTION_SENT = "ACTION_MESSAGE_SENT";
    private final static String ACTION_DELIVERY = "ACTION_MESSAGE_DELIVERY";

    //location
    private LocationManager locationManager;
    private double lat;
    private double lng;
    private String myLocation;

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
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        myLocation = getGPS();
    }

    public void onFabClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1: //112 sms 전송
                sms = SmsManager.getDefault();

                //             Toast.makeText(MainActivity.this, "송신 대기...", Toast.LENGTH_LONG);
                //             Toast.makeText(MainActivity.this, "상대방 수신 대기...", Toast.LENGTH_LONG);

                confirm_sendSms(NUM_112, TEXT + myLocation);

                Log.d("Raj", "Fab 1");
                break;
            case R.id.fab2:
                sms = SmsManager.getDefault();

                //            Toast.makeText(MainActivity.this, "송신 대기...", Toast.LENGTH_LONG);
                //            Toast.makeText(MainActivity.this, "상대방 수신 대기...", Toast.LENGTH_LONG);

                confirm_sendSms(NUM_119, TEXT + myLocation);
                Log.d("GPS", TEXT + myLocation);
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

    public void animateFAB() {

        if (isFabOpen) {

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
            Log.d("Raj", "open");

        }
    }

    public void onImgBtnClicked(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.acdMenualBtn:
                intent = new Intent(this, SampleActivity.class);
                break;
        }
        if (intent != null)
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
            if (getResultCode() == Activity.RESULT_OK) {
                Toast.makeText(MainActivity.this, "메세지 송신 성공!", Toast.LENGTH_LONG);
            } else {
                Toast.makeText(MainActivity.this, "메세지 송신 실패!", Toast.LENGTH_LONG);

            }
        }
    };

    //수신 확인
    BroadcastReceiver mDeliveryBR = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (getResultCode() == Activity.RESULT_OK) {
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
        Log.i(TAG, "CheckPermission : " + ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
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

    //매개변수 final인 이유: 익명함수 안에 변수들은 final로 선언되어야 접근가능.
    public void confirm_sendSms(final String num, final String text) {
        //메세지 전송 재확인
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
        alert_confirm.setMessage(num + "로 신고 문자를 전송합니다").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'YES'
                        //추후에 NUM_my -> num으로 변경
                        PendingIntent SentIntent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(ACTION_SENT), 0);
                        PendingIntent DeliveryIntent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(ACTION_DELIVERY), 0);
                        sms.sendTextMessage(NUM_my, null, text, SentIntent, DeliveryIntent);
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'No'
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    public String getGPS() {
        String myLocation = "";

        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.d("GPS", "isGPSEnabled=" + isGPSEnabled);
        Log.d("GPS", "isNetworkEnabled=" + isNetworkEnabled);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("GPS", "onStatusChanged");
            }

            public void onProviderEnabled(String provider) {
                Log.d("GPS", "onProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
                Log.d("GPS", "onProviderDisabled");
            }
        };

        // Location 권한을 얻어온다.
        PermissionRequest.Builder requester =
                new PermissionRequest.Builder(this);
        Log.d("GPS", "requester");

        int result = requester
                .create()
                .request(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        20000,
                        new PermissionRequest.OnClickDenyButtonListener() {
                            @Override
                            public void onClick(Activity activity) {

                            }
                        });
        Log.d("GPS", "result:" + result);


        // 사용자가 권한을 수락한 경우
        if (result == PermissionRequest.ALREADY_GRANTED
                || result == PermissionRequest.REQUEST_PERMISSION
               ) {
            Log.d("GPS", "permission 수락");
            // Register the listener with the Location Manager to receive location updates
            try {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, locationListener);
                Log.d("GPS", "requestLocatonUpdates 성공");

                // 수동으로 위치 구하기
                String locationProvider = LocationManager.GPS_PROVIDER;
                Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
                if (lastKnownLocation != null) {
                    lng = lastKnownLocation.getLongitude();
                    lat = lastKnownLocation.getLatitude();
                    Log.d("GPS", "longtitude=" + lng + ", latitude=" + lat);
                }

                //좌표 -> 주소
                Geocoder geocoder = new Geocoder(this);

                List<Address> addr = null;
                try {
                    addr = geocoder.getFromLocation(lat, lng, 2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addr == null) {
                    Log.d("GPS", "No Address");
                } else {
                    String adminArea = addr.get(0).getAdminArea(); //시
                    String locality = addr.get(0).getLocality(); //구 메인
                    String subLocality = addr.get(0).getSubLocality(); //구 서브
                    String throughfare = addr.get(0).getThoroughfare(); //동
                    String subThroughfare = addr.get(0).getSubThoroughfare(); //번지
                    String addrLine = addr.get(0).getAddressLine(0).toString(); // 국가명 시 군 구 동 번지

                    myLocation = addrLine;
                    Log.d("GPS", myLocation);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
                Log.d("GPS", "requestLocatonUpdates 실패" );
            }
        }
        return myLocation + "(" + lat + ", " + lng + ")";
    }
}
