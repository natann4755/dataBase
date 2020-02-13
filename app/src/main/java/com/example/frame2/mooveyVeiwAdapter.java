package com.example.frame2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class mooveyVeiwAdapter extends RecyclerView.Adapter <mooveyVeiwAdapter.VeiwHolder> {


    private OnMooveiClickLisener MyonMooveiClickLisener;
    private LayoutInflater inflater;
    private ArrayList<Result> mydata;

    @NonNull
    @Override
    public VeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.itemmoovi,parent,false);
        return new VeiwHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VeiwHolder holder, int position) {
        holder.onbindSet(mydata.get(position));


    }

    @Override
    public int getItemCount() {
        return mydata.size();
    }



    public mooveyVeiwAdapter (Context context, OnMooveiClickLisener lisiner,  ArrayList<Result> data){
        MyonMooveiClickLisener = lisiner;
        mydata=data;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public class VeiwHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final String pathURL ="https://image.tmdb.org/t/p/w500_and_h282_face/";
        public ImageView imeg;
        public TextView titel;
        public TextView text;
        public ProgressBar myProgressBar;

        public VeiwHolder(@NonNull View view) {
            super(view);
            view.setOnClickListener(this);


            imeg = view.findViewById(R.id.ImageView);
            titel = view.findViewById(R.id.TextViewTitel);
            text = view.findViewById(R.id.TextViewText);
            myProgressBar = view.findViewById(R.id.item_progres);
        }

        public void onbindSet (Result moovei){
//            imeg.setImageResource(moovei.getImeg());
            titel.setText(moovei.getTitle());
            text.setText(moovei.getOverview());
            String imejURL = pathURL+moovei.getPosterPath();
            Picasso
                    .get()
                    .load(imejURL)
                    .into(imeg);
        }


        @Override
        public void onClick(View v) {
            if (MyonMooveiClickLisener == null) return;
            MyonMooveiClickLisener.OnMooveiClicedb(getAdapterPosition());
        }
    }
}
