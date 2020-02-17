package com.example.frame2;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mooveiFragment extends Fragment implements OnMooveiClickLisener {

    public OnMovieFragmentClickListener myonMovieFragmentClickListener ;

    private RecyclerView myRecyclerView;
    private RecyclerView.LayoutManager mylayoutManager;
    private RecyclerView.Adapter myAdapter;
    static final String key = "key";
    private ArrayList <Result> myresults;
    private  ArrayList <Result> newresults;
    private Button buttonAddMore;
    private Button removeDB;
    private static int counterPage = 1;

    static mooveiFragment newInstant (ArrayList<Result> mylist){
        mooveiFragment myMooveiFragment = new mooveiFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(key,mylist);
        myMooveiFragment.setArguments(bundle);
        return myMooveiFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieFragmentClickListener){
            myonMovieFragmentClickListener = (OnMovieFragmentClickListener) context;
        }else {
            throw new RuntimeException(context.toString() + "must by implements OnMovieFragmentClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        myonMovieFragmentClickListener = null;
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vveiw = inflater.inflate(R.layout.ferm1,container,false);
        myresults = getArguments().getParcelableArrayList(key);
        myRecyclerView = vveiw.findViewById(R.id.FM_rv);

        intimyRecyclerView();

        buttonAddMore = vveiw.findViewById(R.id.frem1_botton);
        buttonAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterPage++;

                addMooveus(counterPage);
            }
        });

        removeDB = vveiw.findViewById(R.id.frem1_botton_delet_db);
        removeDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDataBase.getInstance(getActivity()).mooveiDao().deleteAll();
            }
        });
        return vveiw;
    }

    private void intimyRecyclerView() {
        if (getContext() != null) {
            mylayoutManager = new LinearLayoutManager(getContext());
            myRecyclerView.setLayoutManager(mylayoutManager);
            myAdapter = new mooveyVeiwAdapter(getContext(), this, myresults);
            myRecyclerView.setAdapter(myAdapter);
        }
    }

    @Override
    public void OnMooveiClicedb(int ItemPositiom) {
        if (myonMovieFragmentClickListener != null) {
            myonMovieFragmentClickListener.OnMooveiClicked((myresults.get(ItemPositiom)),ItemPositiom);
        }
    }

    private void addMooveus(int page){

        Call<ImegeSearchResult> myCall = TMDBRetrofistRest.myMooveiServich.searchMobiesByPepuler(MainActivity.keyMoovey,page);
        myCall.enqueue(new Callback<ImegeSearchResult>() {
            @Override
            public void onResponse(Call<ImegeSearchResult> call, Response<ImegeSearchResult> response) {
                if (response.isSuccessful()){
                    newresults = (ArrayList) response.body().getResults();
                    newresults.addAll(myresults);
                    SetData(newresults);
                }
            }



            @Override
            public void onFailure(Call<ImegeSearchResult> call, Throwable t) {
            }
        });
    }
    public void SetData(List<Result> mylist) {
        myresults.clear();
        myresults.addAll(mylist);
        myAdapter.notifyDataSetChanged();
    }

}
