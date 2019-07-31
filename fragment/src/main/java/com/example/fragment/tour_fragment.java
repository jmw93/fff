package com.example.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class tour_fragment extends Fragment {
    private Context mContext;
    private Activity activity;
    MainActivity mainActivity;
    ArrayList<Tour> tourlist;

    Adapter adapter;
    RecyclerView recyclerView;
    XMLPullParserHandler xmlPullParserHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext= context;
        if(context instanceof Activity){
            activity =(Activity)context;
            mainActivity = (MainActivity)getActivity();
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.tourfragment, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        xmlPullParserHandler = new XMLPullParserHandler();

        adapter = new Adapter(new Adapter.OnTourClickListener() {
            @Override
            public void onTourClicked(Tour model) {

//                Toast.makeText(MainActivity.this,model.getName(),Toast.LENGTH_SHORT).show();
                int contentid = model.getContentid();
                int contenttypeid = model.getContenttypeid();
                mainActivity.sendinfodata(contentid,contenttypeid);
                //메인 액티비티에 요청하기
 //               IntentData intentData = new IntentData();
//                intentData.setContentid(contentid);
//                intentData.setContenttypeid(contenttypeid);
//
//                Intent intent=new Intent(getApplicationContext(),InformActivity.class);
//                intent.putExtra("targetData",intentData);
//                //인텐트 전달시 Parcelable 사용할지, 아니면 바로꺼내서 사용할지 정해야함
//                startActivity(intent); // 상세화면 액티비티 전환.
            }
        });
        tourlist = new ArrayList<Tour>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

            new Thread(new Runnable() {
            @Override
            public void run() {
                tourlist = xmlPullParserHandler.parsing();
                Log.d("jmw93", String.valueOf(tourlist.size()));

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setItems(tourlist);

                        recyclerView.setAdapter(adapter);

                    }

                });
            }
        }).start();


        return view;
    }
}
