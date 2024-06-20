package com.example.final_android.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.final_android.R;
import com.example.final_android.RecyclerView.FilmListAdapter;

import com.example.final_android.ViewPager.SlidersAdapter;
import com.example.final_android.ViewPager.SliderItems;
import com.example.final_android.databinding.ActivityMainBinding;
import com.example.final_android.http.MoviesAPIClient;
import com.example.final_android.http.MoviesService;
import com.example.final_android.httpObjects.Root;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private Handler slideHandler = new Handler();
    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            binding.viewPager2.setCurrentItem(binding.viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //remove the navigation bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        binding.bottomMenu.setItemSelected(R.id.explorer , true);


        binding.bottomMenu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                  //handle the bottom menu
                }
            }
        });


        initBanners();
        initTopMovies();
        initAdventures();

        binding.searchMovies.setOnClickListener(searchMovie);


    }

    View.OnClickListener searchMovie = v-> searchMovie();

    private void searchMovie() {
        String search = binding.editTextText2.getText().toString();
        if(search.isEmpty()){
            Toast.makeText(this, "Please enter a movie name", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    private void initAdventures(){
        binding.progressBarAdvanture.setVisibility(View.VISIBLE);
        Retrofit retrofit = MoviesAPIClient.getClient();
        MoviesService service = retrofit.create(MoviesService.class);
        Call<Root> callAsync = service.getAdventureMovies(1);

        callAsync.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                Root root = response.body();
                assert root != null;
                ArrayList<SliderItems> items = new ArrayList<>();
                for (int i = 0; i < root.data.size(); i++) {
                    items.add(new SliderItems(root.data.get(i).id,root.data.get(i).poster, root.data.get(i).title, root.data.get(i).genres, root.data.get(i).year, root.data.get(i).imdb_rating));
                }
                if(!items.isEmpty()){
                    binding.AdventureRV.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    binding.AdventureRV.setAdapter(new FilmListAdapter(items));
                }
                binding.progressBarAdvanture.setVisibility(View.GONE);

                binding.searchMovies.setOnClickListener(v->{
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra("search", binding.editTextText2.getText().toString());
                    startActivity(intent);

                });

            }

            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable throwable) {
                // handle failure
                //Toast.makeText(MainActivity.this, "can't load Adventures try again later!", Toast.LENGTH_SHORT).show();
                //wait 1 sec and try again
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initAdventures();
                    }
                }, 1000);
            }
        });
    }
    private void initTopMovies(){
        binding.progressBarTop.setVisibility(View.VISIBLE);
        Retrofit retrofit = MoviesAPIClient.getClient();
        MoviesService service = retrofit.create(MoviesService.class);
        Call<Root> callAsync = service.getMovies(2, null, null);

        callAsync.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                Root root = response.body();
                assert root != null;
                ArrayList<SliderItems> items = new ArrayList<>();
                for (int i = 0; i < root.data.size(); i++) {
                    items.add(new SliderItems(root.data.get(i).id,root.data.get(i).poster, root.data.get(i).title, root.data.get(i).genres, root.data.get(i).year, root.data.get(i).imdb_rating));
                }
                if(!items.isEmpty()){
                    binding.TopMoviesRV.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    binding.TopMoviesRV.setAdapter(new FilmListAdapter(items));
                }
                binding.progressBarTop.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable throwable) {
                // handle failure
                //Toast.makeText(MainActivity.this, "can't load TopMovies try again later!", Toast.LENGTH_SHORT).show();
                //wait 1 sec and try again
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initTopMovies();
                    }
                }, 1000);

            }
        });
    }
    private void initBanners() {
        binding.progressBarBanners.setVisibility(View.VISIBLE);
        Retrofit retrofit = MoviesAPIClient.getClient();
        MoviesService service = retrofit.create(MoviesService.class);
        Call<Root> callAsync = service.getMovies(1, null, null);

        callAsync.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                Root root = response.body();
                assert root != null;
                ArrayList<SliderItems> items = new ArrayList<>();
                for (int i = 0; i < root.data.size(); i++) {
                    items.add(new SliderItems(root.data.get(i).id,root.data.get(i).poster, root.data.get(i).title, root.data.get(i).genres, root.data.get(i).year, root.data.get(i).imdb_rating));
                }
                banners(items);
                binding.progressBarBanners.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable throwable) {
                // handle failure
                //Toast.makeText(MainActivity.this, "can't load Banners try again later!", Toast.LENGTH_SHORT).show();
                //wait 1 sec and try again
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initBanners();
                    }
                }, 1000);
            }
        });


    }

    private void banners(ArrayList<SliderItems> sliderItemsList) {
        binding.viewPager2.setAdapter(new SlidersAdapter(sliderItemsList, binding.viewPager2));
        binding.viewPager2.setClipToPadding(false);
        binding.viewPager2.setClipChildren(false);
        binding.viewPager2.setOffscreenPageLimit(3);
        binding.viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        }));

        binding.viewPager2.setPageTransformer(compositePageTransformer);
        binding.viewPager2.setCurrentItem(1);
        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
            }
        });

    }

}