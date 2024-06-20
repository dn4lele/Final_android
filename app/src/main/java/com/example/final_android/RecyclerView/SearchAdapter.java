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
import com.example.final_android.ViewType;
import com.example.final_android.httpObjects.Root;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    Context context;
    List<SliderItems> items= new ArrayList<>();
    private ViewType viewType;

    public SearchAdapter(List<SliderItems> items, ViewType viewType) {
        this.items = items;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate=null;
        switch (this.viewType){
            case OneColumn:
                inflate = LayoutInflater.from(context).inflate(R.layout.search_item_one, parent ,false  );
                break;
            case TwoColumn:
                inflate = LayoutInflater.from(context).inflate(R.layout.search_item_two, parent ,false  );
                break;
            default:
                inflate = LayoutInflater.from(context).inflate(R.layout.search_item_one, parent ,false  );
                break;
        }
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {

        holder.textView2.setText(items.get(position).getName());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(context).load(items.get(position).getImage()).apply(requestOptions).into(holder.search_image_view);

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView search_image_view;
        TextView textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            search_image_view = itemView.findViewById(R.id.search_image_view);
            textView2 = itemView.findViewById(R.id.textView2);

        }
    }
}
