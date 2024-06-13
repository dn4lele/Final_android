package com.example.final_android.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.final_android.R;
import com.example.final_android.Screens.DetailActivity;
import com.example.final_android.ViewPager.SliderItems;

import java.util.ArrayList;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.Viewholder> {
    ArrayList<SliderItems> items;
    Context context;

    public FilmListAdapter(ArrayList<SliderItems> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public FilmListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_viewholder , parent,false);
        return  new Viewholder(inflate);
        
    }

    @Override
    public void onBindViewHolder(@NonNull FilmListAdapter.Viewholder holder, int position) {
        holder.nametxt.setText(items.get(position).getName());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(context).load(items.get(position).getImage()).apply(requestOptions).into(holder.pic);

        int pos = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("movieID", items.get(pos).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView nametxt;
        ImageView pic;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            nametxt= itemView.findViewById(R.id.textView);
            pic=itemView.findViewById(R.id.pic);

        }
    }
}
