package com.example.final_android.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.final_android.R;
import com.example.final_android.Screens.DetailActivity;
import com.example.final_android.ViewPager.SliderItems;
import com.google.android.material.slider.Slider;

import java.util.List;

public class SlidersAdapter extends RecyclerView.Adapter<SlidersAdapter.SliderViewholder> {

    private List<SliderItems> sliderItemsList;
    private ViewPager2 viewPager2;
    private Context context;
    private  Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItemsList.addAll(sliderItemsList);
            notifyDataSetChanged();
        }
    };

    public SlidersAdapter(List<SliderItems> sliderItemsList, ViewPager2 viewPager2) {
        this.sliderItemsList = sliderItemsList;
        this.viewPager2 = viewPager2;
    }



    @NonNull
    @Override
    public SlidersAdapter.SliderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new SliderViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_viewholder,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull SlidersAdapter.SliderViewholder holder, int position) {
        holder.setSliderItems(sliderItemsList.get(position));
        if(position==sliderItemsList.size()-2){
            viewPager2.post(runnable);
        }
        int pos = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("movieID", sliderItemsList.get(pos).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sliderItemsList.size();
    }


    //the holder
    public class SliderViewholder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameTxt,ganereTxt,yearTxt,ratingTxt;
        public SliderViewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageSlide);
            nameTxt=itemView.findViewById(R.id.nameTxt);
            ganereTxt=itemView.findViewById(R.id.genreTxt);
            yearTxt=itemView.findViewById(R.id.yearTxt);
            ratingTxt=itemView.findViewById(R.id.ratingTxt);
        }
        void setSliderItems(SliderItems sliderItems){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions=requestOptions.transform(new CenterCrop(),new RoundedCorners(60));
            Glide.with(context).load(sliderItems.getImage()).apply(requestOptions).into(imageView);

            nameTxt.setText(sliderItems.getName());
            ganereTxt.setText(sliderItems.getGenre().get(0));
            yearTxt.setText(""+sliderItems.getYear());
            ratingTxt.setText(sliderItems.getRating());

        }
    }
}