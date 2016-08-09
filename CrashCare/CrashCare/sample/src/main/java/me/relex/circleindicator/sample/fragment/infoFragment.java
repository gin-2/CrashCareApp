package me.relex.circleindicator.sample.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import DB.DatabaseHandler;
import DB.User_Info;
import me.relex.circleindicator.sample.Info_Result;
import me.relex.circleindicator.sample.MySQLiteOpenHelper;
import me.relex.circleindicator.sample.R;
/**
 * Created by YoungJung on 2016-08-01.
 */
public class infoFragment extends android.support.v4.app.Fragment {
    private final static String dbName = "user.db";
    private MySQLiteOpenHelper helper;
    int dbVersion = 2;
    public User_Info userInfo;
    int USER_ID;

    private EditText edit_name;
    private EditText edit_tel;
    private EditText edit_carnum;
    private Button btn_ok;
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
        View rootView = inflater.inflate(R.layout.info, container, false);
        return rootView;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        edit_name= (EditText)view.findViewById(R.id.edit_name);
        edit_tel= (EditText)view.findViewById(R.id.edit_tel);
        edit_carnum=(EditText)view.findViewById(R.id.edit_carnum);
        btn_ok =(Button)view.findViewById(R.id.btn_ok);
        helper = new DatabaseHandler(getActivity());

        helper = new MySQLiteOpenHelper(getActivity(),dbName,null,dbVersion);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = edit_name.getText().toString();
                String Tel = edit_tel.getText().toString();
                String Carnum = edit_carnum.getText().toString();


                if("".equals(Name) || "".equals(Tel) || "".equals(Carnum)){
                    Toast.makeText(getContext(),"정확히 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    helper.insert(Name, Tel, Carnum);
                    Intent i = new Intent(getActivity(), Info_Result.class);
//                    i.putExtra("inputCoe",helper.getResult());
                    startActivity(i);
                }
            }
        });
    }
}