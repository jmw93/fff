package com.example.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class InformActivity extends AppCompatActivity {
    Imgdata imgdata;
    Imgdata loop_imgdata;
    Infodata infodata;
    URL originimgurl;
    URL smallimgurl;
    Button button;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    TextView textView_chkbabycarriage;
    TextView textView_chkpet;
    TextView textView_overView;
    TextView textView_infocenter;
    TextView textView_parking;
    int contentid;
    int contenttypeid;
    String ServiceKey ="RjzMYQORqJIq4l9YZkCCmV5mTIec%2BdJYC%2BUzK3c2Aogy4I2Y0tZnRI4292OO56Qqr%2FIMajYNHjo5M8Ayz4R05g%3D%3D";
    ArrayList<Imgdata> imgdatalist = new ArrayList<Imgdata>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);

        textView_chkbabycarriage =findViewById(R.id.textView_chkbabycarriage);
        textView_chkpet=findViewById(R.id.textView_chkpet);
        textView_overView=findViewById(R.id.textView_overView);
        textView_infocenter=findViewById(R.id.textView_infocenter);
        textView_parking=findViewById(R.id.textView_parking);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        Intent intent = getIntent();
        contentid= intent.getExtras().getInt("contentid");
        contenttypeid=intent.getExtras().getInt("contenttypeid");

        imgdata = new Imgdata();
        infodata = new Infodata();
//        IntentData data = (IntentData) intent.getSerializableExtra("targetData");
//        contentid =data.getContentid();
//        contenttypeid=data.getContenttypeid();
//        Log.d("minwoo","contenttypeid: "+data.getContenttypeid());
//        Log.d("minwoo","contenttypeid: "+data.getContentid());

        new Thread(new Runnable() {
            @Override
            public void run() {
                imgdatalist = parsing();
                parsing2();
                parsing3();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(imgdatalist.size()>1) {
                            image1.setImageBitmap(imgdatalist.get(0).getOriginimgurl());
                            image2.setImageBitmap(imgdatalist.get(1).getOriginimgurl());
                            image3.setImageBitmap(imgdatalist.get(2).getOriginimgurl());
                            image4.setImageBitmap(imgdatalist.get(3).getOriginimgurl());

                            initinfo();
                        } else{
                            Toast.makeText(InformActivity.this,"표시할 데이터가 없습니다",Toast.LENGTH_SHORT).show();
                        }
                        Log.d("parsing3",infodata.toString());




                    }
                });
            }
        }).start();

    }
    public void initinfo(){

        textView_chkbabycarriage.setText(infodata.getChkbabycarriage());
        textView_infocenter.setText(infodata.getInfocenter());
        textView_chkpet.setText(infodata.getChkpet());
        textView_overView.setText(infodata.getOverview());
        textView_parking.setText(infodata.getParking());
    }
    public ArrayList<Imgdata> parsing() {

        String urlrequest ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage?ServiceKey=RjzMYQORqJIq4l9YZkCCmV5mTIec%2BdJYC%2BUzK3c2Aogy4I2Y0tZnRI4292OO56Qqr%2FIMajYNHjo5M8Ayz4R05g%3D%3D&contentTypeId="+contenttypeid+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&contentId="+contentid+"&imageYN=Y";
        try {
            URL url = new URL(urlrequest);
            InputStream is = url.openStream();
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = parserFactory.newPullParser();
            xpp.setInput(new InputStreamReader(is,"UTF-8"));

            int eventType = xpp.getEventType();

            //핵심
            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String startTag = xpp.getName();
                        Log.d("minwoo2",startTag);

                        if(startTag.equals("item")){
                            imgdata = new Imgdata();
                            break;
                        }

                        if(startTag.equals("originimgurl")){
                            xpp.next();
                            try{
                                originimgurl = new URL(xpp.getText());
                                URLConnection conn = originimgurl.openConnection();
                                conn.connect();
                                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                                Bitmap bitmap = BitmapFactory.decodeStream(bis);
                                bis.close();
                                imgdata.setOriginimgurl(bitmap);
                            }catch(Exception e){
                                Log.d("minwoo2",e.toString());

                            }

                            break;
                        }
                        if(startTag.equals("smallimageurl")){
                            xpp.next();
                            try{
                                smallimgurl = new URL(xpp.getText());
                                URLConnection conn = smallimgurl.openConnection();
                                conn.connect();
                                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                                Bitmap bitmap = BitmapFactory.decodeStream(bis);
                                bis.close();
                                imgdata.setSmallimageurl(bitmap);
                            }catch(Exception e){
                                Log.d("minwoo2",e.toString());

                            }
                            break;
                        }


                        break;

                    case XmlPullParser.END_TAG:
                        String endTag =xpp.getName();
                        if(endTag.equals("item")){
                            imgdatalist.add(imgdata);

                        }
                        break;

                }//switch문의 끝
                eventType = xpp.next();
            }//for문 끝

        } //try문의 끝
        catch (Exception e){
            Log.e("jmw93",e.toString()+"파싱중오류");
        }

        Log.d("jmw93","파싱성공");
        return imgdatalist;
    }

    /*parsing2파싱 ( 공통정보) */
    public  void parsing2() {

        String urlrequest ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?" +
                "ServiceKey=" + ServiceKey+
                "&contentTypeId="+contenttypeid+"&contentId="+contentid+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=N&areacode=N&catcodeYN=N&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
        try {
            URL url = new URL(urlrequest);
            InputStream is = url.openStream();
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = parserFactory.newPullParser();
            xpp.setInput(new InputStreamReader(is,"UTF-8"));

            int eventType = xpp.getEventType();

            //핵심
            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String startTag = xpp.getName();
                        Log.d("parsing2",startTag);

                        if(startTag.equals("item")){

                            break;
                        }

                        if(startTag.equals("mapx")){
                            xpp.next();
                            infodata.setMapx(xpp.getText());

                            break;
                        }
                        if(startTag.equals("mapy")){
                            xpp.next();
                            infodata.setMapy(xpp.getText());
                            break;
                        }

                        if(startTag.equals("overview")){
                            xpp.next();
                            infodata.setOverview(xpp.getText());
                            break;
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        String endTag =xpp.getName();
                        if(endTag.equals("item")){


                        }
                        break;

                }//switch문의 끝
                eventType = xpp.next();
            }//for문 끝

        } //try문의 끝
        catch (Exception e){
            Log.e("jmw93",e.toString()+"파싱중오류");
        }

        Log.d("jmw93","parsing2성공");

    }

    //parsing3
    public  void parsing3() {

        String urlrequest ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?" +
                "ServiceKey="+ServiceKey +
                "&contentTypeId="+contenttypeid+"&contentId="+contentid+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
        try {
            URL url = new URL(urlrequest);
            InputStream is = url.openStream();
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = parserFactory.newPullParser();
            xpp.setInput(new InputStreamReader(is,"UTF-8"));

            int eventType = xpp.getEventType();

            //핵심
            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String startTag = xpp.getName();
                        Log.d("parsing3",startTag);

                        if(startTag.equals("item")){

                            break;
                        }

                        if(startTag.equals("chkbabycarriage")){
                            xpp.next();
                            infodata.setChkbabycarriage(xpp.getText());

                            break;
                        }
                        if(startTag.equals("chkpet")){
                            xpp.next();
                            infodata.setChkpet(xpp.getText());
                            break;
                        }

                        if(startTag.equals("infocenter")){
                            xpp.next();
                            infodata.setInfocenter(xpp.getText());
                            break;
                        }

                        if(startTag.equals("parking")){
                            xpp.next();
                            infodata.setParking(xpp.getText());
                            break;
                        }
                        if(startTag.equals("restdate")){
                            xpp.next();
                            infodata.setRestdate(xpp.getText());
                            break;
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        String endTag =xpp.getName();
                        if(endTag.equals("item")){


                        }
                        break;

                }//switch문의 끝
                eventType = xpp.next();
            }//for문 끝

        } //try문의 끝
        catch (Exception e){
            Log.e("jmw93",e.toString()+"파싱중오류");
        }

        Log.d("jmw93","파싱성공");

    }
}
