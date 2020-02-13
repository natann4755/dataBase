package com.example.frame2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMovieFragmentClickListener {

    private ViewPager tabletframeLayout = null;
    private mooveiFragment mymooveiFragment ;

     static final String keyMoovey = "566b08e0f0f5d9d9ba6089a67537433c";
    private List<Result> mylist;

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

        final List <Result> mooveiFromDB = AddDataBase.getInstans((Callback<ImegeSearchResult>) this).mooveiDao().getAll();
        if (mooveiFromDB != null){
            mylist = mooveiFromDB;
            mymooveiFragment = mooveiFragment.newInstant((ArrayList<Result>) mylist);
            getSupportFragmentManager().beginTransaction().add(R.id.AM_FrameLayout, mymooveiFragment).commit();
        }
        Call <ImegeSearchResult> myCall = TMDBRetrofistRest.myMooveiServich.searchMobiesByPepuler(keyMoovey,1);
        myCall.enqueue(new Callback<ImegeSearchResult>() {
            @Override
            public void onResponse(Call<ImegeSearchResult> call, Response<ImegeSearchResult> response) {
                if (response.isSuccessful()){
                    mylist = response.body().getResults();
                    if (mooveiFromDB == null) {
                        mymooveiFragment = mooveiFragment.newInstant((ArrayList<Result>) mylist);
                        getSupportFragmentManager().beginTransaction().add(R.id.AM_FrameLayout, mymooveiFragment).commit();
//                        mymooveiFragment.SetData(mylist);
                    }
                    AddDataBase.getInstans(this).mooveiDao().deleteAll();
                    AddDataBase.getInstans(this).mooveiDao().insertAll((ArrayList<Result>) mylist);

                    if (mooveiFromDB != null){
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
