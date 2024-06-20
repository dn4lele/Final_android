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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.final_android.R;
import com.example.final_android.RecyclerView.FilmListAdapter;
import com.example.final_android.RecyclerView.SearchAdapter;
import com.example.final_android.ViewPager.SliderItems;
import com.example.final_android.ViewType;
import com.example.final_android.databinding.ActivitySearchBinding;
import com.example.final_android.http.MoviesAPIClient;
import com.example.final_android.http.MoviesService;
import com.example.final_android.httpObjects.Root;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private ViewType selectedType;

    List<SliderItems> sliderItemsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        selectedType= ViewType.OneColumn;


        binding.backBtn.setOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();
        String lastsearch = intent.getStringExtra("search");
        binding.searchBarMovies.setText(lastsearch);

        String search = binding.searchBarMovies.getText().toString();
        fatchMovies(search);
        binding.searchMovieBtn.setOnClickListener(onClickListener);

        binding.changecollums.setOnClickListener(v -> {
            if(selectedType==ViewType.OneColumn){
                selectedType= ViewType.TwoColumn;
                binding.changecollums.setImageResource(R.drawable.ic_one_col);
                binding.searchMovieRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                binding.searchMovieRv.setAdapter(new SearchAdapter(sliderItemsList, selectedType));
            }
            else if(selectedType==ViewType.TwoColumn){
                selectedType= ViewType.OneColumn;
                binding.changecollums.setImageResource(R.drawable.ic_two_col);
                binding.searchMovieRv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                binding.searchMovieRv.setAdapter(new SearchAdapter(sliderItemsList, selectedType));
            }
        });



    }
    View.OnClickListener onClickListener = v->{
        String search = binding.searchBarMovies.getText().toString();
         fatchMovies(search);
    } ;

    public void fatchMovies(String search){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.searchMovieBtn.setVisibility(View.GONE);
        Retrofit retrofit = MoviesAPIClient.getClient();
        MoviesService service = retrofit.create(MoviesService.class);
        Call<Root> callAsync = service.getMoviesByName(search,1);

        callAsync.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                Root root = response.body();
                assert root != null;
                sliderItemsList.clear();

                for (int i = 0; i < root.data.size(); i++) {
                    sliderItemsList.add(new SliderItems(root.data.get(i).id,root.data.get(i).poster, root.data.get(i).title, root.data.get(i).genres, root.data.get(i).year, root.data.get(i).imdb_rating));
                }
                if(!sliderItemsList.isEmpty()){
                    if(selectedType==ViewType.OneColumn){
                        binding.searchMovieRv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        binding.searchMovieRv.setAdapter(new SearchAdapter(sliderItemsList, selectedType));
                    }
                    else if(selectedType==ViewType.TwoColumn){
                        binding.searchMovieRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        binding.searchMovieRv.setAdapter(new SearchAdapter(sliderItemsList, selectedType));
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "there is no movies that contains this string", Toast.LENGTH_SHORT).show();
                }
                binding.progressBar.setVisibility(View.GONE);
                binding.searchMovieBtn.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable throwable) {
                // handle failure
                //Toast.makeText(MainActivity.this, "can't load Adventures try again later!", Toast.LENGTH_SHORT).show();
                //wait 1 sec and try again
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fatchMovies( search);
                    }
                }, 1000);
            }
        });

    }

    public static void changeView(ViewType viewType){

    }
}