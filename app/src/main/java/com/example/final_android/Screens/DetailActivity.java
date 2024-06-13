package com.example.final_android.Screens;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.final_android.R;
import com.example.final_android.RecyclerView.GenreAdapter;
import com.example.final_android.databinding.ActivityDetailBinding;
import com.example.final_android.http.MoviesAPIClient;
import com.example.final_android.http.MoviesService;
import com.example.final_android.httpObjects.DetailMovieInfo;
import com.example.final_android.httpObjects.Root;

import java.util.ArrayList;
import java.util.List;

import eightbitlab.com.blurview.RenderScriptBlur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private int movieID;

    List<String> genres = new ArrayList<>();
    private String MovieName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setVeriables();


        binding.backimg.setOnClickListener(v -> {
            finish();
        });

    }



    private void setVeriables() {
        movieID = getIntent().getIntExtra("movieID", 0);
        getDataFromHttp();

        binding.copytoclipimg.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Movie's Name", MovieName);
            clipboard.setPrimaryClip(clip);
        });

        float radius = 10f;
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        binding.blurView.setupWith(rootView,new RenderScriptBlur(this))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius);

        binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.blurView.setClipToOutline(true);



    }

    private void getDataFromHttp() {
        Retrofit retrofit = MoviesAPIClient.getClient();
        MoviesService service = retrofit.create(MoviesService.class);
        Call<DetailMovieInfo> callAsync = service.getMovieById(String.valueOf(movieID));

        callAsync.enqueue(new Callback<DetailMovieInfo>() {
            @Override
            public void onResponse(@NonNull Call<DetailMovieInfo> call, @NonNull Response<DetailMovieInfo> response) {
                DetailMovieInfo detailMovieInfo = response.body();
                MovieName = detailMovieInfo.title;
                binding.titletxt.setText(detailMovieInfo.title);
                binding.plotTxt.setText(detailMovieInfo.plot);
                String years = detailMovieInfo.year +" - " + detailMovieInfo.runtime;
                binding.movietimeplusyear.setText(years);
                binding.imdbTxt.setText("IMDB "+detailMovieInfo.imdb_rating);
                genres = detailMovieInfo.genres;
                if(!genres.isEmpty()){
                    binding.genresView.setAdapter(new GenreAdapter(genres));
                    binding.genresView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(DetailActivity.this, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false));
                }
                Glide.with(DetailActivity.this).load(detailMovieInfo.poster).into(binding.filmpic);


            }

            @Override
            public void onFailure(@NonNull Call<DetailMovieInfo> call, @NonNull Throwable throwable) {
                //wait 1 sec and try again
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getDataFromHttp();
            }
        });

    }
}