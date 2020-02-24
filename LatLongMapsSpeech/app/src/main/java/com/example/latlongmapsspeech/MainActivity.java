package com.example.latlongmapsspeech;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.latlongmapsspeech.databinding.ActivityMainBinding;
import com.example.latlongmapsspeech.model.Country;
import com.example.latlongmapsspeech.print.Print;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final int Speak_Request = 10;
    private Print p;
    private final String tag = "MainActivity";
    private List<ResolveInfo> listOfInformation;
    private ActivityMainBinding binding;
    public static Country country;
    private Hashtable<String, String> countriesAndMsgs;

    void init() {
        p = new Print(MainActivity.this);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        binding.talkBtn.setOnClickListener(this);


    }

    void initHashTables() {
        countriesAndMsgs = new Hashtable<>();
        countriesAndMsgs.put("India", "Welcome to India");
        countriesAndMsgs.put("South Korea", "Welcome to South Korea");
        countriesAndMsgs.put("Singapore", "Welcome to Singapore");
        countriesAndMsgs.put("China", "Welcome to China");
        countriesAndMsgs.put("Malaysia", "Welcome to Malaysia");
        countriesAndMsgs.put("Cambodia", "Welcome to Cambodia");
        countriesAndMsgs.put("Pakistan", "Welcome to Pakistan");
        countriesAndMsgs.put("United States", "Welcome to United States");


        country = new Country(countriesAndMsgs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initHashTables();

        PackageManager packageManager = this.getPackageManager();
        listOfInformation = packageManager.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0
        );


    }

    public void listenToTheUsersVoice() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Talk To Me!");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
        startActivityForResult(intent, Speak_Request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Speak_Request &&
                resultCode == RESULT_OK) {
            ArrayList<String> voiceWords = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
            );

            float[] confidLevels = data.getFloatArrayExtra(
                    RecognizerIntent.EXTRA_CONFIDENCE_SCORES
            );

//            int index=0;
//            for(String userWord:voiceWords){
//                if(confidLevels!=null &&
//                    index<confidLevels.length)
//                {
//                    p.sprintf(userWord+" Confidence = "+confidLevels[index]);
//                }
//            }
            String countryMatchedWithUserWord =
                    country.matchCounryWithMinConfid(voiceWords, confidLevels);
            Intent myMapActivity = new Intent(MainActivity.this,
                    MapsActivity.class);

            myMapActivity.putExtra(Country.countryKey,
                    countryMatchedWithUserWord);
            startActivity(myMapActivity);

        }
    }

    int check() {
        if (listOfInformation.size() > 0) {
            p.sprintf("Speech available on this device");
            Log.d(tag, "Speech Available");
            return 1;
        } else {
            p.fprintf("Speech not available on this device");
            Log.d(tag, "Speech not Available");
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.talkBtn:
                if (check() == 1) {
                    listenToTheUsersVoice();
                }
                break;
        }
    }
}
