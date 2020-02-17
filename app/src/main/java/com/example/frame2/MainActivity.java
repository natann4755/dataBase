package com.example.frame2;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMovieFragmentClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ViewPager tabletframeLayout = null;
    private mooveiFragment mymooveiFragment ;

     static final String keyMoovey = "566b08e0f0f5d9d9ba6089a67537433c";
    private List<Result> mylist;
    List <Result> mooveiFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadMoveis();

        tabletframeLayout = findViewById(R.id.AM_F2_ViewPager);

    }

    private List<Fragment> fragmentList() {
        List<Fragment> myFragment = new ArrayList<Fragment>();
        for (int i = 0; i < mylist.size(); i++) {
            myFragment.add(fragment2.newIntent(mylist.get(i)));
        }
        return myFragment;
    }

    private void loadMoveis(){

       mooveiFromDB = AddDataBase.getInstance(this).mooveiDao().getAll();
        if (mooveiFromDB.size()>0){
            mylist = mooveiFromDB;
            mymooveiFragment = mooveiFragment.newInstant((ArrayList<Result>) mylist);
            getSupportFragmentManager().beginTransaction().add(R.id.AM_FrameLayout, mymooveiFragment).commit();
            Log.d(TAG, "loadMoveis: ");
        }
        Call <ImegeSearchResult> myCall = TMDBRetrofistRest.myMooveiServich.searchMobiesByPepuler(keyMoovey,1);
        myCall.enqueue(new Callback<ImegeSearchResult>() {
            @Override
            public void onResponse(Call<ImegeSearchResult> call, Response<ImegeSearchResult> response) {
                if (response.isSuccessful()){
                    mylist = response.body().getResults();
                    if (mooveiFromDB.size()==0) {
                        mymooveiFragment = mooveiFragment.newInstant((ArrayList<Result>) mylist);
                        getSupportFragmentManager().beginTransaction().add(R.id.AM_FrameLayout, mymooveiFragment).commit();

                    }
                    AddDataBase.getInstance(MainActivity.this).mooveiDao().deleteAll();
                    AddDataBase.getInstance(MainActivity.this).mooveiDao().insertAll((ArrayList<Result>) mylist);
                    Log.d(TAG, "onResponse: ");
                    if (mooveiFromDB.size()>0){
                       mymooveiFragment.SetData(mylist);
                    }
                    }

                    if (tabletframeLayout != null) {
                        simpelPageAdapter mysimpelPageAdapter = new simpelPageAdapter(getSupportFragmentManager(), fragmentList());
                        tabletframeLayout.setAdapter(mysimpelPageAdapter);
                    }
                }



            @Override
            public void onFailure(Call<ImegeSearchResult> call, Throwable t) {
            }
        });
    }

    @Override
    public void OnMooveiClicked(Result moovei , int pozishen) {
        fragment2 f2 = fragment2.newIntent(moovei);
        if (tabletframeLayout == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.AM_FrameLayout, f2).commit();
        } else {
//            mPager.setCurrentItem(2, true);
            tabletframeLayout.setCurrentItem(pozishen);
//                    .layoutManager.scrollToPosition(x)
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .addToBackStack(null)
//                    .replace(R.id.AM_F2_ViewPager,f2).commit();
//        }
        }
    }
}
