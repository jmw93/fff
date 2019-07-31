package com.example.fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements onInputListener,onwebListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout button0 = (LinearLayout)findViewById(R.id.button0);
        LinearLayout button1 = (LinearLayout)findViewById(R.id.button1);
        LinearLayout button2 = (LinearLayout)findViewById(R.id.button2);
        LinearLayout button3 = (LinearLayout)findViewById(R.id.button3);
        LinearLayout button4 = (LinearLayout)findViewById(R.id.button4);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recyclerView.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new MainFragment()).commit();

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recyclerView.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new lang_fragment()).commit();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recyclerView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new tour_fragment()).commit();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recyclerView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new MyFragment3()).commit();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recyclerView.setVisibility(View.GONE);
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intent);

            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new MainFragment()).commit();
    }

    @Override
    public void sendInput(String emailAddr , String emailPassword, String recipient) {
//        Log.d("jmw93",emailAddr);
//        Intent intent= new Intent(this,MapsActivity.class);
//        intent.putExtra("emailAddr",emailAddr);
//        intent.putExtra("emailPassword",emailPassword);
//        intent.putExtra("recipient",recipient);
//        startActivity(intent);

    }

    @Override
    public void sendwebView(String URL) {
        Intent intent = new Intent(this, webViewActivity.class);
        intent.putExtra("URL", URL);
        startActivity(intent);
    } // 메인화면에서 클릭시 웹 액티비티 띄워주는것

    public void sendinfodata(int contentid,int contenttypeid){
        Intent intent = new Intent(getApplicationContext(), InformActivity.class);
        intent.putExtra("contentid",contentid);
        intent.putExtra("contenttypeid",contenttypeid);
        startActivity(intent);
    }
//   public void sendwebView(String Url){
//        Intent intent = new Intent(this,webViewActivity.class);
//        intent.putExtra("URL",Url);
//        startActivity(intent);
//    }

}
