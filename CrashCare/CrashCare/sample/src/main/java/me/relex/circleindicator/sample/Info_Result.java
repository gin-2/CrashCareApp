package me.relex.circleindicator.sample;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import DB.DatabaseHandler;

/**
 * Created by YoungJung on 2016-08-08.
 */
public class Info_Result extends Activity {
     private MySQLiteOpenHelper helper;
    private final static String dbName = "user.db";
    int dbVersion = 2;
    TextView txt_name;
    TextView txt_tel;
    TextView txt_carnum;
    private String iCode = null;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.info_result);
//        Intent i = getIntent();
//        iCode = i.getExtras().getString("name");

        txt_name = (TextView) findViewById(R.id.txt_name2);
        txt_tel = (TextView) findViewById(R.id.txt_tel2);
        txt_carnum =(TextView) findViewById(R.id.txt_carnum);


        helper = new DatabaseHandler(this);

        helper = new MySQLiteOpenHelper(this,dbName,null,dbVersion);
        txt_name.setText(helper.getResult());
        txt_tel.setText(helper.getResult1());
        txt_carnum.setText(helper.getResult2());

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
