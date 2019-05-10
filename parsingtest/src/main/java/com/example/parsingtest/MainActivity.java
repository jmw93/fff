package com.example.parsingtest;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String requestUrl = "http://api.visitkorea.or.kr/openapi/service/rest/EngService/areaCode?serviceKey=RjzMYQORqJIq4l9YZkCCmV5mTIec%2BdJYC%2BUzK3c2Aogy4I2Y0tZnRI4292OO56Qqr%2FIMajYNHjo5M8Ayz4R05g%3D%3D&numOfRows=10&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&areaCode=1";


        try {
            URL url = new URL(requestUrl);
            InputStream is = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(is, "UTF-8"));
            int eventType = parser.getEventType();


            //핵심
            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String StartTag = parser.getName();
                        Log.d("parsing", StartTag);
                        break;

                    case XmlPullParser.END_TAG:
                        String endTag = parser.getName();

                        break;

                }//switch문의 끝
                eventType = parser.next();
            }//for문 끝

        } //try문의 끝
        catch (Exception e) {

        }
    }
}



