package com.example.frame2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class fragment2 extends Fragment {

    private static final String moovei_bandel_string = "moovei string";
    private Result data;
    private Button bbb;
    private final String pathURL ="https://image.tmdb.org/t/p/w500_and_h282_face/";
    private ImageView imageView1;
    private ImageView imageView2;
    private TextView titel;
    private TextView ttext;
    private List<ResultsItem> myResponses;

    private ProgressBar myProgressBar;


    public static fragment2 newIntent (Result data){
        fragment2 f2 = new fragment2();
        Bundle bundle = new Bundle();
        bundle.putParcelable(moovei_bandel_string,data);
        f2.setArguments(bundle);
        return f2;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getArguments().getParcelable(moovei_bandel_string);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vveiw = inflater.inflate(R.layout.farm2, container, false);
        initveiw(vveiw);
        if (data != null){
            setdata(vveiw);
        }
        return vveiw;
    }

    private void initveiw(View vveiw) {
        imageView1 = vveiw.findViewById(R.id.imeg1);
        imageView2 = vveiw.findViewById(R.id.imeg2);
        titel = vveiw.findViewById(R.id.titel);
        ttext = vveiw.findViewById(R.id.textt);
        bbb = vveiw.findViewById(R.id.f2_butun);
        myProgressBar = vveiw.findViewById(R.id.frem2_progres);

    }

    private void setdata(View vveiw) {
//        imageView1.setImageResource(data.getImeg());
//        imageView2.setImageResource(data.getImeg());
        titel.setText(data.getTitle());
        ttext.setText(data.getOverview());

        String ImajURL1 = pathURL + data.getPosterPath();
        String ImajURL2 = pathURL + data.getBackdropPath();

        Picasso.get().load(ImajURL1).into(imageView1);
        Picasso.get().load(ImajURL2).into(imageView2);
        bbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myProgressBar.setVisibility(View.VISIBLE);
                ResultsItem resultFromDB  = AddDataBase.getInstance(getActivity()).trealerDao().getTreailer((data.getId()));
                if (resultFromDB != null){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                            "https://www.youtube.com/watch?v="+resultFromDB.getKey()));
                    startActivity(intent);
                    myProgressBar.setVisibility(View.GONE);
                }else {
                    Call <Response> myCall = TMDBRetrofistRest.myMooveiServich.searchMobiesTrealer(String.valueOf(data.getId()),MainActivity.keyMoovey);
                    myCall.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            myResponses = response.body().getResults();
                            myResponses.get(0).setMooveyId(data.getId());
                            AddDataBase.getInstance(getActivity()).trealerDao().insert(myResponses.get(0));
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                                    "https://www.youtube.com/watch?v="+myResponses.get(0).getKey()));
                            startActivity(intent);
                            myProgressBar.setVisibility(View.GONE);
                        }
                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}
