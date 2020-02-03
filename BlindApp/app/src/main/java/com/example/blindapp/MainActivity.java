package com.example.blindapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    void init(){
        listView=findViewById(R.id.listViewHeroes);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        getHeroes();
        //getMyImg();
    }

    private void getMyImg(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.url)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<Byte>> imgCall=api.getByte();

        imgCall.enqueue(new Callback<List<Byte>>() {
            @Override
            public void onResponse(Call<List<Byte>> call, Response<List<Byte>> response) {
                if(response.isSuccessful()){
                    try{
                        Log.d("ByteImg",""+response.body());
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("ErrorOnResponse",e.getMessage());
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error retrieving response", Toast.LENGTH_SHORT).show();
                    Log.d("Error","Response Failed");
                }
            }

            @Override
            public void onFailure(Call<List<Byte>> call, Throwable t) {
                Log.d("ErrorOnResponse",t.getMessage());
            }
        });
    }

    private void getHeroes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Hero>> callHero = api.getHeroes();

        callHero.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                if (response.isSuccessful()) {
                    List<Hero> heroList = response.body();

                    //Creating an String array for the ListView
                    String[] heroes = new String[heroList.size()];

                    //looping through all the heroes and inserting the names inside the string array
                    for (int i = 0; i < heroList.size(); i++) {
                        heroes[i] = heroList.get(i).getName();
                    }


                    //displaying the string array into listview
                    listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, heroes));
                } else {

                    Toast.makeText(getApplicationContext(), "Response Failure", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
